package com.xander.plugin.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.xander.plugin.utils.DartUtils;
import com.xander.plugin.utils.StringUtils;
import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaobing04 on 2020/12/4.
 */
public class CodeFactory {

    public static void main(String[] args) {
        String json = "{\"androidStatus\":30000,\"msg\":\"success\",\"result\":{\"data\":{\"welfare\":{\"smallTitle\":\"更多\",\"routeUrl\":\"https://mjifen.58.com?bizLine=10020301&utm_source=shangJiaTongAPP\",\"goods\":[{\"imgUrl\":\"https://pic1.58cdn.com.cn//nowater/shopmana/n_v23ebc69dd8f5e43a689b74d222db319d5.gif\",\"amount\":9999.00,\"marketPrice\":2000.00,\"name\":\"金康尼净水器\",\"id\":1263,\"cash\":null,\"mDetailUrl\":\"https://mjifen.58.com/#/goodDetails?bizLine=10020301&id=1263\"},{\"imgUrl\":\"https://pic1.58cdn.com.cn//nowater/shopmana/n_v2d5c36f4f15624403bced9468b11658fb.jpg\",\"amount\":5888.00,\"marketPrice\":500.00,\"name\":\"京东E卡500\",\"id\":1293,\"cash\":null,\"mDetailUrl\":\"https://mjifen.58.com/#/goodDetails?bizLine=10020301&id=1293\"},{\"imgUrl\":\"https://pic1.58cdn.com.cn//nowater/shopmana/n_v268248a14b4a74fabb1853ae75e386ca7.jpg\",\"amount\":2088.00,\"marketPrice\":100.00,\"name\":\"电信充值100元\",\"id\":1297,\"cash\":null,\"mDetailUrl\":\"https://mjifen.58.com/#/goodDetails?bizLine=10020301&id=1297\"}],\"bigTitle\":\"福利商城\"},\"myHelp\":{\"smallTitle\":\"投诉/认证/申诉/答疑/联系客服\",\"routeUrl\":\"https://help.58.com/mhelp/hy\",\"bigTitle\":\"帮助中心\"},\"myOrder\":{\"smallTitle\":\"您购买的企业服务都记录在这儿\",\"routeUrl\":\"https://esmall.58.com/m/order.html?from=sjt_grzx_wddd\",\"bigTitle\":\"我的订单\"},\"basicMap\":{\"updateTrend\":0,\"vipUrl\":\"https://img.58cdn.com.cn/escstatic/fecar/pmuse/sjt/icon_title_vip.png\",\"level\":2,\"updateScore\":0,\"totalScore\":240,\"ageUrl\":\"https://img.58cdn.com.cn/escstatic/fecar/pmuse/sjt/icon_vip_2_guoqi.png\",\"imUrl\":\"https://ai.58.com/?key=10001#/entry/uid=10001&usersource=101&access_id=100012&business_type=58hyzhuanshukf&joinfrom=homepagezhuanshukf&client_type=app\",\"face\":\"https://pic1.58cdn.com.cn/m1/bigimage/n_v2ed94ef2f2c954fc18599065c413f208b.png\",\"scoreUrl\":\"https://hyapp.58.com/app/merchant/task/merchantScore\",\"name\":\"emuhu7un4\",\"linkUrl\":\"https://mshop.58.com/intelligentmarket/home/getHomeIndex?source=app_58sjt\",\"age\":2,\"status\":0}}},\"status\":\"0\"}";
        generateDartByJson(null, null, json, "Auto");
    }

    /**
     * 生成Dart类，通过Json
     *
     * @param file
     * @param psiClazz
     * @param json
     * @return 0 -> success;  -1 -> fail
     */
    public static int generateDartByJson(PsiFile file, PsiClass psiClazz, String json, String key) {
        if (TextUtils.isEmpty(json)) {
            return -1;
        }

        //TODO 创建json解析器
        JsonParser parse = new JsonParser();
        JsonElement jsonElement = parse.parse(json);

        if (jsonElement != null && jsonElement.isJsonObject() && jsonElement.getAsJsonObject() != null) {
            parseJsonObject(file, psiClazz, jsonElement.getAsJsonObject(), key);
            return 0;
        } else if (jsonElement != null && jsonElement.isJsonArray() && jsonElement.getAsJsonArray() != null) {
            parseJsonArray(file, psiClazz, jsonElement.getAsJsonArray(), key);
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 解析JsonObject
     *
     * @param file
     * @param psiClazz
     * @param json
     * @param classKey
     */
    private static void parseJsonObject(PsiFile file, PsiClass psiClazz, JsonObject json, String classKey) {
        StringBuffer sb = new StringBuffer("");
        sb.append(StringUtils.formatSingleLine(0, "class " + classKey + " {"));
        List<DartField> fieldList = new ArrayList<>();
        for(Map.Entry<String, JsonElement> item : json.entrySet()){
            String key = item.getKey();
            JsonElement jsonElement = json.get(key);
            if(jsonElement.isJsonPrimitive()){
                fieldList.add(new DartField(key, DartUtils.getTypeByJsonPrimitive(json.get(key).getAsJsonPrimitive()), DartField.Type.Basic));

                sb.append(StringUtils.formatSingleLine(1, DartUtils.getTypeByJsonPrimitive(json.get(key).getAsJsonPrimitive()) + " " + key + ";"));
            }else if(jsonElement.isJsonObject()){
                fieldList.add(new DartField(key, StringUtils.firstToUpperCase(key), DartField.Type.Map));

                sb.append(StringUtils.formatSingleLine(1, StringUtils.firstToUpperCase(key) + " " +  key + ";"));
                generateDartByJson(file, psiClazz, item.getValue().toString(), StringUtils.firstToUpperCase(key));
            }else if(jsonElement.isJsonArray()){
                fieldList.add(new DartField(key, "List<" + StringUtils.firstToUpperCase(key) + ">", DartField.Type.List));

                sb.append(StringUtils.formatSingleLine(1, "List<" + StringUtils.firstToUpperCase(key) + "> " +  key + ";"));
                generateDartByJson(file, psiClazz, item.getValue().toString(), StringUtils.firstToUpperCase(key));
            }else if(jsonElement.isJsonNull()){
                fieldList.add(new DartField(key, "List<" + StringUtils.firstToUpperCase(key) + ">", DartField.Type.Null));

                sb.append(StringUtils.formatSingleLine(1, "Object " +  key + ";"));
            }
        }
        sb.append(DartUtils.createConstructor(fieldList, classKey));
        sb.append(DartUtils.createFromJson(fieldList,classKey));
        sb.append(DartUtils.createToJson(fieldList,classKey));
        sb.append(StringUtils.formatSingleLine(0, "}"));
        System.out.println(sb.toString());

        // 获取元素操作的工厂类
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(file.getProject());
        psiClazz.add(factory.createClass(sb.toString()));

        // 导入需要的类
        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(file.getProject());
        styleManager.optimizeImports(file);
        styleManager.shortenClassReferences(psiClazz);
    }

    /**
     * 解析JsonArray
     *
     * @param file
     * @param psiClazz
     * @param json
     * @param classKey
     */
    private static void parseJsonArray(PsiFile file, PsiClass psiClazz, JsonArray json, String classKey) {
        JsonObject jsonObject = new JsonObject();
        for(int i = 0; i<  json.size(); i++){
            JsonElement jsonElement = json.get(i);
            for(Map.Entry<String, JsonElement> item : jsonElement.getAsJsonObject().entrySet()){
                jsonObject.add(item.getKey(), item.getValue());
            }
        }
        generateDartByJson(file, psiClazz, jsonObject.toString(), classKey);
    }
}
