import request from '@/utils/request'
import { getAccessToken,getRefreshToken } from '@/utils/auth'

// 用户登录
export function login(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/user/login',
    method: 'post',
    data: data
  })
}

export function getList() {
  return request({
    url: '/user/list',
    method: 'get'
  })
}

export function addUser(username,name,password,phone,gender) {
  const data = {
    username,
    name,
    password,
    phone,
    gender
  }
  return request({
    url: '/user/add',
    method: 'post',
    data: data
  })
}

export function deleteUser(token,user_id) {
  return request({
    url: `/user/delete/${user_id}`, 
    method: 'delete',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
}

export function updateUser(user_id,status) {
  const data={
    user_id,
    status
  };
  return request({
    url: `/user/update/${user_id}`, 
    method: 'put',
    data: data
  });
}


// 获取用户信息
export function getInfo(token) {
  return request({
    url: '/user/',
    method: 'get',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
}

// 获取菜单
export function getMenu(token) {
  return request({
    url: '/user/menu/',
    method: 'get',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
}








// 退出方法
export function logout() {
  const data = {
      accessToken: getAccessToken(),
      refreshToken: getRefreshToken()
  }
  console.log(data);
  return request({
      url: '/user/logout',
      method: 'post',
      data: data
  })
}


// 刷新访问令牌
export function refreshToken() {
  const data = {
      refreshToken: getRefreshToken()
  }
  return request({
      url: '/user/refresh-token',
      method: 'post',
      data: data
  })
}
