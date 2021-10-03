package com.zxz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxz.server.pojo.Department;
import com.zxz.server.pojo.Employee;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     *
     * @param currentPage
     * @param size
     * @param employee
     * @param localDates
     * @return
     */
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);


    RespBean insertEmployee(Employee employee);

    RespBean maxWorkId();


    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
