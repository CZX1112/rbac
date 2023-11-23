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

  </div>
</template>

<script>
import { getAllRoles, getInfo } from "@/api/role.js";

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

  computed: {
    pagedTableData() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.tableData.slice(startIndex, endIndex);
    }
  },

  data() {
    return {
      findData: {
        rolename: ""
      },
      currentPage: 1,
      pageSize: 8,
      tableData: []
    };
  },
  mounted() {
    this.init();
  },
};
</script>

<style scoped>
.button-container {
  text-align: left; /* 将按钮容器中的内容右对齐 */
}
</style>