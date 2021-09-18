package com.zxz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxz.server.pojo.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartmentsByParentId(Integer parentId);

    void addDep(Department department);

    void deleteDep(Department dep);
}
