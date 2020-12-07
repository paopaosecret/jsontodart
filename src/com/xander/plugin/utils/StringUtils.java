package com.xander.plugin.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.util.TextUtils;
import org.codehaus.jettison.json.JSONObject;

import java.util.Locale;

/**
 * Created by zhaobing04 on 2020/12/4.
 */
public class StringUtils {
    /**
     * 将string按需要格式化,前面加缩进符,后面加换行符
     * @param tabNum 缩进量
     * @param srcString
     * @return
     */
    public static String formatSingleLine(int tabNum, String srcString) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<tabNum; i++) {
            sb.append("\t");
        }
        sb.append(srcString);
        sb.append("\n");
        return sb.toString();
    }

    /**
     * 将字符串第一个字符转为大写
     *
     * @param str
     * @return
     */
    public static String firstToUpperCase(String str) {
        if(TextUtils.isEmpty(str)){
            return "";
        }
        return str.substring(0, 1).toUpperCase(Locale.getDefault()) + str.substring(1);
    }

    /**
     * 驼峰转下划线命名
     */
    public static String camelToUnderLine(String src) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbWord = new StringBuilder();
        char[] chars = src.trim().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if(c >= 'A' && c <= 'Z') {
                // 一旦遇到大写单词，保存之前已有字符组成的单词
                if(sbWord.length() > 0) {
                    if(sb.length() > 0) {
                        sb.append("_");
                    }
                    sb.append(sbWord.toString());
                }
                sbWord = new StringBuilder();
            }
            sbWord.append(c);
        }
        if(sbWord.length() > 0) {
            if(sb.length() > 0) {
                sb.append("_");
            }
            sb.append(sbWord.toString());
        }
        return sb.toString();
    }

    public static boolean isJson(String content) {
        if(TextUtils.isEmpty(content)){
            return false;
        }
        try {
            //TODO 创建json解析器
            JsonParser parse = new JsonParser();
            JsonElement jsonElement = parse.parse(content);
            if(jsonElement.isJsonObject()){
                return true;
            }else if(jsonElement.isJsonArray()){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
