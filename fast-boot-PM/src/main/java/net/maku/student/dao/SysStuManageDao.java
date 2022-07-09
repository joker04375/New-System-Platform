package net.maku.student.dao;

import net.maku.student.entity.SysStuPostEntity;
import net.maku.framework.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysStuManageDao extends BaseDao<SysStuPostEntity> {
}
