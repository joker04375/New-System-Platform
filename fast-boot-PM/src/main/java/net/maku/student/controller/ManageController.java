package net.maku.student.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.enterprise.entity.SysOrgPracStuEntity;
import net.maku.framework.common.utils.Result;
import net.maku.framework.security.user.SecurityUser;

import net.maku.student.entity.SysStuPracTimeEntity;
import net.maku.student.service.SysStuManageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("sys/stu/manage")
@AllArgsConstructor
@Tag(name = "我的实习管理")
public class ManageController {
    private final SysStuManageService sysStuManageService;
//    private final SysStuPracTimeService sysStuPracService;

    /**
     * 实习申报
     * @return
     */
    @PostMapping("post/apply")
    public Result applyPost(@RequestBody SysOrgPracStuEntity sysOrgPracStuEntity) {
        sysStuManageService.save(sysOrgPracStuEntity);
        return Result.ok("申报成功");
    }


    /**
     * 我的实习
     * @return
     */
    @GetMapping("myPracInfo")
    public Result getMyPracInfo() {
//        List<SysStuPracTimeEntity> sysStuPracEntities= sysStuPracService.getMyPracByUserId(SecurityUser.getUserId());
        List<SysStuPracTimeEntity> myPracByUserId = sysStuManageService.getMyPracByUserId(SecurityUser.getUserId());
        return Result.ok(myPracByUserId);
    }


    /**
     * 实习周报
     * @return
     */
    @PostMapping()
    public Result postMonthReport(){
        return Result.ok();
    }



}
