package com.zxz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxz.server.pojo.Menu;

import java.util.List;


/**
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface MenuMapper extends BaseMapper<Menu> {


    List<Menu> getMenuByAdminId(Integer id);

    List<Menu> getMenusByRole();
}
