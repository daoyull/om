package com.zxz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxz.server.pojo.Admin;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.pojo.Role;
import org.springframework.security.core.Authentication;

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

    /**
     * 根据用户id获取权限列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);

    /**
     * 获取所有操作员
     * @return
     */
    List<Admin> getAllAdmin(String search);

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    RespBean updateAdminRoles(Integer adminId, Integer[] rids);

    RespBean updatePassword(String oldPass, String pass, Integer adminId);

    RespBean updateAdminUserFace(String url, Integer id, Authentication authentication);
}
