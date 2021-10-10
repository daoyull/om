package com.zxz.server.controller;


import com.zxz.server.pojo.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;


/**
 * @version 1.0
 * @Author zhen
 * @Date 2021/10/9 21:40
 * @注释 备份数据库
 */

@RestController
public class SavaDataBaseController {

    final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${filePath}")
    private String filePath;


    @GetMapping("/backupSQL")
    public RespBean backupSQL() {
        String fileName = "yeb_" + new Date().getTime() + ".sql";
        String cmd = "mysqldump -uroot -p123456 yeb > " + filePath + fileName ; //-u后的root为mysql数据库用户名，-p后接的123456为该用户密码，注意不要有空格；dbName填写需要备份数据的数据库名称，大于号后接生成文件路径
        System.out.println(cmd);
        try {
            Runtime.getRuntime().exec(cmd);
//
        } catch (Exception e) {
            return RespBean.error("【备份数据库】失败：" + e.getMessage());
        }
        log.info("【备份数据库】成功，SQL文件：{}", fileName);
        return RespBean.success("备份数据库成功");
    }
}
