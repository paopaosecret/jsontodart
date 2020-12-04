package com.xander.plugin.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlDocument;

import java.io.File;

/**
 * Created by zhaobing04 on 2020/12/4.
 */
public class AndroidUtils {
    /**
     * 获取清单文件
     * @param project
     * @return
     */
    public static PsiFile getManifestFile(Project project) {
        String path = project.getBasePath() + File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "AndroidManifest.xml";
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(path);
        if(virtualFile == null) {
            return null;
        }
        return PsiManager.getInstance(project).findFile(virtualFile);
    }

    /**
     * 解析清单文件获取包名
     * @param project
     * @return
     */
    public static String getAppPackageName(Project project) {
        PsiFile manifestFile = getManifestFile(project);
        XmlDocument xml = (XmlDocument) manifestFile.getFirstChild();
        return xml.getRootTag().getAttribute("package").getValue();
    }

    /**
     * 获取包名目录
     * @param project
     * @return
     */
    public static VirtualFile getAppPackageBaseDir(Project project) {
        String path = project.getBasePath() + File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "java" + File.separator +
                getAppPackageName(project).replace(".", File.separator);
        //TODO LocalFileSystem.getInstance().findFileByPath(path):只能获取文件不嫩过去目录
        return LocalFileSystem.getInstance().findFileByPath(path);
    }

    /**
     * 在通过包名路径获取包名
     * @param dir
     * @return
     */
    public static String getPackageNameByPath(VirtualFile dir) {
        if(!dir.isDirectory()) {
            // 非目录的取所在文件夹路径
            dir = dir.getParent();
        }
        String path = dir.getPath().replace("/", ".");
        String preText = "src.main.java";
        int preIndex = path.indexOf(preText) + preText.length() + 1;
        path = path.substring(preIndex);
        return path;
    }
}
