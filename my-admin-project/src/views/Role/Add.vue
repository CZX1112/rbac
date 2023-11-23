<template>
  <div class="add-role">
    <h1>添加角色</h1>
    <el-form ref="form" :model="role" :rules="rules" label-width="100px">
      <el-form-item label="角色编号" prop="rolename">
        <el-input v-model="role.role_id"></el-input>
      </el-form-item>
      <el-form-item label="角色名称" prop="rolename">
        <el-input v-model="role.rolename"></el-input>
      </el-form-item>
      <el-form-item label="角色描述" prop="description">
        <el-input v-model="role.description"></el-input>
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
        <!-- <el-button type="primary" @click="submitForm">提交</el-button> -->
        <el-button type="primary" @click="test">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
  
<script>
import { addUser } from '@/api/user.js'
import { addRole } from '@/api/role.js'
import { getAllPermissions, addRolePermission } from '@/api/permission.js'

export default {
  name: "AddRole",
  data() {
    return {
      role: {
        role_id: "",
        rolename: "",
        description: "",
        permissions: []
      },
      permissions: [],

      rules: {
        username: [{ required: true, message: "请输入用户名", trigger: "blur" },
        ],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.fetchPermissionList();
  },
  methods: {
    test() {
      console.log(this.role.permissions);
      console.log(this.role.permissions[0]);
      console.log(this.permissions[0].permission_name);
      // 增加角色
      addRole(this.role).then((res) => {
        if (res.code==200) {
          this.$message.success('增加角色成功');
          // this.$router.push({ path: '/rbac/role' });
        }
      });
      // // 为角色设置权限
      // addRolePermission(this.role.role_id, this.role.permissions[0]).then((res) => {
      //   if (res.code==200) {
      //     this.$message.success('为角色分配权限成功');
      //     // this.$router.push({ path: '/rbac/role' });
      //   }
      // });
        // 为角色设置权限（循环添加所有权限）
        this.role.permissions.forEach(permission => {
          addRolePermission(this.role.role_id, permission).then((res) => {
            if (res.code == 200) {
              
              // 可以在此处执行跳转逻辑，根据需求而定
              // this.$router.push({ path: '/rbac/role' });
            }
          });          
        });
        this.$router.push({ path: '/rbac/role/list' });
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          addUser(this.role.rolename, this.role.description).then(response => {
                if (response.code==200)
                {
                  console.log(this.role.address)
                  this.$message.success('插入成功')
                  this.$router.push({ path: '/rbac/user/list' });
                }
                else
                {
                  this.$message.error('插入失败')
                }
          }).catch(() => {
            // 异常
            this.$message.error('插入失败')
            this.loading = false
          })

          // TODO: send role data to backend
          console.log(this.role);
        } else {
          console.log("form validation failed");
        }
      });
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    fetchPermissionList() {
      getAllPermissions().then((res) => {
        this.permissions = res.data;
      });
    },
  }
};
</script>
  
<style>
.add-role {
  padding: 20px;
}
</style>
  