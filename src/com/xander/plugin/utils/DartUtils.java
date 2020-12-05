package com.xander.plugin.utils;

import com.google.gson.JsonPrimitive;
import com.xander.plugin.core.DartField;
import com.xander.plugin.core.StringFormat;

import java.util.List;

/**
 * Created by zhaobing04 on 2020/12/5.
 */
public class DartUtils {

    /**
     * 根据Json类型获取Dart类型
     *
     * @param jsonPrimitive
     * @return
     */
    public static String getTypeByJsonPrimitive(JsonPrimitive jsonPrimitive){
        if(jsonPrimitive.isNumber()){
            return "num";
        }else if(jsonPrimitive.isString()){
            return "String";
        }else if(jsonPrimitive.isBoolean()){
            return "bool";
        }
        return "Object";
    }

    /**
     * 根据字段信息和类名生成构造器
     *
     * @param fieldList
     * @param className
     * @return
     */
    public static String createConstructor(List<DartField> fieldList, String className){
        StringBuffer sb = new StringBuffer("");
        sb.append("\n");
        //TODO 1.添加方法头
        if(fieldList != null &&fieldList.size() > 0){
            sb.append(StringUtils.formatSingleLine(1, String.format(StringFormat.CONSTRUCTOR_HEAD_HAVE_FIELD, className)));
        }else{
            sb.append(StringUtils.formatSingleLine(1, String.format(StringFormat.CONSTRUCTOR_HEAD_NO_FIELD, className)));
        }

        //TODO 2.添加方法中字段赋值
        for(DartField field : fieldList){
            sb.append(StringUtils.formatSingleLine(2, String.format(StringFormat.CONSTRUCTOR_FIELD_INIT, field.name)));
        }

        //TODO 3、添加方法尾
        if(fieldList != null &&fieldList.size() > 0){
            sb.append(StringUtils.formatSingleLine(1, "});"));
        }else{
            sb.append(StringUtils.formatSingleLine(1, ");"));
        }
        return sb.toString();
    }

    /**
     * 根据字段信息和类名生成fromJson方法
     *
     * @param fieldList
     * @param className
     * @return
     */
    public static String createFromJson(List<DartField> fieldList, String className){
        StringBuffer sb = new StringBuffer("");
        sb.append("\n");
        //TODO 1.添加方法头
        sb.append(StringUtils.formatSingleLine(1, String.format(StringFormat.FROM_JSON_HEAD, className)));

        //TODO 2.添加方法中字段赋值
        for(DartField field : fieldList){
            switch (field.type){
                case Map:
                    sb.append(StringUtils.formatSingleLine(2, String.format(StringFormat.FROM_JSON_FIELD_INIT_MAP1, field.name, field.name)));
                    sb.append(StringUtils.formatSingleLine(3, String.format(StringFormat.FROM_JSON_FIELD_INIT_MAP2, StringUtils.firstToUpperCase(field.name), field.name)));
                    break;
                case List:
                    sb.append(StringUtils.formatSingleLine(2, String.format(StringFormat.FROM_JSON_FIELD_INIT_LIST1, field.name)));
                    sb.append(StringUtils.formatSingleLine(3, String.format(StringFormat.FROM_JSON_FIELD_INIT_LIST2, field.name, StringUtils.firstToUpperCase(field.name))));
                    sb.append(StringUtils.formatSingleLine(3, String.format(StringFormat.FROM_JSON_FIELD_INIT_LIST3, field.name)));
                    sb.append(StringUtils.formatSingleLine(4, String.format(StringFormat.FROM_JSON_FIELD_INIT_LIST4, field.name, StringUtils.firstToUpperCase(field.name))));

                    sb.append(StringUtils.formatSingleLine(3, "});"));
                    sb.append(StringUtils.formatSingleLine(2, "}"));
                    break;
                default:
                    sb.append(StringUtils.formatSingleLine(2, String.format(StringFormat.FROM_JSON_FIELD_INIT_DEFAULT, field.name, field.name)));
                    break;
            }
        }

        //TODO 3、添加方法尾
        sb.append(StringUtils.formatSingleLine(1,"}"));
        return sb.toString();
    }

    /**
     * 根据字段信息和类名生成toJson方法
     * @param fieldList
     * @param className
     * @return
     */
    public static String createToJson(List<DartField> fieldList, String className){
        StringBuffer sb = new StringBuffer("");
        sb.append("\n");
        //TODO 1.添加方法头
        sb.append(StringUtils.formatSingleLine(1, "Map<String, dynamic> toJson() {"));
        sb.append(StringUtils.formatSingleLine(2, "final Map<String, dynamic> data = new Map<String, dynamic>();"));

        //TODO 2.添加方法中字段赋值
        for(DartField field : fieldList){
            switch (field.type){
                case Map:
                    sb.append(StringUtils.formatSingleLine(2, String.format(StringFormat.TO_JSON_FIELD_INIT_MAP1, field.name)));
                    sb.append(StringUtils.formatSingleLine(3, String.format(StringFormat.TO_JSON_FIELD_INIT_MAP2, field.name, field.name)));
                    sb.append(StringUtils.formatSingleLine(2, "}"));
                    break;
                case List:
                    sb.append(StringUtils.formatSingleLine(2, String.format(StringFormat.TO_JSON_FIELD_INIT_LIST1, field.name)));
                    sb.append(StringUtils.formatSingleLine(3, String.format(StringFormat.TO_JSON_FIELD_INIT_LIST2, field.name, field.name)));
                    sb.append(StringUtils.formatSingleLine(2, "}"));
                    break;
                default:
                    sb.append(StringUtils.formatSingleLine(2, String.format(StringFormat.TO_JSON_FIELD_INIT_DEFAULT, field.name, field.name)));
                    break;
            }
        }

        //TODO 3、添加方法尾
        sb.append(StringUtils.formatSingleLine(1, "}"));
        return sb.toString();
    }
}
