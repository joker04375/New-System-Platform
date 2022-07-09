package net.maku.student.service;

import net.maku.student.entity.SysStuDetailsEntity;
import net.maku.framework.common.service.BaseService;

public interface SysStuDetailsService extends BaseService<SysStuDetailsEntity> {
    SysStuDetailsEntity getByStuNum(String stuNum);
}
