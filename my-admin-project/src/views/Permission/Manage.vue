<template>
  <div>
    <!--搜索表单-->
        <el-form :inline="true" :model="find" class="demo-form-inline">
        <el-form-item label="权限名称">
            <el-input v-model="find.findName" placeholder="请输入权限名称"></el-input>
        </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="findPermissionByName">查询</el-button>
         <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="pagedTableData" border style="width: 100%">
      <el-table-column prop="permission_id" label="权限编号"></el-table-column>
      <el-table-column prop="permission_name" label="权限名称"></el-table-column>
      <el-table-column prop="description" label="权限描述"></el-table-column>
      <el-table-column prop="url" label="权限链接"></el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button @click="readDialog(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="editDialog(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 8, 10, 20, 30, 40]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableData.length"
    ></el-pagination>

    <!-- 编辑对话框 -->
    <el-dialog :title="dialogTitle" :visible="dialogVisible" @close="closeDialog">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="权限编号">
          <el-input v-model="form.permission_id" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="权限名称">
          <el-input v-model="form.permission_name" :readonly="isReadOnly"></el-input>
        </el-form-item>
        <el-form-item label="权限描述">
          <el-input v-model="form.description" :readonly="isReadOnly"></el-input>
        </el-form-item>
        <el-form-item label="权限链接">
          <el-input v-model="form.url" :readonly="isReadOnly"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit" :disabled="isReadOnly">完成</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getAllPermissions, getPermission, deleteByPermissionId, updatePermission } from '@/api/permission.js'

export default {
  methods: {
    findPermissionByName() {
      getPermission(this.find.findName).then((resp) => {
        console.log(resp);
        if (resp.code === 200) {
          // 更改成功
          this.$message.success("权限查询成功");
          // 更新数据，重新加载界面
          this.tableData = [resp.data]; // 将对象放入数组中
          this.currentPage = 1;
        }
        else {
          this.$message.error(resp.msg);
        }
      });
    },
    fetchPermissionList() {
      getAllPermissions().then((res) => {
        this.permissions = res.data;
        this.tableData = res.data;
      });
    },
    // 清空查询列表并重置界面
    clear(){
      this.find.findName = null;
      this.init();
    },
    readDialog(row) {
      this.dialogTitle = "查看权限信息";
      this.dialogVisible = true;
      this.isReadOnly = true;
      // 将选中行的数据复制到 editedRow
      this.form.permission_id = row.permission_id;
      this.form.permission_name = row.permission_name;
      this.form.description = row.description;
      this.form.url = row.url;
    },
    editDialog(row) {
      this.dialogTitle = "编辑权限信息";
      this.dialogVisible = true;
      this.isReadOnly = false;
      // 将选中行的数据复制到 editedRow
      this.form.permission_id = row.permission_id;
      this.form.permission_name = row.permission_name;
      this.form.description = row.description;
      this.form.url = row.url;
    },

    closeDialog() {
      this.dialogVisible = false;
    },

    onSubmit() {
      let method;
      method = updatePermission(this.form); // 更新权限
      console.log(this.form);
      // 在这里调用 addRole 方法，将角色数据保存到数据库
      method.then((resp) => {
        console.log(resp);
        if (resp.code === 200) {
          // 添加成功
          this.$message.success("权限修改成功");
          // 更新数据，重新加载界面
          this.init();
        }
        else {
          this.$message.error(resp.msg);
        }
      });
      this.dialogVisible = false;
    },

    //删除权限信息
    deleteById(row){
      console.log(row.permission_id);
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
          //2. 发送AJAX请求
          deleteByPermissionId(row.permission_id).then((resp) => {
            console.log(resp);
            if (resp.code == 200) {
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

    //初始化 - 查询全部
    init() {
      getAllPermissions().then((result) => {
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
    }
  },
  computed: {
    pagedTableData() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.tableData.slice(startIndex, endIndex);
    }
  },
  data() {
    return {
      dialogTitle: "查看权限信息",
      dialogVisible: false, // 控制对话框的显示
      isReadOnly: true,

      permissions: [],
      tableData: [],
      find: {
        findName: ""
      },
      form: {
        permission_id: "",
        permission_name: "",
        description: "",
        url: ""
      },
      currentPage: 1,
      pageSize: 8,
      deleteDialogVisible: false, // 控制删除确认对话框的显示

    };
  },
  mounted() {
    //当页面加载完成后自动执行。
    //开始从数据库中读取初始化数据
    this.init();
  },
};
</script>

<style scoped>
.button-container {
  text-align: left; /* 将按钮容器中的内容右对齐 */
}
</style>