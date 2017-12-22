package com.example.a.permissionone;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.example.a.baselibrary.ioc.ioc.ViewById;
import com.example.a.framelibrary.BaseSkinActivity;



public class SendActivity extends BaseSkinActivity {

    private static final int CALL_PHONE_REQUEST_CODE = 200;
    @ViewById(R.id.tv_text)
    private TextView mTextTv;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_2);
    }

    @Override
    protected void initData() {


    }



    @Override
    protected void initView() {
            TextView textView = (TextView) findViewById(R.id.tv_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SendActivity.this,2/0+"bug测试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initTitle() {

    }




}
