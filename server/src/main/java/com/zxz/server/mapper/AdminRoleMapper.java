package com.zxz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxz.server.pojo.AdminRole;
import com.zxz.server.pojo.RespBean;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {


    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    Integer addAdminRoles(Integer adminId, Integer[] rids);
}
