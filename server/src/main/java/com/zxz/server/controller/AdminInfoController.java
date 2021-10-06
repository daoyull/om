package com.zxz.server.controller;


import com.zxz.server.pojo.Admin;
import com.zxz.server.pojo.RespBean;
import com.zxz.server.service.IAdminService;
import com.zxz.server.utils.MinioUtil;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @version 1.0
 * @Author zhen
 * @Date 2021/10/5 15:42
 * @注释
 */
@RestController
public class AdminInfoController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private MinioUtil minioUtil;


    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/admin/info")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication
            authentication) {
//更新成功，重新构建Authentication对象
        if (adminService.updateById(admin)) {
/**
 * 1.用户对象
 * 2.凭证（密码）
 * 3.用户角色
 */
            SecurityContextHolder.getContext().setAuthentication(new
                    UsernamePasswordAuthenticationToken(admin,
                    authentication.getCredentials(),
                    authentication.getAuthorities()));
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @ApiOperation(value = "更新用户密码")
    @PutMapping("/admin/pass")
    public RespBean updateAdminPassword(@RequestBody Map<String, Object> info) {
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updatePassword(oldPass, pass, adminId);
    }

    @ApiOperation(value = "更新用户头像")
    @PostMapping("/admin/userface")
    public RespBean updateHrUserFace(MultipartFile file, Integer id, Authentication authentication) throws Exception {

        return adminService.updateAdminUserFace(minioUtil.upload(file, "yeb-face"), id, authentication);

    }

}
