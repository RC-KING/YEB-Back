package com.jdd.server.controller;


import com.jdd.server.pojo.Position;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.service.IPositionService;
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
@RequestMapping("/system/basic/position")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPosition(){
        // 这个list是mybatis plus自带的
        return positionService.list();
    }

    @ApiOperation(value = "增加职位信息")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        // 插件自带的
        if (positionService.save(position)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }


    @ApiOperation(value = "更改职位信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if (positionService.updateById(position)){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping ("/{id}")
    public RespBean deletePosition(@PathVariable Integer id){

        if (positionService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping ("/")
    public RespBean mtlDeletePosition(Integer[] ids){

        if (positionService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }






}
