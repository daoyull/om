package com.zxz.server.controller;


import com.zxz.server.pojo.Employee;
import com.zxz.server.pojo.EmployeeTrain;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.service.IEmployeeService;
import com.zxz.server.service.IEmployeeTrainService;
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
@RequestMapping("/employee-train")
public class EmployeeTrainController {

    @Autowired
    private IEmployeeTrainService employeeTrainService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation("员工培训")
    @PostMapping("/add")
    public Object employeeTrain(@RequestBody EmployeeTrain employeeTrain){
        employeeTrain.setTrainDate(LocalDate.now());
        if(employeeTrainService.save(employeeTrain)){
            return RespBean.success("成功");
        }
        return RespBean.error("失败");
    }

    @ApiOperation("员工培训记录")
    @GetMapping("/all")
    public List<EmployeeTrain> getAll(){
        List<EmployeeTrain> list = employeeTrainService.list();
        for (EmployeeTrain employeeTrain : list) {
            employeeTrain.setName(employeeService.getById(employeeTrain.getEid()).getName());
        }
        return list;
    }

}
