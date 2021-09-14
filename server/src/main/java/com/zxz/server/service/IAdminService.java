package com.zxz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxz.server.pojo.Admin;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface IAdminService extends IService<Admin> {

    RespBean login(String username, String password,String code, HttpServletRequest request);

    Admin getAdminByUserName(String username);

    /*
    * 根据用户id获取权限列表
    * */
    List<Role> getRoles(Integer adminId);
}
