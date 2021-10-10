package com.zxz;


import com.zxz.server.pojo.RespBean;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


/**
 * Unit test for simple App.
 */
public class AppTest {


    @Value("${filePath}")
    private String filePath;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException {
        final Logger log = LoggerFactory.getLogger(getClass());
        String fileName = "yeb_" + new Date().getTime() + ".sql";
        String cmd = "mysqldump -uroot -p123456 yeb > E:/yeb/" + fileName; //-u后的root为mysql数据库用户名，-p后接的123456为该用户密码，注意不要有空格；dbName填写需要备份数据的数据库名称，大于号后接生成文件路径
        System.out.println(cmd);
        try {
            Runtime.getRuntime().exec(cmd);
//
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("【备份数据库】成功，SQL文件" + cmd);


    }
}
