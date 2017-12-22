package com.example.a.permissionone;

import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.baselibrary.ioc.fixBug.FixDexManager;
import com.example.a.baselibrary.ioc.ioc.CheckNet;
import com.example.a.baselibrary.ioc.ioc.OnClick;
import com.example.a.baselibrary.ioc.ioc.ViewById;
import com.example.a.framelibrary.BaseSkinActivity;

import java.io.File;

public class MainActivity extends BaseSkinActivity {

    private static final int CALL_PHONE_REQUEST_CODE = 200;
    @ViewById(R.id.tv_text)
    private TextView mTextTv;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData() {
    //   fixDexBug();

    }

    private void fixDexBug() {
        File fixFile = new File(Environment.getExternalStorageDirectory(),"fix.dex");
        if (fixFile.exists()){
            FixDexManager fixDexManager = new FixDexManager(this);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath());
                Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"fail",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void initView() {
        TextView textView = (TextView) findViewById(R.id.tv_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SendActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initTitle() {

    }

//
//   // @CheckNet  //没网就不执行该方法，直接打印toast
//    public void onClick(TextView view){
//        Intent intent = new Intent(this,SendActivity.class);
//        startActivity(intent);
//
//    }


}
