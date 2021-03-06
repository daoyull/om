package com.zxz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxz.server.pojo.Employee;
import com.zxz.server.pojo.RespPageBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工分页查询
     * @param employeePage
     * @param employee
     * @param beginDateScope
     * @return
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> employeePage, @Param("employee") Employee employee, @Param("beginDateScope")  LocalDate[] beginDateScope);

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);

    @Select("SELECT id \n" +
            "from t_employee \n" +
            "ORDER BY id DESC\n" +
            "LIMIT 0,1")
    Integer getMaxWordId();
}
