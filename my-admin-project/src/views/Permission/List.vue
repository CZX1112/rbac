<template>
    <div>
    <!--搜索表单-->
        <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="权限名称">
            <el-input v-model="form.findName" placeholder="请输入权限名称"></el-input>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="findPermissionByName">查询</el-button>
            <el-button type="info" @click="clear">清空</el-button>
        </el-form-item>
        </el-form>

        <div class="user-list">
            <el-table :data="pagedTableData" style="width: 100%">
                <el-table-column prop="permission_id" label="权限编号"></el-table-column>
                <el-table-column prop="permission_name" label="权限名称"></el-table-column>
                <el-table-column prop="description" label="权限描述"></el-table-column>
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

        </div>
    </div>
</template>
  
<script>
import { getAllPermissions, getPermission } from '@/api/permission.js'
export default {
  name: "PermissionList",
  data() {
    return {
        permissions: [],
        tableData: [],
        form: {
            findName: ""
        },
        currentPage: 1,
        pageSize: 8
    };
  },
  mounted() {
    this.fetchPermissionList();
  },
  methods: {
    findPermissionByName() {
      getPermission(this.form.findName).then((resp) => {
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
    // 清空查询列表并重置界面
    clear(){
      this.form.findName = null;
      this.fetchPermissionList();
    },
    fetchPermissionList() {
      getAllPermissions().then((res) => {
        this.permissions = res.data;
        this.tableData = res.data;
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
  }
};
</script>
  
<style>
.user-list {
  padding: 20px;
}
</style>