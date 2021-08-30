package com.jdd.server.controller;


import com.jdd.server.pojo.Joblevel;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "获取所有职称信息")
    @GetMapping("/")
    public List<Joblevel> getAllJoblevel(){
        // 这个list是mybatis plus自带的
        return joblevelService.list();
    }

    @ApiOperation(value = "增加职称信息")
    @PostMapping("/")
    public RespBean addJoblevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        // 插件自带的
        if (joblevelService.save(joblevel)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }


    @ApiOperation(value = "更改职称信息")
    @PutMapping("/")
    public RespBean updateJoblevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.updateById(joblevel)){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除职称信息")
    @DeleteMapping ("/{id}")
    public RespBean deleteJoblevel(@PathVariable Integer id){

        if (joblevelService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除职称信息")
    @DeleteMapping("/")
    public RespBean mtlDeleteJoblevel(Integer[] ids){

        if (joblevelService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }


}
