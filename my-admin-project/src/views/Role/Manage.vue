<template>
  <div>
    <!--搜索表单-->
    <el-form :inline="true" :model="findData" class="demo-form-inline">
      <el-form-item label="角色名称">
        <el-input
          v-model="findData.rolename"
          placeholder="请输入角色名称"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="findRoleByName">查询</el-button>
         <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="pagedTableData" border style="width: 100%">
      <el-table-column fixed prop="role_id" label="角色编号">
      </el-table-column>
      <el-table-column prop="rolename" label="角色名称">
      </el-table-column>
      <el-table-column prop="description" label="角色描述">
      </el-table-column>
      <!-- <el-table-column prop="createTime" label="创建时间">
      </el-table-column>
      <el-table-column prop="updateTime" label="更新时间">
      </el-table-column> -->
      <el-table-column prop="createTime" label="创建时间">
        <template slot-scope="{ row }">
          {{ formatDate(row.createTime) }}
        </template>
      </el-table-column>

      <el-table-column prop="updateTime" label="更新时间">
        <template slot-scope="{ row }">
          {{ formatDate(row.updateTime) }}
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button @click="readDialog(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="editDialog(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row)" type="text" size="small">删除</el-button>
          <el-button @click="setPermission(scope.row)" type="text" size="small">权限设置</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[1, 5, 8, 10, 20, 30, 40]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableData.length"
    ></el-pagination>

    <!-- 编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      :visible="dialogVisible"
      @close="closeDialog"
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="角色编号">
          <el-input v-model="form.role_id" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="角色名称">
          <el-input v-model="form.rolename" :readonly="isReadOnly"></el-input>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input v-model="form.description" :readonly="isReadOnly"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit" :disabled="isReadOnly">完成</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


    <!-- 选择权限对话框 -->
    <el-dialog
      title="设置权限"
      :visible="setPermissionVisible"
      @close="closeSetPermission"
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="角色编号">
          <el-input v-model="form.role_id" :readonly="isReadOnly"></el-input>
        </el-form-item>
        <el-form-item label="角色名称">
          <el-input v-model="form.rolename" :readonly="isReadOnly"></el-input>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input v-model="form.description" :readonly="isReadOnly"></el-input>
        </el-form-item>

        <el-form-item label="权限设置" prop="permissions">
          <el-select v-model="role.permissions" multiple placeholder="请选择权限">
            <el-option
              v-for="permission in permissions"
              :key="permission.permission_id"
              :label="permission.permission_name"
              :value="permission.permission_id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmitSetPermission">完成</el-button>
          <el-button @click="closeSetPermission">取消</el-button>
        </el-form-item>

      </el-form>
      <!-- <el-form ref="form" :model="form" label-width="80px">

      </el-form> -->
    </el-dialog>
  </div>
</template>

<script>
import { getAllRoles, deleteById, addRole, updateRole, getInfo } from "@/api/role.js";
import { getAllPermissions, addRolePermission, deletePermissionById } from '@/api/permission.js'

export default {
  methods: {
    findRoleByName() {
      getInfo(this.findData.rolename).then((resp) => {
        console.log(resp);
        if (resp.code === 200) {
          // 更改成功
          this.$message.success("角色查询成功");
          // 更新数据，重新加载界面
          this.tableData = [resp.data]; // 将对象放入数组中
          this.currentPage = 1;
        }
        else {
          this.$message.error(resp.msg);
        }
      });
    },
    // 清空查询列表并重置界面
    clear(){
      this.findData.rolename = null;
      this.init();
    },
    readDialog(row) {
      this.dialogTitle = "查看角色信息";
      this.dialogVisible = true;
      this.isReadOnly = true;
      // 将选中行的数据复制到 editedRow
      this.form.role_id = row.role_id;
      this.form.rolename = row.rolename;
      this.form.description = row.description;
      this.form.module = row.module;
    },
    editDialog(row) {
      this.dialogTitle = "编辑角色信息";
      this.dialogVisible = true;
      this.isReadOnly = false;
      // 将选中行的数据复制到 editedRow
      this.form.role_id = row.role_id;
      this.form.rolename = row.rolename;
      this.form.description = row.description;
      this.form.module = row.module;
    },

    closeDialog() {
      this.dialogVisible = false;
    },

    fetchPermissionList() {
      getAllPermissions().then((res) => {
        this.permissions = res.data;
      });
    },


    onSubmit() {
      // let可以表示创建角色或更新角色信息
      let method;
      method = updateRole(this.form); // 更新角色
      console.log(this.form);
      // 在这里调用 addRole 方法，将角色数据保存到数据库
      method.then((resp) => {
        console.log(resp);
        if (resp.code === 200) {
          // 添加成功
          this.$message.success("角色修改成功");
          // 更新数据，重新加载界面
          this.init();
        }
        else {
          this.$message.error(resp.msg);
        }
      });
      this.dialogVisible = false;
    },


    //删除角色信息
    deleteById(row){
      console.log(row.role_id);
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
          //2. 发送AJAX请求
          deleteById(row.role_id).then((resp) => {
            console.log(resp);
            if (resp.code == 200) {
              // 删除改角色对应的权限信息
              deletePermissionById(row.role_id).then((res) => {
                if (res.code == 200) {
                  this.$message.success('删除成功');
                }
              });

              //删除成功
              this.$message.success("恭喜你，删除成功");
              // 更新data数据，重新加载界面
              this.init();
            } else {
              this.$message.error(resp.msg);
            }
          });
      }).catch(() => {
          //用户点击取消按钮
          this.$message.info("已取消删除");
        });
    },

    // 权限设置部分
    setPermission(row) {
      this.setPermissionVisible = true;
      this.isReadOnly = true;
      // 将选中行的数据复制到 editedRow
      this.form.role_id = row.role_id;
      this.form.rolename = row.rolename;
      this.form.description = row.description;
      this.form.module = row.module;
    },
    onSubmitSetPermission() {
      deletePermissionById(this.form.role_id).then((res) => {
        if (res.code == 200) {
          this.$message.success('删除成功');

          // 为角色设置权限（循环添加所有权限）
          this.role.permissions.forEach(permission => {
            addRolePermission(this.form.role_id, permission).then((res) => {
              if (res.code == 200) {
                
              }
            });
            this.$message.success('为角色分配权限成功');
          });

        }
      });

      this.setPermissionVisible = false;
    },
    closeSetPermission() {
      this.setPermissionVisible = false;
    },

    //初始化 - 查询全部
    init() {
      getAllRoles().then((result) => {
        console.log(result);
        if (result.code == 200) {
          this.tableData = result.data;
        }
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
    },
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' };
      const date = new Date(dateString);
      return date.toLocaleString('en-US', options); // Adjust 'en-US' based on your desired locale
    },
  },

  data() {
    return {
      dialogTitle: "查看用户信息",
      dialogVisible: false, // 控制对话框的显示
      isReadOnly: true,
      form: {
        role_id: "",
        rolename: "",
        description: "",
        module: "",
        createTime: "",
        updateTime: "",
      },
      findData: {
        rolename: ""
      },
      role: {
        role_id: "",
        rolename: "",
        description: "",
        permissions: []
      },
      permissions: [],
      editedRow: {}, // 用于编辑的行数据
      tableData: [],
      deleteDialogVisible: false, // 控制删除确认对话框的显示

      setPermissionVisible: false,
      currentPage: 1,
      pageSize: 8,
    };
  },

  computed: {
    pagedTableData() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.tableData.slice(startIndex, endIndex);
    }
  },

  mounted() {
    //当页面加载完成后自动执行。
    //开始从数据库中读取初始化数据
    this.init();
    this.fetchPermissionList();
  },
};
</script>

<style scoped>
.button-container {
  text-align: left; /* 将按钮容器中的内容右对齐 */
}
</style>