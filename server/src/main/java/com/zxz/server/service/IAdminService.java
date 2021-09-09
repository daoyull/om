package com.zxz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxz.server.pojo.Admin;
import com.zxz.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
public interface IAdminService extends IService<Admin> {

    RespBean login(String username, String password, HttpServletRequest request);

    Admin getAdminByUserName(String username);
}
