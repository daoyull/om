package com.zxz.server.config.filter;

import com.zxz.server.pojo.Menu;
import com.zxz.server.pojo.Role;
import com.zxz.server.service.IMenuService;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;


import java.util.Collection;
import java.util.List;

/**
 * @version 1.0
 * @Author zhen
 * @Date 2021/9/14 20:33
 * @注释 根据请求url分析请求的角色
 */

@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private IMenuService menuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        String url = ((FilterInvocation) o).getRequestUrl();
        List<Menu> menus = menuService.getMenusByRole();
        for(Menu menu : menus){
            if(antPathMatcher.match(menu.getUrl(),url)){
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
