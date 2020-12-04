package com.xander.plugin.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlDocument;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhaobing04 on 2020/12/4.
 * TODO project.getBasePath()是项目的根目录，在其基础上拼接后续路径
 *
 */
public class FileUtils {


    /**
     * 在baseDir路径下新建一个子路径
     * @param baseDir
     * @param pathName
     * @return
     */
    public static VirtualFile createDirectory(VirtualFile baseDir, String pathName){
        //Todo VirtualFile.findChild(filename):获取文件子一级路径中寻找文件或文件夹，
        //TODO 注意和LocalFileSystem.getInstance().findFileByPath(path)的区别
        VirtualFile childDir = baseDir.findChild(pathName);
        if(childDir == null){
            try {
                //TODO 创建一个目录
                childDir = baseDir.createChildDirectory(null, pathName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return childDir;
    }

    /**
     * 在baseDir路径下新建一个文件
     * @param baseDir
     * @param fileName
     * @return
     */
    public static VirtualFile createFile(VirtualFile baseDir, String fileName){
        VirtualFile childDir = baseDir.findChild(fileName);
        if(childDir == null){
            try {
                //TODO 创建一个空文件
                childDir = baseDir.createChildData(null, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return childDir;
    }
}
