package com.xander.plugin.ui;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.xander.plugin.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by zhaobing04 on 2020/12/4.
 */
public class CustomDialog extends JDialog{
    private JTextArea inputJson;
    private JButton btnOk;
    private JPanel body;
    private JLabel toastTitle;
    private PsiClass clazz;
    public CustomDialog(PsiClass clazz) {
        //TODO 设置尺寸
        setPreferredSize(new Dimension(600, 400));

        //TODO 设置窗口居中显示
        setLocation(UIUtils.getScreenWidth()/2 - 300, UIUtils.getScreenHeight()/2 - 200);
        setLocationRelativeTo(null);
        setContentPane(body);
        setModal(true);
        this.clazz = clazz;
        btnOk.addActionListener(e -> performGenerate());
        btnOk.addActionListener(e -> dispose());
    }

    public String getInputText(){
        return inputJson.getText();
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
