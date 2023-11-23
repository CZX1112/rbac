<template>
    <div class="user-list">
      <el-table :data="pagedTableData" style="width: 100%">
        <el-table-column prop="user_id" label="用户编号">

        </el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="phone" label="手机号"></el-table-column>
        <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.status === 1" @change="handleStatusChange(scope.row)">
  
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>

        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button @click="setRole(scope.row)" type="text" size="small">角色设置</el-button>
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
        :total="users.length"
      ></el-pagination>

     <!-- 选择角色对话框 -->
      <el-dialog
        title="设置角色"
        :visible="setRoleVisible"
        @close="closeSetRole"
      >
        <el-form ref="form" label-width="80px">
          <el-form-item label="用户编号">
            <el-input v-model="selectedUser.user_id" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="selectedUser.username" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="selectedUser.name" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="selectedUser.phone" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="selectedUser.gender" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="地址">
            <el-input v-model="selectedUser.address" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="selectedUser.email" :readonly="true"></el-input>
          </el-form-item>

          <el-form-item label="角色">
            <el-select v-model="selectedUser.role_id" placeholder="请选择角色">
              <el-option
                v-for="role in roles"
                :key="role.role_id"
                :label="role.rolename"
                :value="role.role_id"
              ></el-option>
            </el-select>
          </el-form-item>

        </el-form>

        <el-form ref="form" label-width="80px">
          <el-form-item>
            <el-button type="primary" @click="onSubmitSetRole(selectedUser.user_id, selectedUser.role_id)">完成</el-button>
            <el-button @click="closeSetRole">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

    </div>
  </template>
    
  <script>
  import { getList, deleteUser, updateUser, getRoleByUserid } from "@/api/user";
  import { Notification } from 'element-ui';

  import { removeToken, getAccessToken } from '@/utils/auth'
  import { getAllRoles } from "@/api/role";
  import { addUserRole, updateUserRole } from "@/api/userrole";
  export default {
    name: "UserList",
    data() {
      return {
        users: [],
        setRoleVisible: false,
        selectedUser: {
          user_id: "",
          username: "",
          name: "",
          phone: "",
          gender: "",
          address: "",
          email: "",
          role_id: ""
        },
        roles: [],
        currentPage: 1,
        pageSize: 8,
      };
    },
    computed: {
      pagedTableData() {
        const startIndex = (this.currentPage - 1) * this.pageSize;
        const endIndex = startIndex + this.pageSize;
        return this.users.slice(startIndex, endIndex);
      }
    },
    mounted() {
      this.fetchUserList();
      this.fetchRoleList();
    },
    methods: {
      fetchUserList() {
        getList().then((res) => {
          this.users = res.data;
        });
      },
      fetchRoleList() {
        getAllRoles().then((res) => {
          this.roles = res.data;
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
        deleteUser(getAccessToken(),row.user_id)
          .then(() => {
            this.users.splice(index, 1); // 从users数组中移除被删除的用户
          })
          .catch((error) => {
            console.error(error);
          });
      },
      setRole(row) {
        this.selectedUser = { ...row }; // 创建用户数据的副本
        console.log(this.selectedUser)
        this.setRoleVisible = true;
      },
      closeSetRole() {
        this.setRoleVisible = false;
      },
      onSubmitSetRole(user_id, role_id) {
        console.log("begin");
        console.log(user_id);
        console.log(role_id);
        getRoleByUserid(user_id).then((res) => {
          console.log(res);
          // 该用户原本就已设置角色，需要更新
          if (res.code == 200) {
            console.log("sssss");
            console.log(user_id);
            console.log(role_id);
            console.log("sssss");
            console.log("已经设置过角色了，不需要再设置");
            updateUserRole(user_id, role_id).then((res) => {
              console.log("角色更改完毕");
              // Show success notification
              Notification.success({
              title: 'Success',
              message: '角色更改完毕'
              });
            });
          }
          // 原本没有
          else {
            addUserRole(user_id, role_id).then((res) => {
              console.log("角色设置完毕")
              // Show success notification
              Notification.success({
              title: 'Success',
              message: '角色设置完毕'
              });
            });
          }
          console.log(role_id);
        });
        console.log("end");
        this.setRoleVisible = false;
      },
      closeSetRole() {
        this.setRoleVisible = false;
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
    