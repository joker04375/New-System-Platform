package net.maku.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import net.maku.student.dao.SysStuResumeDao;
import net.maku.student.entity.SysStuResumeEntity;
import net.maku.framework.common.service.impl.BaseServiceImpl;
import net.maku.framework.security.user.SecurityUser;
import net.maku.student.service.SysStuResumeService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SysStuResumeServiceImpl extends BaseServiceImpl<SysStuResumeDao, SysStuResumeEntity> implements SysStuResumeService {
    private SysStuResumeDao sysStuResumeDao;

    @Override
    public SysStuResumeEntity selectByUserId(Long userId) {
        //根据当前用户id
        LambdaQueryWrapper<SysStuResumeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysStuResumeEntity::getUserId,userId);
        SysStuResumeEntity sysStuResumeEntity = sysStuResumeDao.selectOne(queryWrapper);
        return sysStuResumeEntity;
    }


    @Override
    public void deleteResumeByUserId() {
        //根据当前用户id
        LambdaQueryWrapper<SysStuResumeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysStuResumeEntity::getUserId,SecurityUser.getUserId());

        //删除
        sysStuResumeDao.delete(queryWrapper);
    }
}
