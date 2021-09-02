package com.jdd.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.jdd.server.pojo.*;
import com.jdd.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2021-08-31
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private INationService nationService;

    @ApiOperation(value = "查询所有员工(分页)")
    @GetMapping("/")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    Employee employee,
                                    // 入职日期是范围,传入两个日期(开始和结束)
                                    LocalDate[] beginDateScope){
        return employeeService.getEmployeeByPage(currentPage,pageSize,employee,beginDateScope);
    }

    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/politicsstatus")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }
    @ApiOperation(value = "获取所有职称")
    @GetMapping("/joblevel")
    public List<Joblevel> getAllJoblevels(){
        return joblevelService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/position")
    public List<Position> getAllPositions(){
        return positionService.list();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/department")
    public List<Department> getAllDepartments(){
        return departmentService.list();
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nation")
    public List<Nation> getAllNations(){
        return nationService.list();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkId")
    public RespBean getMaxWorkId(){
        return employeeService.getMaxWorkId();
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public RespBean addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public RespBean updateEmployee(@RequestBody Employee employee){
        if (employeeService.updateById(employee)){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployee(@PathVariable Integer id){
        if (employeeService.removeById(id)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }


    // ======================= 数据导出 =====================
    @ApiOperation(value = "数据导出")
    @GetMapping(value = "/export",produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response){
        // 这里没有传ID进去,所以查询的是所有的员工信息
        List<Employee> employeeList = employeeService.getEmployee(null);
        ExportParams exportParams =new ExportParams("员工表","员工表", ExcelType.HSSF);
        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, Employee.class, employeeList);


        // 流输出
        ServletOutputStream outputStream =null;
        try {
            // 流形式
            response.setHeader("content-type", "application/octet-stream");
            // 防止中文乱码
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @ApiOperation(value = "数据导入")
    @PostMapping(value = "/import")
    public RespBean importEmployee(MultipartFile file){
        ImportParams importParams = new ImportParams();
        // 去掉标题行,不然会报错
        importParams.setTitleRows(1);
        // 查询全部民族信息(类似一个id==name表,可通过name去查找id)


        List<Employee> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, importParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 民族信息比对
        List<Nation> nationList = nationService.list();

        for (Employee employee : list) {
            // 这个地方就使用到了hashCode和equals方法
            int index = nationList.indexOf(new Nation(employee.getNation().getName()));
            // 通过下标去获取id
            Integer id = nationList.get(index).getId();
            // 设置进去
            employee.setNationId(id);
        }
        // 政治面貌信息比对
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        for (Employee employee : list) {
            // 这个地方就使用到了hashCode和equals方法
            int index = politicsStatusList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()));
            // 通过下标去获取id
            Integer id = nationList.get(index).getId();
            // 设置进去
            employee.setPoliticId(id);
        }
        // 职位信息比对
        List<Position> positionList = positionService.list();
        for (Employee employee : list) {
            // 这个地方就使用到了hashCode和equals方法
            int index = positionList.indexOf(new Position(employee.getPosition().getName()));
            // 通过下标去获取id
            Integer id = positionList.get(index).getId();
            // 设置进去
            employee.setPosId(id);
        }
        // 职称信息比对
        List<Joblevel> joblevelList = joblevelService.list();
        for (Employee employee : list) {
            // 这个地方就使用到了hashCode和equals方法
            int index = joblevelList.indexOf(new Joblevel(employee.getJoblevel().getName()));
            // 通过下标去获取id
            Integer id = joblevelList.get(index).getId();
            // 设置进去
            employee.setJobLevelId(id);
        }
        // 职称信息比对
        List<Department> departmentList = departmentService.list();
        for (Employee employee : list) {
            // 这个地方就使用到了hashCode和equals方法
            int index = departmentList.indexOf(new Department(employee.getDepartment().getName()));
            // 通过下标去获取id
            Integer id = departmentList.get(index).getId();
            // 设置进去
            employee.setDepartmentId(id);
        }
        // 处理完成后进行插入数据
        if(employeeService.saveBatch(list)){
            return RespBean.success("导入成功!");
        }
        return RespBean.error("导入失败!");

    }
}
