package net.maku.student.dao;

import net.maku.student.entity.ModelFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysStuFileDao {
    /**
     * 根据学生id查找发布的模板文件
     * @param userId
     * @return
     */
    ModelFileEntity selectModelFilesByStuId(@Param("userId") Long userId);
}
