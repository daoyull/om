package com.zxz.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxz.server.mapper.SalaryAdjustMapper;
import com.zxz.server.pojo.SalaryAdjust;
import com.zxz.server.service.ISalaryAdjustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@Service
public class SalaryAdjustServiceImpl extends ServiceImpl<SalaryAdjustMapper, SalaryAdjust> implements ISalaryAdjustService {


    @Autowired
    private SalaryAdjustMapper salaryAdjustMapper;


    @Override
    public SalaryAdjust getSalaryById(Integer id) {
        return salaryAdjustMapper.getSalaryById(id);
    }
}
