package com.zxz.server.controller;


import com.zxz.server.pojo.EmployeeRemove;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.service.IEmployeeRemoveService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/personnel/remove")
public class EmployeeRemoveController {

    @Autowired
    private IEmployeeRemoveService employeeRemoveService;

    @ApiOperation("获取所有数据")
    @PostMapping("/add")
    public RespBean addEmpMv(@RequestBody EmployeeRemove employeeRemove){
        employeeRemove.setRemoveDate(LocalDate.now());
        if(employeeRemoveService.save(employeeRemove)){
            return RespBean.success("调动信息添加成功");
        }
        return RespBean.error("调动信息添加失败");
    }


}
