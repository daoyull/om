package com.zxz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxz.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRoles(Integer adminId);
}
