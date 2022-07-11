package net.maku.college.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import net.maku.college.dao.SysCollegePracDao;
import net.maku.college.entity.SysCollegePracEntity;
import net.maku.college.service.SysCollegePracService;
import net.maku.enterprise.common.OrgUtils;
import net.maku.framework.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysCollegePracServiceImpl extends BaseServiceImpl<SysCollegePracDao, SysCollegePracEntity> implements SysCollegePracService {
    @Override
    public void postInternship(String year, String name,int collegeId) {
        SysCollegePracEntity info = new SysCollegePracEntity();
        info.setYear(year);
        info.setQuarter(name);
        info.setStatus(1);
        info.setCollegeId(collegeId);
        info.setTimeId(OrgUtils.getIdByTime());
        baseMapper.insert(info);
    }

    @Override
    public List<SysCollegePracEntity> getAllOrderByYear() {
        return baseMapper.selectList(new QueryWrapper<SysCollegePracEntity>().orderByDesc("year"));
    }
}
