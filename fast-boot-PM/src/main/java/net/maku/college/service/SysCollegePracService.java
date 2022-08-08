package net.maku.college.service;

import net.maku.college.entity.SysCollegePracEntity;
import net.maku.framework.common.service.BaseService;
import net.maku.framework.common.utils.Result;

import java.util.List;

public interface SysCollegePracService extends BaseService<SysCollegePracEntity> {
    Result<String> postInternship(String year, String name, int collegeId);

    List<SysCollegePracEntity> getAllOrderByYear(long collegeId);
}
