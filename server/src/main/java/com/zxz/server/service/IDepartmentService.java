package com.zxz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxz.server.pojo.Department;
import com.zxz.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> getAllDepartments();

    RespBean addDep(Department department);

    RespBean deleteDep(Integer id);
}
