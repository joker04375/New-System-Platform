package net.maku.enterprise.dao;

import net.maku.enterprise.entity.SysOrgPracManageEntity;
import net.maku.enterprise.entity.interation.SysAllOrgPracEntity;
import net.maku.framework.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysOrgPracManageDao extends BaseDao<SysOrgPracManageEntity>{

    List<SysAllOrgPracEntity> getAllPrac();

    /**
     * author: leo
     * */
    List<SysAllOrgPracEntity> getPracsByConditions(Map<String,String> map);

    /**
     * author: leo
     * */
    List<SysAllOrgPracEntity> getByPracIds(List<Long> pracIds);
}
