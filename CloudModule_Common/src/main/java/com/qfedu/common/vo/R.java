package com.qfedu.common.vo;

import lombok.Data;

/**
 * projectName: CloudLike
 *
 * @author: 靳帅
 * time: 2020/10/30 16:21
 * description:
 */
@Data
public class R <T> {
    private Integer code;
    private String msg;
    private T date;

    public R() {
    }

    public R(Integer code, String msg, T date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }

    public static R succeed(){
        return new R(1,"OK",null);
    }
    public static<T> R succeed(T date){
        return new R(1,"OK",date);
    }
    public static R fail(){
        return new R(1,"fail",null);
    }
    public static R fail(String msg){
        return new R(1,msg,null);
    }


}
