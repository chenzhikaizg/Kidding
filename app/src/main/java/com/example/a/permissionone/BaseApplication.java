package com.example.a.permissionone;

import android.app.AlertDialog;
import android.app.Application;

import com.example.a.baselibrary.ioc.ExceptionCrashHandler;
import com.example.a.baselibrary.ioc.fixBug.FixDexManager;

/**
 * Created by $chenzhikai on 2017/12/21.
 */

public class BaseApplication   extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //设置全局的异常捕捉类
        ExceptionCrashHandler.getInstance().init(this);


//        FixDexManager fixDexManager = new FixDexManager(this);
//        //加载全部的修复包
//        try {
//            fixDexManager.loadFixDex();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
