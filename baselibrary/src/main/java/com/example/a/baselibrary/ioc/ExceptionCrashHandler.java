package com.example.a.baselibrary.ioc;

import android.content.Context;

/**
 * Created by $chenzhikai on 2017/12/21.
 * 单例的设计模式的异常捕捉类
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static ExceptionCrashHandler mInstace;
    //获取系统默认的
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;
    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        //全局异常


        //写入到本地文件 ex 当前的版本 ，获取手机信息

        //1.崩溃信息

        //2.获取当前的版本，应用信息 包名

        //3.手机信息

        //保存当前的文件，等应用再次启动上传上传问题，上传文件不在这处理

        //让系统默认的处理下
        mDefaultExceptionHandler.uncaughtException(thread,e);

    }

    public static ExceptionCrashHandler getInstance(){

        if (mInstace==null){
            synchronized (ExceptionCrashHandler.class){
                if (mInstace==null){
                    mInstace = new ExceptionCrashHandler();
                }
            }
        }
        return mInstace;

    }
    private Context mContext;
    public void init(Context context){
        this.mContext= context;
        //设置全局的异常为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);

        mDefaultExceptionHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }
}
