package com.xander.plugin.core;

/**
 * Created by zhaobing04 on 2020/12/5.
 */
public class StringFormat {
    public static final String CONSTRUCTOR_HEAD_HAVE_FIELD = "%s({";
    public static final String CONSTRUCTOR_HEAD_NO_FIELD = "%s(";
    public static final String CONSTRUCTOR_FIELD_INIT = "this.%s,";

    public static final String FROM_JSON_HEAD = "%s.fromJson(Map<String, dynamic> json) {";
    public static final String FROM_JSON_FIELD_INIT_DEFAULT = "%s = json['%s'];";
    public static final String FROM_JSON_FIELD_INIT_MAP1 = "%s = json['%s'] != null ?";
    public static final String FROM_JSON_FIELD_INIT_MAP2 = "new %s.fromJson(json['%s']) : null;";
    public static final String FROM_JSON_FIELD_INIT_LIST1 = "if (json['%s'] != null) {";
    public static final String FROM_JSON_FIELD_INIT_LIST2 = "%s = new List<%s>();";
    public static final String FROM_JSON_FIELD_INIT_LIST3 = "json['%s'].forEach((v) {";
    public static final String FROM_JSON_FIELD_INIT_LIST4 = "%s.add(new %s.fromJson(v));";

    public static final String TO_JSON_FIELD_INIT_DEFAULT = "data['%s'] = this.%s;";
    public static final String TO_JSON_FIELD_INIT_MAP1 = "if (this.%s != null) {";
    public static final String TO_JSON_FIELD_INIT_MAP2 = "data['%s'] = this.%s.toJson();";
    public static final String TO_JSON_FIELD_INIT_LIST1 = "if (this.%s != null) {";
    public static final String TO_JSON_FIELD_INIT_LIST2 = "data['%s'] = this.%s.map((v) => v.toJson()).toList();";
}
