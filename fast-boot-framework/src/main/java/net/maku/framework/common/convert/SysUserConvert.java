//package net.maku.framework.common.convert;
//
//import net.maku.framework.common.entity.SysUserEntity;
//import net.maku.framework.common.entity.SysUserVO;
//import net.maku.framework.security.user.UserDetail;
//import org.mapstruct.Mapper;
//import org.mapstruct.ReportingPolicy;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//
//
//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface SysUserConvert {
//    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);
//
//    SysUserVO convert(SysUserEntity entity);
//
//    SysUserEntity convert(SysUserVO vo);
//
//    SysUserVO convert(UserDetail userDetail);
//
//    UserDetail convertDetail(SysUserEntity entity);
//
//    List<SysUserVO> convertList(List<SysUserEntity> list);
//
//}
