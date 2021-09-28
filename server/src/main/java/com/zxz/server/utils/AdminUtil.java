package com.zxz.server.utils;

import com.zxz.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @version 1.0
 * @Author zhen
 * @Date 2021/9/18 17:28
 * @注释
 */
public class AdminUtil {

    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
