//package net.maku.framework.common.dao;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import net.maku.framework.common.entity.SysUserEntity;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 系统用户
// *
// * @author 阿沐 babamu@126.com
// */
//@Mapper
//public interface SysUserDao extends BaseDao<SysUserEntity> {
//
//	List<SysUserEntity> getList(Map<String, Object> params);
//
//	SysUserEntity getById(@Param("id") Long id);
//
//	default SysUserEntity getByUsername(String username){
//		return this.selectOne(new QueryWrapper<SysUserEntity>().eq("username", username));
//	}
//
//	default SysUserEntity getByWorkNumber(String workNumber){
//		return this.selectOne(new QueryWrapper<SysUserEntity>().eq("work_number", workNumber));
//	}
//
//	default SysUserEntity getByMobile(String mobile){
//		return this.selectOne(new QueryWrapper<SysUserEntity>().eq("mobile", mobile));
//	}
//}