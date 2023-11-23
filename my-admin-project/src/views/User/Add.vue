<template>
  <div class="add-user">
    <h1>添加用户</h1>
    <el-form ref="form" :model="user" :rules="rules" label-width="100px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="user.name"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="user.password" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="user.confirmPassword" show-password></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="user.gender">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="user.phone"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="user.address"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="user.email"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
  
<script>
import { addUser } from '@/api/user.js'

export default {
  name: "AddUser",
  data() {
    return {
      user: {
        username: "",
        name: "",
        password: "",
        confirmPassword: "",
        gender: "",
        phone: "",
        address: "",
        email: ""
      },

      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        phone: [{ required: true, message: "请输入手机号", trigger: "blur" }],
        confirmPassword: [
          { required: true, message: "请确认密码", trigger: "blur" },
          {
            validator: this.validateConfirmPassword,
            trigger: "blur",
          },
        ],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        email: [{ required: true, message: "请输入邮箱", trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          addUser(this.user.username,this.user.name,this.user.password,this.user.phone,this.user.gender,this.user.address,this.user.email).then(response => {
                if (response.code==200)
                {
                  console.log(this.user.address)
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

          // TODO: send user data to backend
          console.log(this.user);
        } else {
          console.log("form validation failed");
        }
      });
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    validateConfirmPassword(rule,value,callback){
                console.log(value);
          console.log(this.user.password);
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.user.password) {

          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
    }
  }
};
</script>
  
<style>
.add-user {
  padding: 20px;
}
</style>
  