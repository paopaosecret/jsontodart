package com.xander.plugin.utils;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhaobing04 on 2020/12/4.
 */
public class ClassUtils {
    /**
     * 获取Java文件的Class类对象
     */
    public static PsiClass getFileClass(PsiFile file) {
        for (PsiElement psiElement : file.getChildren()) {
            if (psiElement instanceof PsiClass) {
                return (PsiClass) psiElement;
            }
        }
        return null;
    }
}
