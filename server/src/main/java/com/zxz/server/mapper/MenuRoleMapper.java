package com.zxz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxz.server.pojo.MenuRole;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

//    Integer insertRecord(Integer rid, Integer[] mids);
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
