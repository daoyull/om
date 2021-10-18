package com.zxz.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.zxz.server.pojo.*;
import com.zxz.server.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.ognl.CollectionElementsAccessor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IDepartmentService departmentService;

    @ApiModelProperty("获取所有员工分页")
    @GetMapping("/all")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size, Employee employee, LocalDate[] beginDateScope) {
        return employeeService.getEmployeeByPage(currentPage, size, employee, beginDateScope);
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/add")
    public RespBean addEmp(@RequestBody Employee employee) {
        return employeeService.insertEmployee(employee);
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.list();
    }

    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/politicsstatus")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getAllJobLevels() {
        return joblevelService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return positionService.list();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID() {
        return employeeService.maxWorkId();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/deps")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id) {
        if (employeeService.removeById(id)) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/update")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) {
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }


    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<Employee> list = employeeService.getEmployee(null);
        ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        Workbook book = ExcelExportUtil.exportExcel(params, Employee.class, list);
        ServletOutputStream out = null;
        try {
//流形式
            response.setHeader("content-type", "application/octet-stream");
//防止中文乱码
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode("员工表.xls", "UTF-8"));

            out = response.getOutputStream();
            book.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @ApiOperation(value = "导入员工数据")
//    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "上传文件", dataType
//            = "MultipartFile")})
    @PostMapping("/import")
    public RespBean importEmployee(MultipartFile file) {
        ImportParams params = new ImportParams();
        //去掉标题行
        params.setTitleRows(1);
        HashMap<String, Integer> nationMap = (HashMap<String, Integer>) nationService.list().stream().collect(Collectors.toMap(Nation::getName, Nation::getId));
        HashMap<String, Integer> politicsStatusMap = (HashMap<String, Integer>) politicsStatusService.list().stream().collect(Collectors.toMap(PoliticsStatus::getName, PoliticsStatus::getId));
        HashMap<String, Integer> departmentMap = (HashMap<String, Integer>) departmentService.list().stream().collect(Collectors.toMap(Department::getName, Department::getId));
        HashMap<String, Integer> joblevelMap = (HashMap<String, Integer>) joblevelService.list().stream().collect(Collectors.toMap(Joblevel::getName, Joblevel::getId));
        HashMap<String, Integer> positionMap = (HashMap<String, Integer>) positionService.list().stream().collect(Collectors.toMap(Position::getName, Position::getId));
        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            for (Employee employee : list) {
                employee.setNationId(nationMap.get(employee.getNation().getName()));
                employee.setPoliticId(politicsStatusMap.get(employee.getPoliticsStatus().getName()));
                employee.setDepartmentId(departmentMap.get(employee.getDepartment().getName()));
                employee.setJobLevelId(joblevelMap.get(employee.getJoblevel().getName()));
                employee.setPosId(positionMap.get(employee.getPosition().getName()));
            }

            if (employeeService.saveBatch(list)) {
                return RespBean.success("导入成功");
            }
            return RespBean.error("导入失败!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败!");
    }

}
