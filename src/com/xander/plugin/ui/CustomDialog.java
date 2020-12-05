package com.xander.plugin.ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.xander.plugin.utils.UIUtils;

import javax.swing.*;
import java.awt.*;


/**
 * Created by zhaobing04 on 2020/12/4.
 */
public class CustomDialog extends JDialog{
    private JTextArea inputJson;
    private JButton btnOk;
    private JPanel body;
    public CustomDialog(AnActionEvent event, Component baseComponent) {
        //TODO 设置尺寸
        setPreferredSize(new Dimension(600, 400));

        //TODO 设置窗口居中显示
        setLocation(UIUtils.getScreenWidth()/2 - 300, UIUtils.getScreenHeight()/2 - 200);
        setLocationRelativeTo(baseComponent);
        setContentPane(body);
        setModal(true);
        btnOk.addActionListener(e -> performGenerate());
        btnOk.addActionListener(e -> dispose());
    }

    private void performGenerate() {
        if(onGenerateListener != null) {
            onGenerateListener.onGenerate(inputJson.getText());
        }

        dispose();
    }

    private OnGenerateListener onGenerateListener;

    public void setOnGenerateListener(OnGenerateListener onGenerateListener) {
        this.onGenerateListener = onGenerateListener;
    }

    public interface OnGenerateListener {
        void onGenerate(String inputJson);
    }
}
