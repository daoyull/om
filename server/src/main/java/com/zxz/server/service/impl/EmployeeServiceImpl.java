package com.zxz.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxz.server.mapper.DepartmentMapper;
import com.zxz.server.mapper.EmployeeMapper;
import com.zxz.server.pojo.*;
import com.zxz.server.service.IEmployeeService;
import com.zxz.server.service.ISalaryAdjustService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ISalaryAdjustService salaryAdjustService;


    /**
     * @param currentPage
     * @param size
     * @param employee
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

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */

    @Override
    public RespBean insertEmployee(Employee employee) {
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days / 365.00))
        );
        if (1 == employeeMapper.insert(employee)) {
            Employee emp = employeeMapper.getEmployee(employee.getId()).get(0);
            rabbitTemplate.convertAndSend("mail.welcome", emp);

            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @Override
    public RespBean maxWorkId() {
        Integer maxWordId = employeeMapper.getMaxWordId();
        return RespBean.success(null, maxWordId + 1);
    }

    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }

    @Override
    public RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeePage = employeeMapper.getEmployeeWithSalary(page);
        List<Employee> records = employeePage.getRecords();
        for (Employee record : records) {
            Integer res = 0;
            SalaryAdjust salary = salaryAdjustService.getSalaryById(record.getId());
            if(null!=salary){
                 res = salary.getAfterSalary()-salary.getBeforeSalary();
            }

           double a = record.getSalary().getBasicSalary()+record.getSalary().getBonus()+record.getSalary().getTrafficSalary()
                    +record.getSalary().getPensionPer()*record.getSalary().getPensionBase()+record.getSalary().getMedicalBase()*record.getSalary().getMedicalPer()
                   +record.getSalary().getAccumulationFundBase()*record.getSalary().getAccumulationFundPer();
            record.setAllSalary((int) (a+res));
        }
        employeePage.setRecords(records);
        RespPageBean respPageBean = new RespPageBean(employeePage.getTotal(),
                employeePage.getRecords());
        return respPageBean;
    }

}
