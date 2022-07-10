package net.maku.enterprise.dao;

import net.maku.enterprise.entity.SysOrgPracStuEntity;
import net.maku.framework.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: 25652
 * @time: 2022/6/15 21:29
 */
@Mapper
public interface SysOrgPracStuDao extends BaseDao<SysOrgPracStuEntity> {
    List<SysOrgPracStuEntity> getAllStusByPracs(List<Long> pracId);
}
