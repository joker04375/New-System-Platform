package net.maku.student.controller;

import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.Result;
import net.maku.framework.security.user.SecurityUser;
import net.maku.student.entity.ModelFileEntity;
import net.maku.student.service.SysStuFileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/stu/file")
@AllArgsConstructor
public class FileController {

    private final SysStuFileService sysStuFileService;
    @GetMapping("getModelFiles")
    public Result getModelFiles(){
        ModelFileEntity upLoadFileEntity = sysStuFileService.selectModelFilesByStuId(SecurityUser.getUserId());
        return Result.ok(upLoadFileEntity);
    }

}
