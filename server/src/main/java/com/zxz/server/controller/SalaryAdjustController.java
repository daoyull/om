package com.zxz.server.controller;


import com.zxz.server.pojo.EmployeeTrain;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.pojo.SalaryAdjust;
import com.zxz.server.service.IEmployeeService;
import com.zxz.server.service.ISalaryAdjustService;
import com.zxz.server.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@RestController
@RequestMapping("/salary-adjust")
public class SalaryAdjustController {

    @Autowired
    private ISalaryAdjustService salaryAdjustService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation("员工奖金调整")
    @PostMapping("/add")
    public Object updataSalary(@RequestBody SalaryAdjust salaryAdjust){
        salaryAdjust.setAsDate(LocalDate.now());
        if(salaryAdjustService.save(salaryAdjust)){
            return RespBean.success("成功");
        }
        return RespBean.error("失败");
    }

    @ApiOperation("获取列表")
    @GetMapping("/all")
    public Object getAll(){
        List<SalaryAdjust> list = salaryAdjustService.list();
        for (SalaryAdjust salaryAdjust : list) {
            salaryAdjust.setName(employeeService.getById(salaryAdjust.getEid()).getName());
        }
        return list;
    }

    @ApiOperation("获取奖金")
    @GetMapping("/select")
    public void getSalary(Integer id){
        SalaryAdjust salary = salaryAdjustService.getSalaryById(id);

        if (null!=salary){

        }
    }


}
