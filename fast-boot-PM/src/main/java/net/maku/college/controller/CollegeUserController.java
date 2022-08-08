package net.maku.college.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.college.constant.CollegeEnum;
import net.maku.college.request.userRequest;
import net.maku.college.request.userType;
import net.maku.enterprise.common.OrgUtils;
import net.maku.framework.common.page.PageResult;
import net.maku.framework.common.query.Query;
import net.maku.framework.common.utils.PageListUtils;
import net.maku.framework.common.utils.Result;
import net.maku.college.service.SysCollegeUserService;
import net.maku.student.entity.SysStuDetailsEntity;
import net.maku.student.service.SysStuDetailsService;
import net.maku.system.entity.SysUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("sys/college/user")
@Tag(name="学院用户管理")
@AllArgsConstructor
public class CollegeUserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CollegeUserController.class);
    private final SysCollegeUserService sysCollegeUserService;

    private final SysStuDetailsService sysStuDetailsService;


    @GetMapping("/info/{collegeId}/{roleName}")
    @Operation(summary = "用户管理（针对不同角色以及不同学院）")
    public Result<PageResult<SysUserEntity>> getAllStudents(Query query,@PathVariable(name = "collegeId") long collegeId, @PathVariable(name = "roleName") String roleName) {
        List<SysUserEntity> students = sysCollegeUserService.getAllUserByRoleAndOrg(roleName,collegeId);
        // 进行分页
        Page pages = PageListUtils.getPages(query.getPage(), query.getLimit(), students);
        PageResult<SysUserEntity> page = new PageResult<>(pages.getRecords(), pages.getTotal());
        return Result.ok(page);
    }

//    @GetMapping("/upload")
//    public void UploadFile(@RequestParam("file") MultipartFile file){
//        int orgId = 1;
//        int pracId = 1;
//        String path = sysPublicFileService.CreatePublicFile(orgId, pracId, file);
//        System.out.println(path);
//    }

    @PostMapping("userRegister")
    @Operation(summary = "增加用户（根据类型不同进行判断）")
    public Result<String> addUser(@RequestBody userRequest user) {
        long count = 0;
        String rawPassword = "";
        SysUserEntity userInfo = new SysUserEntity();
        switch (user.getType()) {
            case userType.STUDENT:
                count = sysCollegeUserService.count(new QueryWrapper<SysUserEntity>().eq("work_number", user.getWorkNumber()));
                if(count!=0) {
                    LOGGER.error("已经存在该用户，无需再新增用户");
                    return Result.error("已经存在该用户，无需再新增用户");
                }
                // 如果可以的话 后续改为身份证后六位
                rawPassword = "sztu@" + user.getWorkNumber().substring(user.getWorkNumber().length()-6);
                userInfo.setWorkNumber(user.getWorkNumber());
                userInfo.setOrgId((long)user.getOrgId());
                userInfo.setPassword(encryptPassword(rawPassword));
                userInfo.setRole(1);
                // 存储到总用户表
                boolean save = sysCollegeUserService.save(userInfo);

                String colName = CollegeEnum.getColName(user.getOrgId());
                SysStuDetailsEntity stu = new SysStuDetailsEntity();
                stu.setStuId(Long.parseLong(user.getWorkNumber()));
                stu.setCollege(colName);
                // 存储到学生表
                boolean flag = sysStuDetailsService.save(stu);
                break;
            case userType.TEACHER:
                count = sysCollegeUserService.count(new QueryWrapper<SysUserEntity>().eq("work_number", user.getWorkNumber()));
                if(count!=0) {
                    LOGGER.error("已经存在该用户，无需再新增用户");
                    return Result.error("已经存在该用户，无需再新增用户");
                }
                // 如果可以的话 后续改为身份证后六位
                rawPassword = "sztu@" + user.getWorkNumber().substring(user.getWorkNumber().length()-6);
                userInfo.setWorkNumber(user.getWorkNumber());
                userInfo.setOrgId((long)user.getOrgId());
                userInfo.setPassword(encryptPassword(rawPassword));
                userInfo.setRole(2);
                // 存储到总用户表
                sysCollegeUserService.save(userInfo);
                break;
            case userType.ENTERPRISE:
                String raw = OrgUtils.getIdByTime().toString();
                String workNum = raw.substring(raw.length()-6);
                userInfo.setWorkNumber(workNum);
                rawPassword = "sztu@" + workNum;
                userInfo.setPassword(encryptPassword(rawPassword));
                userInfo.setRole(3);
                userInfo.setOrgId((long)user.getOrgId());
                userInfo.setOrgName(CollegeEnum.getColName(user.getOrgId()));
                // 存储到总用户表
                sysCollegeUserService.save(userInfo);
                break;
            default:
                LOGGER.error("no support userType");
                return Result.error("no support userType");
        }
        return Result.ok("user password is:" + rawPassword);
    }

    private String encryptPassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }
}

