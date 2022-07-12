package net.maku.student.service;

import net.maku.student.entity.ModelFileEntity;

public interface SysStuFileService {
    ModelFileEntity selectModelFilesByStuId(Long userId);
}
