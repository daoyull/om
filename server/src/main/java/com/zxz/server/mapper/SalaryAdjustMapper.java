package com.zxz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxz.server.pojo.SalaryAdjust;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface SalaryAdjustMapper extends BaseMapper<SalaryAdjust> {

    @Select("select * from t_salary_adjust where eid = #{id}")
    SalaryAdjust getSalaryById(Integer id);
}
