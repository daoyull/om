package com.zxz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxz.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface IMenuService extends IService<Menu> {

    /*
    * 根据用用户ID获取Menu
    * */
    List<Menu> getMenuByAdminId();


    /*
    * 根据角色获取Menus
    * */
    List<Menu> getMenusByRole();
}
