package com.zxz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxz.server.pojo.Admin;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface AdminMapper extends BaseMapper<Admin> {



    @Select("select * from t_admin where username like #{username} and enabled=1")
    Admin getAdminByUserName(String username);


}
