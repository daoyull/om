package com.zxz.server.controller;


import com.zxz.server.pojo.RespBean;
import com.zxz.server.pojo.SalaryAdjust;
import com.zxz.server.service.ISalaryAdjustService;
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
@RequestMapping("/salary-adjust")
public class SalaryAdjustController {

    @Autowired
    private ISalaryAdjustService salaryAdjustService;

    @ApiOperation("员工奖金调整")
    @PostMapping("/add")
    public Object updataSalary(@RequestBody SalaryAdjust salaryAdjust){
        salaryAdjust.setAsDate(LocalDate.now());
        if(salaryAdjustService.save(salaryAdjust)){
            return RespBean.success("成功");
        }
        return RespBean.error("失败");
    }

}
