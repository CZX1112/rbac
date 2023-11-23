import request from '@/utils/request'

// 添加新的数据
export function addUserRole(user_id, role_id) {
    return request({
      url: `/userrole/add?user_id=${user_id}&role_id=${role_id}`,
      method: 'post',
    });
  }

// 更新数据
export function updateUserRole(user_id, role_id) {
  return request({
    url: `/userrole/update?user_id=${user_id}&role_id=${role_id}`,
    method: 'post',
  });
}