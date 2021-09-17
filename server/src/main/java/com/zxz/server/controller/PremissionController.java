package com.zxz.server.controller;


import com.zxz.server.pojo.RespBean;
import com.zxz.server.pojo.Role;
import com.zxz.server.service.IRoleService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author zhen
 * @Date 2021/9/17 11:31
 * @注释 权限组
 */
@RestController
@RequestMapping("/system/basic/permiss")
public class PremissionController {

    @Autowired
    private IRoleService roleService;


    @ApiModelProperty("获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRole(){
        return roleService.list();
    }


    @ApiModelProperty("获取所有角色")
    @PostMapping("/role")
    public RespBean AddRole(@RequestBody Role role){
        if (role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiModelProperty("删除角色")
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid){
        if(roleService.removeById(rid)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }


}
