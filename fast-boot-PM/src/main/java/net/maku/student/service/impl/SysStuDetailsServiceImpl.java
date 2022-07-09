package net.maku.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import net.maku.student.dao.SysStuDetailsDao;
import net.maku.student.entity.SysStuDetailsEntity;
import net.maku.framework.common.service.impl.BaseServiceImpl;
import net.maku.student.service.SysStuDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SysStuDetailsServiceImpl extends BaseServiceImpl<SysStuDetailsDao, SysStuDetailsEntity> implements SysStuDetailsService {
    private final SysStuDetailsDao sysStuDetailsDao;

    @Override
    public SysStuDetailsEntity getByStuNum(String stuNum) {
        LambdaQueryWrapper<SysStuDetailsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysStuDetailsEntity::getStuNum,stuNum);
        SysStuDetailsEntity sysStuDetailsEntity = sysStuDetailsDao.selectOne(queryWrapper);
        return sysStuDetailsEntity;
    }
}
