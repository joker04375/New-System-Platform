package net.maku.enterprise.dao;

import net.maku.enterprise.entity.SysOrgPracManageEntity;
import net.maku.enterprise.dto.SysAllOrgPracDto;
import net.maku.framework.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysOrgPracManageDao extends BaseDao<SysOrgPracManageEntity>{

    List<SysAllOrgPracDto> getAllPrac();

    /**
     * author: leo
     * */
    List<SysAllOrgPracDto> getPracsByConditions(Map<String,String> map);

    /**
     * author: leo
     * */
    List<SysAllOrgPracDto> getByPracIds(List<Long> pracIds);

    SysAllOrgPracDto getByOrgAndPracId(long orgId,long pracId);
}
