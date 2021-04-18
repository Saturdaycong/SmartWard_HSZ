package com.yanglao.ctt.eckctt.mvp.model.entity;

import java.io.Serializable;


/**
 * 如果你服务器返回的数据固定为这种方式(字段名可根据服务器更改)
 * 替换范型即可重用BaseJson
 */

public class UploadBaseJson<T> implements Serializable {
    public T data;
    public String type;
    public String userName;
}
