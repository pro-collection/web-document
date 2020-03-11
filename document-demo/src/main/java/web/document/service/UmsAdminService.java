package web.document.service;

import web.document.mbg.model.UmsAdmin;
import web.document.mbg.model.UmsPermission;

import java.util.List;

/**
 * 后台管理员 Service
 */
public interface UmsAdminService {
    UmsAdmin getAdminByUsername(String username);

    UmsAdmin register(UmsAdmin umsAdminParam);

    String login(String username, String password);

    List<UmsPermission> getPermissionList(Long adminId);
}
