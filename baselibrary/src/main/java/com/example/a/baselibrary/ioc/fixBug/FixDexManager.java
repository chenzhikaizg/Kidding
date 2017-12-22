package com.example.a.baselibrary.ioc.fixBug;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

import static android.content.ContentValues.TAG;

/**
 * Created by $chenzhikai on 2017/12/22.
 */

public class FixDexManager {

    private Context mContext;

    private File mDexDir;

        public FixDexManager(Context context) {
            this.mContext = context;
            //获取应用可以访问的dex目录

            this.mDexDir = context.getDir("odex",Context.MODE_PRIVATE);
        }

    /**
     * 修复dex包
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath)  throws  Exception{


        //2.获取下载好的补丁的dexElement

        //2.1移动到系统能够访问的 dex 目录下 ClassLoader

        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()){
            throw  new FileNotFoundException(fixDexPath);
        }

        File destFile = new File(mDexDir,srcFile.getName());
        if (destFile.exists()){
            Log.e(TAG, "fixDex: " +fixDexPath+"已经被夹在了");
            return;
        }
        copyFile(srcFile,destFile);

        //2.2 ClassLoader读取fixDex路径 //为什么加入到集合 一起动可能就要修复BaseApplication
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);

        fixDexFiles(fixDexFiles);

    }

    /**
     *把dexElements注入到classLoader
     * @param classLoader
     * @param dexElements
     */
    private void injectDexElement(ClassLoader classLoader, Object dexElements) throws  Exception {
        // 1.先获取 pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        // IOC 熟悉反射
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        // 2. pathList里面的dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        dexElementsField.set(pathList, dexElements);
    }

    /**
     * 合并两个数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    /**
     * copy file
     *
     * @param src  source file
     * @param dest target file
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }



    /**
     * 从classloader中获取dexElement
     * @param classLoader
     * @return
     */
    private Object getDexElementByClassLoader(ClassLoader classLoader) throws  Exception{
        //先获取 PathList
       Field pathListField   = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        //pathlist里面的dexElement
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        return   dexElementsField.get(pathList);
    }

    /**
     * 加载全部的修复包
     */
    public void loadFixDex() throws  Exception {
        File[] dexFiles = mDexDir.listFiles();
        List<File> fixDexFiles = new ArrayList<>();

        for (File dexFile : dexFiles) {
            if (dexFile.getName().endsWith(".dex")){
                fixDexFiles.add(dexFile);
            }
            fixDexFiles(fixDexFiles);

        }

    }

    /**
     * 修复dex
     * @param fixDexFiles
     */
    private void fixDexFiles(List<File> fixDexFiles)  throws  Exception{
        //1.先获取已经运行的DexElement
        ClassLoader applicatonClassLoader = mContext.getClassLoader();

        Object applicationDexElements = getDexElementByClassLoader(applicatonClassLoader);

        File  optiaizedirectory = new File(mDexDir,"odex");
        if (!optiaizedirectory.exists()){
            optiaizedirectory.mkdirs();
        }

        //修复
        for (File fixDexFile : fixDexFiles) {
            //dex dex路径
            //optiaizedirectory 解压路径
            //libraryPath .so文件位置
            //parent 父ClassLoader
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    fixDexFile.getAbsolutePath(),//dex路径 bixu
                    optiaizedirectory,//解压路径
                    null,
                    applicatonClassLoader
            );
            Object fixDexElements = getDexElementByClassLoader(fixDexClassLoader);
            //3.把补丁的dexElement 查到已经运行的dexElement的最前面  先合并

            //applicatonClassLoader 数组 合并 fixDexElements
            applicationDexElements = combineArray(fixDexElements, applicationDexElements);

        }
        injectDexElement(applicatonClassLoader,applicationDexElements);


    }
}
