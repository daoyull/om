package com.zxz.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxz.server.mapper.DepartmentMapper;
import com.zxz.server.mapper.EmployeeMapper;
import com.zxz.server.pojo.Department;
import com.zxz.server.pojo.Employee;
import com.zxz.server.pojo.RespPageBean;
import com.zxz.server.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * @param currentPage
     * @param size
     * @param employee
     * @param localDates
     * @return
     */
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeePage = employeeMapper.getEmployeeByPage(page,
                employee, beginDateScope);
        RespPageBean respPageBean = new
                RespPageBean(employeePage.getTotal(), employeePage.getRecords());
        return respPageBean;

    }
}
