package com.xander.plugin.core;

/**
 * Created by zhaobing04 on 2020/12/5.
 *
 * 例如：String num;
 * String为类型名称
 * num为字段名称
 * 其中String、num、bool类型对应为基本类型
 */
public class DartField {


    //Todo 字段名称
    public String name;

    //
    public String typeName;

    //TODO 1基本类型， 2 List 类型   3 Map类型
    public Type type;

    public DartField(String name, String typeName, Type type){
        this.name = name;
        this.typeName = typeName;
        this.type = type;
    }

    /**
     * 类型
     */
    public static enum Type{
        Basic,
        Map,
        List,
        Null,
    }
}
