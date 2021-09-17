package com.zxz.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxz.server.pojo.Menu;
import com.zxz.server.pojo.MenuRole;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.pojo.Role;
import com.zxz.server.service.IMenuRoleService;
import com.zxz.server.service.IMenuService;
import com.zxz.server.service.IRoleService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;


    @ApiModelProperty("获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRole() {
        return roleService.list();
    }


    @ApiModelProperty("获取所有角色")
    @PostMapping("/role")
    public RespBean AddRole(@RequestBody Role role) {
        if (role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (roleService.save(role)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiModelProperty("删除角色")
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid) {
        if (roleService.removeById(rid)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "获取所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid) {
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",
                rid)).stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        return menuRoleService.updateMenuRole(rid, mids);
    }


}
