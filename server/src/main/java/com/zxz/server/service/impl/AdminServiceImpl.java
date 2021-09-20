package com.zxz.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxz.server.mapper.AdminMapper;
import com.zxz.server.mapper.AdminRoleMapper;
import com.zxz.server.mapper.RoleMapper;
import com.zxz.server.pojo.Admin;
import com.zxz.server.pojo.AdminRole;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.pojo.Role;
import com.zxz.server.service.IAdminRoleService;
import com.zxz.server.service.IAdminService;
import com.zxz.server.utils.AdminUtil;
import com.zxz.server.utils.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxz
 * @since 2021-09-09
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    //登录之后返回Token
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isBlank(captcha) || !code.equals(captcha)){
                return RespBean.error("验证码错误");
        }
        //登录功能
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println(userDetails);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }

        if(!userDetails.isEnabled()){
            return RespBean.error("账户已被禁用，请联系管理员");
        }

        //添加security进全文
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        //创建并返回Token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功", tokenMap);
    }

    //获取用户名根据用户
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.getAdminByUserName(username);
    }

    //根据用户id获取权限列表
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    @Override
    public List<Admin> getAllAdmin(String search) {
        return adminMapper.getAllAdmin(AdminUtil.getCurrentAdmin().getId(),search);
    }


    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    public RespBean updateAdminRoles(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer result = adminRoleMapper.addAdminRoles(adminId, rids);
        if(result ==rids.length){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

}
