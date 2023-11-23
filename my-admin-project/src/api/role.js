import request from '@/utils/request'

// 获取所有角色
export function getAllRoles() {
  return request({
    url: '/role/list',
    method: 'get'
  })
}

// 根据id删除角色
export function deleteById(role_id) {
  return request({
    url: `/role/delete?role_id=${role_id}`,
    method: 'delete',
  })
}

// 根据角色名查询角色
export function getInfo(rolename) {
  return request({
    url: `/role/getInfo?rolename=${rolename}`,
    method: 'get',
  })
}

// 添加角色信息
export function addRole(role) {
  return request({
    url: '/role/add',
    method: 'post',
    data: role
  })
}

// 修改角色信息
export function updateRole(role) {
  return request({
    url: '/role/update',
    method: 'put',
    data: role
  })
}