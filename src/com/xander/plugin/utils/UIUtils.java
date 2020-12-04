package com.xander.plugin.utils;

import java.awt.*;

/**
 * Created by zhaobing04 on 2020/12/4.
 */
public class UIUtils {
    /**
     * 获取屏幕的宽
     *
     * @return
     */
    public static int getScreenWidth() {
        //TODO 获取屏幕的尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.width;
    }

    /**
     * 获取屏幕的高
     *
     * @return
     */
    public static int getScreenHeight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.height;
    }
}
