package net.maku.student.controller;

import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/stu/file")
@AllArgsConstructor
public class FileController {

    @GetMapping("getFiles")
    public Result getModelFiles(){
        return Result.ok();
    }

}
