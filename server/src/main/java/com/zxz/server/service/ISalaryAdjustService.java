package com.zxz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxz.server.pojo.SalaryAdjust;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface ISalaryAdjustService extends IService<SalaryAdjust> {

    SalaryAdjust getSalaryById(Integer id);
}
