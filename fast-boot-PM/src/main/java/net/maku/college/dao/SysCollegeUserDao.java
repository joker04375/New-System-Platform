package net.maku.college.dao;

import net.maku.framework.common.dao.BaseDao;
import net.maku.system.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysCollegeUserDao extends BaseDao<SysUserEntity> {

    List<SysUserEntity> getAllUserByRoleAndOrg(@Param("roleName") String roleName,@Param("orgId") long orgId);
}
