const { defineConfig } = require('@vue/cli-service');

module.exports = {
  transpileDependencies: true,
  lintOnSave:false,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:28080', // 后端服务器的地址
        changeOrigin: true, // 允许跨域
        pathRewrite: {
          '^/api': '' // 将请求路径中的/api前缀去掉
        }
      }
    }
  }
}

