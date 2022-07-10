package net.maku.college.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.college.entity.SysCollegePracEntity;
import net.maku.college.service.SysCollegePracService;
import net.maku.enterprise.dto.SysAllOrgPracDto;
import net.maku.enterprise.entity.*;
import net.maku.enterprise.service.*;
import net.maku.framework.common.page.PageResult;
import net.maku.framework.common.query.Query;
import net.maku.framework.common.service.SysPublicFileService;
import net.maku.framework.common.utils.FileUtils;
import net.maku.framework.common.utils.PageListUtils;
import net.maku.framework.common.utils.Result;
import net.maku.student.entity.SysStuPostEntity;
import net.maku.student.service.SysStuManageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/college/prac")
@Tag(name="实习管理")
@AllArgsConstructor
public class CollegePracMangerController {

    private final SysOrgPracManageService sysOrgPracManageService;

    private final SysOrgPracPostService sysOrgPracPostService;

    private final SysOrgPracStuService sysOrgPracStuService;

    private final SysOrgPracInterviewService sysOrgPracInterviewService;

    private final SysOrgPracFileService sysOrgPracFileService;

    private final SysPublicFileService sysPublicFileService;

    private final SysOrgCollegePracService sysOrgCollegePracService;

    private final SysStuManageService sysStuManageService;

    private final SysCollegePracService sysCollegePracService;

    @GetMapping("specificTimePracs")
    @Operation(summary = "查看某一学院某一批次下所有实习项目")
    public Result<PageResult<SysAllOrgPracDto>> getAllPracsByCollegeAndTime(Query query, long collegeId, long timeId) {
        // 中间表通过CollegeId和TimeId查询到这一学院这一批次下的所有实习id
        List<SysOrgCollegePracEntity> pracsInfo = sysOrgCollegePracService.selectOrgByCollegeIdAndTimeID(collegeId, timeId);
        List<SysAllOrgPracDto> result = new ArrayList<>();
        for (SysOrgCollegePracEntity info : pracsInfo) {
            result.add(sysOrgPracManageService.getByOrgAndPracId(info.getOrgId(),info.getOrgPracId()));
        }
        // 进行分页
        Page pages = PageListUtils.getPages(query.getPage(), query.getLimit(), result);
        PageResult<SysAllOrgPracDto> page = new PageResult<>(pages.getRecords(), pages.getTotal());
        return Result.ok(page);
    }

    @GetMapping("specificimeStus")
    @Operation(summary = "查看某一学院某一批次下所有实习学生")
    public Result<PageResult<SysOrgPracStuEntity>> getAllStusByCollegeAndTime(Query query,long collegeId,long timeId) {
        // 中间表通过CollegeId和TimeId查询到这一学院这一批次下的所有实习id
        List<SysOrgCollegePracEntity> pracsInfo = sysOrgCollegePracService.selectOrgByCollegeIdAndTimeID(collegeId,timeId);
        List<SysOrgPracStuEntity> result = new ArrayList<>();
        for (SysOrgCollegePracEntity info : pracsInfo) {
            result.addAll(sysOrgPracStuService.getAllPracStuMessage(info.getOrgId(), info.getOrgPracId()));
        }
        // 进行分页
        Page pages = PageListUtils.getPages(query.getPage(), query.getLimit(), result);
        PageResult<SysOrgPracStuEntity> page = new PageResult<>(pages.getRecords(), pages.getTotal());
        return Result.ok(page);
    }

    /*
     * Prams:
     *   year:年份
     *   name:公司名称
     *   query:查询语句
     * */
    @GetMapping("search")
    @Operation(summary = "实习页面根据条件进行查询")
    public Result<PageResult<SysAllOrgPracDto>> getPracsByConditions(Query query,@RequestParam(required = false) Map<String,String> conditions) {
        List<SysAllOrgPracDto> pracs = sysOrgPracManageService.getPracsByConditions(conditions);
        // 进行分页
        Page pages = PageListUtils.getPages(query.getPage(), query.getLimit(), pracs);
        PageResult<SysAllOrgPracDto> page = new PageResult<>(pages.getRecords(), pages.getTotal());
        return Result.ok(page);
    }

    @GetMapping("/post/{orgId}/{pracId}")
    @Operation(summary = "获取某个实习项目下的所有岗位")
    public Result<List<SysOrgPracPostEntity>> getAllPostByOrgId(@PathVariable(name = "orgId") long orgId, @PathVariable(name = "pracId") long pracId) {
        List<SysOrgPracPostEntity> allPracPostMessage = sysOrgPracPostService.getAllPracPostMessage(orgId, pracId);
        return Result.ok(allPracPostMessage);
    }

    @PutMapping("post/status/{id}/{status}")
    @Operation(summary = "是否通过该企业实习项目中的岗位")
    public Result<String> isAcceptPost(@PathVariable(name = "id") int id, @PathVariable(name = "status") int status){
        sysOrgPracPostService.changePostStatus(id,status);
        return Result.ok("success");
    }

    @GetMapping("post/stu/{orgId}/{pracId}")
    @Operation(summary = "对企业实习项目中学生的查看")
    public Result<PageResult<SysOrgPracStuEntity>> getAllStuById(Query query, @PathVariable(name = "orgId") long orgId, @PathVariable(name = "pracId") long pracId) {
        List<SysOrgPracStuEntity> resultList = new ArrayList<>();
        // 对学生状态继续过滤（在实习中还是已结束）
        for (SysOrgPracStuEntity stuEntity : sysOrgPracStuService.getAllPracStuMessage(orgId, pracId)) {
            if(stuEntity.getStatus()== -2 || stuEntity.getStatus() == 2) {
                resultList.add(stuEntity);
            }
        }
        // 进行分页
        Page pages = PageListUtils.getPages(query.getPage(), query.getLimit(), resultList);
        PageResult<SysOrgPracStuEntity> page = new PageResult<>(pages.getRecords(), pages.getTotal());
        return Result.ok(page);
    }

    @GetMapping("post/interview/{orgId}/{pracId}")
    @Operation(summary = "获取笔试面试管理信息")
    public Result<List<SysOrgPracInterviewEntity>> getAllInterview(@PathVariable(name = "orgId") long orgId, @PathVariable(name = "pracId") long pracId) {
        List<SysOrgPracInterviewEntity> allInterviews = sysOrgPracInterviewService.getAllInterviews(orgId, pracId);
        return Result.ok(allInterviews);
    }

    @GetMapping("home")
    @Operation(summary = "查看该学院下发布的实习批次（根据年份排序）")
    public Result<List<SysCollegePracEntity>> getAllPracsByStatus(Query query) {
        List<SysCollegePracEntity> orgPracs = sysCollegePracService.getAllOrderByYear();
        for (SysCollegePracEntity collegePrac : orgPracs) {
            long collegeId = collegePrac.getCollegeId();
            long timeId = collegePrac.getTimeId();
            // 中间表通过CollegeId和TimeId查询到这一学院这一批次下的所有实习id
            List<SysOrgCollegePracEntity> pracsInfo = sysOrgCollegePracService.selectOrgByCollegeIdAndTimeID(collegeId, timeId);
            List<Long> pracIds = new ArrayList<>();
            for (SysOrgCollegePracEntity pracEntity : pracsInfo) {
                pracIds.add(pracEntity.getOrgPracId());
            }
            long count = sysStuManageService.count(new QueryWrapper<SysStuPostEntity>().in("prac_id", pracIds));
            collegePrac.setStuNum(count);
        }
        return Result.ok(orgPracs);
    }



    @PostMapping("postInternship")
    @Operation(summary = "学院发表单期实习")
    public Result<String> postInternship(@RequestParam("year") String year, @RequestParam("name") String quarter,@RequestParam("college_id") int collegeId) {
        sysCollegePracService.postInternship(year,quarter,collegeId);
        return Result.ok("Success");
    }

    @PostMapping("post/fileUpload/{orgId}/{pracId}")
    @Operation(summary = "上传公共文件")
    public Result<String> submitForm(@RequestParam("file") MultipartFile file,
                                     @RequestParam("fileName") String fileName,
                                     @RequestParam("fileType") String fileType,
                                     @RequestParam("uploader") String uploader,
                                     @RequestParam("uploadTime") String uploadTime,
                                     @PathVariable("orgId") long orgId,
                                     @PathVariable("pracId") long pracId
                                     ) {
        // 上传文件至本地，返回存储路径
        String storagePath = FileUtils.uploadCommonFile(file);

        SysOrgPracFileEntity sysOrgPracFileEntity = new SysOrgPracFileEntity();

        sysOrgPracFileEntity.setFileAddr(storagePath);
        sysOrgPracFileEntity.setFileName(fileName);
        sysOrgPracFileEntity.setFileType(fileType);
        sysOrgPracFileEntity.setUploader(uploader);
        // 1 代表公共文件(直接设置为1，这个接口是用来上传公共文件的)
        sysOrgPracFileEntity.setIsCommon(1);

            // 作用在时间戳
        //long timeLong = Long.parseLong(uploadTime);
        //date = simpleDateFormat.parse(simpleDateFormat.format(timeLong));
        //sysOrgPracFileEntity.setUploadTime(new java.sql.Date(date.getTime()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(uploadTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        java.sql.Timestamp timestamp = new Timestamp(date.getTime());
        sysOrgPracFileEntity.setUploadTime(String.valueOf(timestamp));

        sysOrgPracFileEntity.setOrgId(orgId);
        sysOrgPracFileEntity.setPracId(pracId);

        sysOrgPracFileService.save(sysOrgPracFileEntity);


        return Result.ok("success");
    }

}
