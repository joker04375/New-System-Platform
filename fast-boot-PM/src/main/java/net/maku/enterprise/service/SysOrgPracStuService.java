package net.maku.enterprise.service;

import net.maku.enterprise.dao.SysOrgPracStuDao;
import net.maku.enterprise.dto.SysStuPracDetailDto;
import net.maku.enterprise.entity.SysOrgPracStuEntity;
import net.maku.framework.common.service.BaseService;

import java.util.List;
import java.util.Map;

public interface SysOrgPracStuService extends BaseService<SysOrgPracStuEntity> {

    SysOrgPracStuEntity getOnePracStuMessage(Long Id);

    List<SysOrgPracStuEntity> getAllPracStuMessage(Long orgId, Long pracId);

    Boolean update(SysOrgPracStuEntity sysOrgPracStuEntity);

    Boolean delete(Long Id,Long orgId, Long pracId,Long stuId) ;

    List<SysOrgPracStuEntity> getAllApplyStu( Long orgId, Long pracId);

    List<SysOrgPracStuEntity> getAllWorkingStu(Long orgId, Long pracId);

    List<SysOrgPracStuEntity> getAllOutStu(Long orgId, Long pracId);
    //void addApplyStu(SysStuApplyPostDTO sysStuApplyPostDTO);

    /**
     * author:lzm
     */
    List<SysOrgPracStuEntity> getAllStusByPracs(List<Long> pracId);

    /**
     * author:lzm
     */
    List<SysStuPracDetailDto> getAllStuPracByOrgAndPracId(Long orgId, Long pracId);

    /**
     * author:lzm
     */
    List<SysOrgPracStuEntity> getAllStuPracByOrgAndPracAndPostId(Long orgId, Long pracId, Long postId);

    /**
     * author:lzm
     */
    List<SysOrgPracStuEntity> getStusByConditions(Map<String,String> map);

}
