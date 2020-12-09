package com.xander.plugin.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by zhaobing04 on 2020/12/4.
 */
public class CustomDialog extends JDialog{
    private JTextArea inputJson;
    private JButton btnOk;
    private JPanel body;
    private JTextField classNameText;

    public CustomDialog(Component baseComponent) {
        try{
            //TODO 设置尺寸
            setPreferredSize(new Dimension(600, 400));

            //TODO 设置窗口展示位置

            if(baseComponent != null){
                setLocation( baseComponent.getLocationOnScreen().x, 0);
                System.out.println("baseComponent: " +  baseComponent);
                System.out.println("baseComponent: " + baseComponent.getLocationOnScreen().x  + " " + baseComponent.getLocationOnScreen().y);
            }else{
                setLocation( 0, 0);
            }
            setTitle("Json2Dart");
            setContentPane(body);
            setModalityType(ModalityType.APPLICATION_MODAL);
            btnOk.addActionListener(e -> performGenerate());
        }catch (Exception e){
        }
    }

    private void performGenerate() {
        try {
            if(onGenerateListener != null) {
                onGenerateListener.onGenerate(classNameText.getText().trim(), inputJson.getText().trim());
            }
            dispose();
        }catch (Exception e){
        }
    }

    private OnGenerateListener onGenerateListener;

    public void setOnGenerateListener(OnGenerateListener onGenerateListener) {
        this.onGenerateListener = onGenerateListener;
    }

    public interface OnGenerateListener {
        void onGenerate(String className, String inputJson);
    }
}
