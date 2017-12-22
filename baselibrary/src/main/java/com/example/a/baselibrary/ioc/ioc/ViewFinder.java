package com.example.a.baselibrary.ioc.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by $chenzhikai on 2017/12/19.
 * findViewById的辅助类
 */

public class ViewFinder {
    private Activity mActivity;
    private View mView;


    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;

    }

    public ViewFinder(View view, Object object) {

    }

    public View findViewById(int viewId){
        return mActivity !=null ?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }
}
