package net.maku.enterprise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import net.maku.enterprise.dao.SysOrgPracPostDao;
import net.maku.enterprise.dto.SysAllOrgPostDto;
import net.maku.enterprise.entity.SysOrgPracPostEntity;
import net.maku.enterprise.service.SysOrgPracPostService;
import net.maku.framework.common.service.impl.BaseServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * @description:企业岗位管理
 * @author: 25652
 * @time: 2022/6/15 18:24
 */
@Service
@AllArgsConstructor
public class SysOrgPracPostServiceImpl extends BaseServiceImpl<SysOrgPracPostDao, SysOrgPracPostEntity>
        implements SysOrgPracPostService {

    private PlatformTransactionManager transactionManager;
    private SysOrgPracPostDao sysOrgPracPostDao;


    @Override
    public SysOrgPracPostEntity getOnePracPostMessage(Long Id)
    {
        SysOrgPracPostEntity sysOrgPracPostEntity = baseMapper.selectById(Id);
        return sysOrgPracPostEntity;
    }

    @Override
    public List<SysOrgPracPostEntity> getAllPracPostMessage(Long orgId ,Long pracId){
        List<SysOrgPracPostEntity> list = baseMapper.selectList(new QueryWrapper<SysOrgPracPostEntity>()
                .eq("org_id",orgId)
                .eq("prac_id",pracId));
        return list;
    }

    @Override
    public Boolean update(SysOrgPracPostEntity sysOrgPracPostEntity) {
        SysOrgPracPostEntity entity = baseMapper.selectOne(new QueryWrapper<SysOrgPracPostEntity>()
                .eq("id",sysOrgPracPostEntity.getId())
                .eq("org_id", sysOrgPracPostEntity.getOrgId())
                .eq("prac_id", sysOrgPracPostEntity.getPracId())
                .eq("post_id", sysOrgPracPostEntity.getPostId()));
        if(entity==null)
        {
            return false;
        }
        baseMapper.updateById(sysOrgPracPostEntity);
        return true;
    }

    @Override
    @CacheEvict(cacheManager = "redisCacheManager", value = "'orgPost'", key = "'postId' + #postId")
    public Boolean delete(Long Id,Long orgId,Long pracId,Long postId) {
        SysOrgPracPostEntity entity = baseMapper.selectOne(new QueryWrapper<SysOrgPracPostEntity>()
                .eq("id",Id)
                .eq("org_id", orgId)
                .eq("prac_id", pracId)
                .eq("post_id", postId));
        if(entity==null)
        {
            return false;
        }
        baseMapper.deleteById(Id);
        return true;
    }

    @Override
    @Cacheable(cacheManager = "redisCacheManager", value = "orgPost", key = "allOrgPost")
    public List<SysAllOrgPostDto> getAllOrgPost(){
        List<SysAllOrgPostDto> allOrgPost = sysOrgPracPostDao.getAllOrgPost();
        return allOrgPost;
    }

    @Override
    public void changePostStatus(Integer id, Integer status) {
        SysOrgPracPostEntity sysOrgPracPostEntity = baseMapper.selectById(id);
        sysOrgPracPostEntity.setStatus(status);
        sysOrgPracPostEntity.setDeleted(1);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            baseMapper.updateById(sysOrgPracPostEntity);
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
        }
    }

    @Override
    public List<SysOrgPracPostEntity> getConditionPost(String condition){
        List<SysOrgPracPostEntity> postEntities = baseMapper.selectList(new QueryWrapper<SysOrgPracPostEntity>().eq("visible", 0)
                .like("post_name", condition));
        return postEntities;
    }

}
