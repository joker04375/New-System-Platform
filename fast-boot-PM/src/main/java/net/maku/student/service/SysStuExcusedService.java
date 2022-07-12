package net.maku.student.service;

import net.maku.student.entity.SysStuExcusedEntity;
import net.maku.framework.common.service.BaseService;

import java.util.List;

public interface SysStuExcusedService extends BaseService<SysStuExcusedEntity> {
    /**
     * 添加请假
     * @param sysStuExcusedEntity
     */
    void applyExcused(SysStuExcusedEntity sysStuExcusedEntity);

    /**
     * 查询请假
     * @return
     */
    List<SysStuExcusedEntity> selectExcuseds(Long stuId);


    /**
     * 删除请假
     * @param id
     */
    void deleteExcused(Long id);

    /**
     * 学院查询请假
     * @return
     */
    List<SysStuExcusedEntity> selectCollegeExcusedsByColIdAndTimeId(Long colId,Long timeId);

    /**
     * author:lzm
     * 查询所有需要经过学院审批的请假
     */
    List<SysStuExcusedEntity> selectExcusedByCollege(long collegeId);

    /**
     * 企业查询请假
     * @return
     */
    List<SysStuExcusedEntity> selectEnterpriseExcusedsByOrgIdAndPracId(Long orgId, Long pracId);
}
