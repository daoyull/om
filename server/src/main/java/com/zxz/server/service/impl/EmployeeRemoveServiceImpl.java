package com.zxz.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxz.server.mapper.EmployeeRemoveMapper;
import com.zxz.server.pojo.EmployeeRemove;
import com.zxz.server.service.IEmployeeRemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@Service
public class EmployeeRemoveServiceImpl extends ServiceImpl<EmployeeRemoveMapper, EmployeeRemove> implements IEmployeeRemoveService {

    @Autowired
    private EmployeeRemoveMapper employeeRemoveMapper;

    @Override
    public List<EmployeeRemove> getAllMsg() {
        return employeeRemoveMapper.getAllMsg();
    }
}
