package com.zxz.server.controller;


import com.zxz.server.pojo.Employee;
import com.zxz.server.pojo.RespPageBean;
import com.zxz.server.service.IEmployeeService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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

    @ApiModelProperty("获取所有员工分页")
    @GetMapping("")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size, Employee employee, LocalDate[] beginDateScope) {
        return employeeService.getEmployeeByPage(currentPage,size,employee,beginDateScope);
    }

}
