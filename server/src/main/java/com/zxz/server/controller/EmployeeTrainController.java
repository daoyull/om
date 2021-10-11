package com.zxz.server.controller;


import com.zxz.server.pojo.Employee;
import com.zxz.server.pojo.EmployeeTrain;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.service.IEmployeeTrainService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@RestController
@RequestMapping("/employee-train")
public class EmployeeTrainController {

    @Autowired
    private IEmployeeTrainService employeeTrainService;

    @ApiOperation("员工培训")
    @PostMapping("/add")
    public Object employeeTrain(@RequestBody EmployeeTrain employeeTrain){
        employeeTrain.setTrainDate(LocalDate.now());
        if(employeeTrainService.save(employeeTrain)){
            return RespBean.success("成功");
        }
        return RespBean.error("失败");
    }

}
