<template>
  <div class="add-permission">
    <h1>添加权限</h1>
    <el-form ref="form" :model="permission" :rules="rules" label-width="100px">
      <el-form-item label="权限编号" prop="permission_id">
        <el-input v-model="permission.permission_id"></el-input>
      </el-form-item>
      <el-form-item label="权限名称" prop="permission_name">
        <el-input v-model="permission.permission_name"></el-input>
      </el-form-item>
      <el-form-item label="权限描述" prop="description">
        <el-input v-model="permission.description"></el-input>
      </el-form-item>
      <el-form-item label="权限链接" prop="url">
        <el-input v-model="permission.url"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
  
<script>
import { addPermission } from '@/api/permission.js'

export default {
  name: "AddPermission",
  data() {
    return {
      permission: {
        permission_id: "",
        permission_name: "",
        description: "",
        url: ""
      },

      rules: {
        permission_id: [{ required: true, message: "请输入权限编号", trigger: "blur" }],
        permission_name: [{ required: true, message: "请输入权限名称", trigger: "blur" }],
        description: [{ required: true, message: "请选择权限描述", trigger: "blur" }],
        url: [{ required: true, message: "请输入权限链接", trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          addPermission(this.permission).then(response => {
                if (response.code==200)
                {
                  console.log(this.permission.permission_name)
                  this.$message.success('插入成功')
                  this.$router.push({ path: '/rbac/permission/list' });
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
          console.log(this.permission);
        } else {
          console.log("form validation failed");
        }
      });
    },
    resetForm() {
      this.$refs.form.resetFields();
    }
  }
};
</script>
  
<style>
.add-permission {
  padding: 20px;
}
</style>
  