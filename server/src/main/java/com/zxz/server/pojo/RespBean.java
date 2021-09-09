package com.zxz.server.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*
 * 公共返回对象
 * */
public class RespBean {

    private int code;
    private String message;
    private Object obj;

    /*
    * 返回成功结果
    * */
    public static RespBean success(String message) {
        return new RespBean(200, message, null);
    }

    /*
     * 返回成功结果
     * */
    public static RespBean success(String message, Object obj) {
        return new RespBean(200, message, obj);
    }

    /*
     * 返回失败结果
     * */
    public static RespBean error(String message) {
        return new RespBean(500, message, null);
    }

    /*
     * 返回失败结果
     * */
    public static RespBean error(String message, Object obj) {
        return new RespBean(500, message, obj);
    }


}
