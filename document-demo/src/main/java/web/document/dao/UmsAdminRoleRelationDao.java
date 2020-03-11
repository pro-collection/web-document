package web.document.dao;

import org.apache.ibatis.annotations.Param;
import web.document.mbg.model.UmsPermission;

import java.util.List;

public interface UmsAdminRoleRelationDao {
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
