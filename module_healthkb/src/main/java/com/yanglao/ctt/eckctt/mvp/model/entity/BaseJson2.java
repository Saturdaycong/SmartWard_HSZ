package com.yanglao.ctt.eckctt.mvp.model.entity;

import java.io.Serializable;


/**
 * 如果你服务器返回的数据固定为这种方式(字段名可根据服务器更改)
 * 替换范型即可重用BaseJson
 * Created by jess on 26/09/2016 15:19
 * Contact with jess.yan.effort@gmail.com
 */

public class BaseJson2<T> implements Serializable {
    public T data;
    public String code;
    public String msg;

    public boolean isSuccess(){
        return "2".equals(code);
    }
}
