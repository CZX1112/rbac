import request from '@/utils/request'

// 获取所有角色
export function getAllPermissions() {
  return request({
    url: '/permission/list',
    method: 'get'
  })
}

// 添加新的数据
export function addRolePermission(role_id, permission_id) {
    return request({
      url: `/permission/add?role_id=${role_id}&permission_id=${permission_id}`,
      method: 'post',
    });
  }

// 根据角色id删除对应权限
export function deletePermissionById(role_id) {
    return request({
      url: `/permission/delete?role_id=${role_id}`,
      method: 'delete',
    })
  }

// 根据权限名查询权限信息
export function getPermission(permission_name) {
  return request({
    url: `/permission/getByPermissionname?permission_name=${permission_name}`,
    method: 'get',
  })
}

// 根据id删除权限
export function deleteByPermissionId(permission_id) {
  return request({
    url: `/permission/deleteByPermissionId?permission_id=${permission_id}`,
    method: 'delete',
  })
}

// 添加权限信息
export function addPermission(permission) {
  return request({
    url: '/permission/addPermission',
    method: 'post',
    data: permission
  })
}

// 修改权限信息
export function updatePermission(permission) {
  return request({
    url: '/permission/update',
    method: 'put',
    data: permission
  })
}