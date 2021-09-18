package com.zxz.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxz.server.mapper.DepartmentMapper;
import com.zxz.server.pojo.Department;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.service.IDepartmentService;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @Override
    public RespBean addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
        if(1==department.getResult()){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @Override
    public RespBean deleteDep(Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentMapper.deleteDep(dep);
        if (-2==dep.getResult()){
            return RespBean.error("该部门下有子部门，删除失败！");
        }
        if (-1==dep.getResult()){
            return RespBean.error("该部门下有员工，删除失败！");
        }
        if (1==dep.getResult()){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

}
