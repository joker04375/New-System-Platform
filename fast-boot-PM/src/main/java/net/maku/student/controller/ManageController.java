package net.maku.student.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.enterprise.dto.SysAllOrgPostDto;
import net.maku.enterprise.service.SysOrgPracPostService;
import net.maku.framework.common.page.PageResult;
import net.maku.framework.common.query.Query;
import net.maku.framework.common.utils.PageListUtils;
import net.maku.framework.common.utils.Result;
import net.maku.student.entity.SysStuPostEntity;
import net.maku.student.service.SysStuManageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("sys/stu/manage")
@AllArgsConstructor
@Tag(name = "实习管理")
public class ManageController {

    private final SysOrgPracPostService sysOrgPracPostService;
    private final SysStuManageService sysStuManageService;

    /**
     * 岗位浏览
     * @return
     */
    @RequestMapping("post")
    public Result<PageResult<SysAllOrgPostDto>> selectAllPost(@Valid Query query){
        List<SysAllOrgPostDto> allOrgPost = sysOrgPracPostService.getAllOrgPost();

        //进行分页
        Page pages = PageListUtils.getPages(query.getPage(), query.getLimit(), allOrgPost);
        PageResult<SysAllOrgPostDto> pageResult = new PageResult<SysAllOrgPostDto>(pages.getRecords(), pages.getTotal());

        return Result.ok(pageResult);
    }

    /**
     * 实习申报
     * @return
     */
    @PostMapping("sys/stu/manage/post/apply")
    public Result applyPost(@RequestBody SysStuPostEntity sysStuPostEntity){
        sysStuManageService.save(sysStuPostEntity);

        return Result.ok();
    }
}
