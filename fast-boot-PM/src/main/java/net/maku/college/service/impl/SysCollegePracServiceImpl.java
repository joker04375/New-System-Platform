package net.maku.college.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import net.maku.college.dao.SysCollegePracDao;
import net.maku.college.entity.SysCollegePracEntity;
import net.maku.college.response.ResponseEnum;
import net.maku.college.service.SysCollegePracService;
import net.maku.enterprise.common.OrgUtils;
import net.maku.framework.common.exception.ErrorCode;
import net.maku.framework.common.service.impl.BaseServiceImpl;
import net.maku.framework.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
@AllArgsConstructor
public class SysCollegePracServiceImpl extends BaseServiceImpl<SysCollegePracDao, SysCollegePracEntity> implements SysCollegePracService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysCollegePracServiceImpl.class);

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public Result postInternship(String year, String name, int collegeId) {
        // 事务
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        SysCollegePracEntity info = new SysCollegePracEntity();
        info.setYear(year);
        info.setQuarter(name);
        info.setStatus(1);
        info.setCollegeId(collegeId);
        info.setTimeId(OrgUtils.getIdByTime());
        try {
            baseMapper.insert(info);
            transactionManager.commit(txStatus);
            return Result.ok(ResponseEnum.REQUEST_OK);
        } catch (Exception e) {
            LOGGER.error("发布实习失败，学院id为 {}",collegeId);
            transactionManager.rollback(txStatus);
            return Result.error(ResponseEnum.REQUEST_FAIL);
//            return Result.error(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
    }

    @Override
    public List<SysCollegePracEntity> getAllOrderByYear(long collegeId) {
        return baseMapper.selectList(new QueryWrapper<SysCollegePracEntity>().eq("college_id",collegeId).orderByDesc("year"));
    }
}
