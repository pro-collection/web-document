package document.run.dao;

import document.run.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsAdminRoleRelationDao {

    /* 获取用户的所有权限 */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
