<template>
  <div>
  <!--搜索表单-->
    <el-form :inline="true" :model="form" class="demo-form-inline">
      <el-form-item label="用户名">
        <el-input v-model="form.findName" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="findUserByName">查询</el-button>
         <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

  <div class="user-list">
    <el-table :data="pagedTableData" style="width: 100%">
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="phone" label="手机号"></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <!-- <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column prop="updateTime" label="更新时间"></el-table-column> -->

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

      <!-- <el-table-column label="状态">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status === 1" @change="handleStatusChange(scope.row)">

          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column> -->
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
  </div>
</template>
  
<script>
import { getList, deleteUser, updateUser, getUser } from "@/api/user";
export default {
  name: "UserList",
  data() {
    return {
      users: [],
      tableData: [],
      form: {
        findName: ""
      },
      currentPage: 1,
      pageSize: 8,
    };
  },
  mounted() {
    this.fetchUserList();
  },
  computed: {
    pagedTableData() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.tableData.slice(startIndex, endIndex);
    }
  },
  methods: {
    findUserByName() {
      getUser(this.form.findName).then((resp) => {
        console.log(resp);
        if (resp.code === 200) {
          // 更改成功
          this.$message.success("用户查询成功");
          // 更新数据，重新加载界面
          this.tableData = [resp.data]; // 将对象放入数组中
          this.currentPage = 1;
        }
        else {
          this.$message.error(resp.msg);
        }
      });
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' };
      const date = new Date(dateString);
      return date.toLocaleString('en-US', options); // Adjust 'en-US' based on your desired locale
    },
    // 清空查询列表并重置界面
    clear(){
      this.form.findName = null;
      this.fetchUserList();
    },
    fetchUserList() {
      getList().then((res) => {
        this.users = res.data;
        this.tableData = res.data;
      });
    },
    handleStatusChange(row) {
      const newStatus = row.status ? 0 : 1;
      console.log(row.user_id, newStatus);
      updateUser(row.user_id, newStatus)
        .then(() => {
          // 成功更新状态
          row.status = newStatus; // 手动更新状态数据
        })
        .catch((error) => {
          console.error(error);
        });
    },
    handleDelete(index, row) {
      // 调用删除用户的API方法

      console.log(row.user_id)
      deleteUser(row.user_id)
        .then(() => {
          this.users.splice(index, 1); // 从users数组中移除被删除的用户
        })
        .catch((error) => {
          console.error(error);
        });
    },
    handleSizeChange(val) {
      this.pageSize = val;
    },
    handleCurrentChange(val) {
      this.currentPage = val;
    }
  }
};
</script>
  
<style>
.user-list {
  padding: 20px;
}
</style>
  