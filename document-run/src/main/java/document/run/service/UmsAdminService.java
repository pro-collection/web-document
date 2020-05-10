package document.run.service;

import document.run.mbg.model.UmsAdmin;
import document.run.mbg.model.UmsPermission;

import java.util.List;

/**
 * 后端管理员 Service
 */
public interface UmsAdminService {
    /* 根据用户名获取后台管理员 */
    UmsAdmin getAdminByUsername(String username);

    /* 注册功能 */
    UmsAdmin register(UmsAdmin umsAdminParam);

    /* 登录功能 */
    String login(String username, String password);

    /* 获取用户所有权限 */
    List<UmsPermission> getPermissionList(Long adminId);
}
