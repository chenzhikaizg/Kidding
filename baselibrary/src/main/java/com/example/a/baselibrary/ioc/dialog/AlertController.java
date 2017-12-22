package com.example.a.baselibrary.ioc.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

/**
 * Created by $chenzhikai on 2017/12/22.
 */

 class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;

    }

    /**
     * 获取dialog
     * @return
     */
    public AlertDialog getmDialog() {
        return mDialog;
    }
    //获取dialog的window
    public Window getmWindow() {
        return mWindow;
    }

    public static class AlertParams{
        public Context mContext;
        public int mThemeResId;
        //点击空白是否能够取消
        public boolean mCancelable = false;
        //dialogcancle监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //dialog dismiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //dialog 可以监听
        public DialogInterface.OnKeyListener mOnKeyListener;

        public AlertParams (Context context, int mThemeResId){
            this.mContext = context;
            this.mThemeResId = mThemeResId;
        }

        /**
         * 绑定和设置参数
         * @param mAlert
         */
        public void apply(AlertController mAlert) {

        }
    }
}
