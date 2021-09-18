package com.zxz.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxz.server.mapper.DepartmentMapper;
import com.zxz.server.mapper.EmployeeMapper;
import com.zxz.server.pojo.Department;
import com.zxz.server.pojo.Employee;
import com.zxz.server.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {


}
