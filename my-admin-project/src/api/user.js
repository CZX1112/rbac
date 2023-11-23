import request from '@/utils/request'

export function getList() {
  return request({
    url: '/user/list',
    method: 'get'
  })
}

export function addUser(username,name,password,phone,gender,address,email) {
  const data = {
    username,
    name,
    password,
    phone,
    gender,
    address,
    email
  }
  return request({
    url: '/user/add',
    method: 'post',
    data: data
  })
}

export function deleteUser(token,user_id) {
  return request({
    url: `/user/delete?user_id=${user_id}`, 
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
    url: `/user/update?user_id=${user_id}`, 
    method: 'put',
    data: data
  });
}


// 根据token获取用户信息
export function getInfo(token) {
  return request({
    url: '/user/',
    method: 'get',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
}

// 根据用户名查询用户信息
export function getUser(username) {
    return request({
      url: `/user/getUser?username=${username}`,
      method: 'get',
    })
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

// 根据用户名获取用户角色
export function getRole(token, user_name) {
    return request({
      url: `/user/getRoleByUsername/${user_name}`,
      method: 'get',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
}

// 根据用户id获取用户角色
export function getRoleByUserid(user_id) {
  return request({
    url: `/user/getRoleByUserid`,
    method: 'get',
    params: {
      user_id: user_id,
    },
  });
}