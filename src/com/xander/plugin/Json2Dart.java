package com.xander.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.xander.plugin.core.CodeFactory;
import com.xander.plugin.ui.CustomDialog;
import com.xander.plugin.utils.AndroidUtils;
import com.xander.plugin.utils.ClassUtils;
import com.xander.plugin.utils.FileUtils;
import org.apache.http.util.TextUtils;

import java.awt.*;
import java.io.File;

/**
 * Created by zhaobing04 on 2020/12/4.
 */
public class Json2Dart extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        try{
            Project project = event.getData(PlatformDataKeys.PROJECT);
            PsiFile file = event.getData(PlatformDataKeys.PSI_FILE);
            PsiClass psiClazz = ClassUtils.getFileClass(file);
            Component component = event.getData(PlatformDataKeys.CONTEXT_COMPONENT);
            CustomDialog dialog = new CustomDialog(event, component);
            dialog.setOnGenerateListener((className, str) -> WriteCommandAction.runWriteCommandAction(project, () -> {
                try{
                    printInputInfo(file, psiClazz);
                    File shuchu = new File(file.getVirtualFile().getPath());
                    FileUtils.clearInfoForFile(shuchu);
                    CodeFactory.generateDartByJson(file, psiClazz, str, TextUtils.isEmpty(className) ? "AutoGen" : className);
                    AndroidUtils.getAppPackageBaseDir(project).refresh(true, true);
                    file.getVirtualFile().refresh(true,true);
                    Messages.showMessageDialog(project, "创建成功，刷新文件夹即可", "提示", Messages.getInformationIcon());
                }catch (Exception e){}
            }));
            dialog.pack();
            dialog.setVisible(true);
        }catch (Exception e){}

//        for (PsiElement psiElement : file.getChildren()) {
//            System.out.println(psiElement);
//            if(psiElement instanceof PsiClass){
//                PsiClass clazz = (PsiClass) psiElement;
//                PsiMethod method = clazz.getMethods()[0];
//                System.out.println(method.getName());
//                System.out.println(method.getParameterList().getText());
//            }
//        }
    }

    public void printInputInfo(PsiFile file, PsiClass psiClazz){
        if(file != null){
            System.out.println(file.getName());
            System.out.println(file.getContainingDirectory());
            System.out.println(file.getVirtualFile().getPath());
        }else{
            System.out.println("file = null");
        }
        if(psiClazz != null){
            System.out.println(psiClazz.getQualifiedName() + ", name = " + psiClazz.getName());
        }else{
            System.out.println("psiClazz = null");
        }
    }

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if(editor != null){
            e.getPresentation().setEnabled(true);
        }else {
            e.getPresentation().setEnabled(false);
        }
    }
}
