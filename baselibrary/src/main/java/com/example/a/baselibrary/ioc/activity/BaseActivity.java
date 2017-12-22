package com.example.a.baselibrary.ioc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by $chenzhikai on 2017/12/20.
 */

public abstract  class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        initTitle();

        initView();

        initData();

    }

    protected abstract void setContentView() ;

    protected  abstract void initData() ;

    protected  abstract void initView() ;

    protected  abstract void initTitle();
}
