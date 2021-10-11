package com.zxz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxz.server.pojo.EmployeeRemove;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface EmployeeRemoveMapper extends BaseMapper<EmployeeRemove> {


    List<EmployeeRemove> getAllMsg();
}
