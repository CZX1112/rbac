<template>
  <div class="success-page">
    <el-main>
      <div class="content">
        <h1>Login Successful!</h1>
        <p>Welcome, {{ username }}!</p>
      </div>
    </el-main>
  </div>
</template>

<script>
import { getInfo } from "@/api/user";
import { getAccessToken } from "@/utils/auth"

export default {
  name: "SuccessPage",
  data() {
    return {
      username: "",
    };
  },
  mounted() {
    var token = getAccessToken()
    getInfo(token).then((res) => {
      this.username = res.data.username;
    })
  },
};
</script>

<style scoped>
.success-page {
  height: 100%;
}

.content {
  margin: 20px;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
