package net.maku.student.service.impl;


import net.maku.enterprise.entity.SysOrgPracStuEntity;
import net.maku.framework.common.service.impl.BaseServiceImpl;
import net.maku.student.dao.SysStuManageDao;
import net.maku.student.service.SysStuManageService;
import org.springframework.stereotype.Service;

/**
 * 实习申报
 */
@Service
public class SysStuManageServiceImpl extends BaseServiceImpl<SysStuManageDao, SysOrgPracStuEntity> implements SysStuManageService {
}
