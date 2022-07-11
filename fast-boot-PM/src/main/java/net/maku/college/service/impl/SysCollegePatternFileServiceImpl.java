package net.maku.college.service.impl;

import lombok.AllArgsConstructor;
import net.maku.college.dao.SysCollegePatternFileDao;
import net.maku.college.entity.SysCollegePatternFileEntity;
import net.maku.college.service.SysCollegePatternFileService;
import net.maku.framework.common.entity.SysPublicFileEntity;
import net.maku.framework.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class SysCollegePatternFileServiceImpl extends BaseServiceImpl<SysCollegePatternFileDao, SysCollegePatternFileEntity> implements SysCollegePatternFileService {
}
