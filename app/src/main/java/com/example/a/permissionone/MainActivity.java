package com.example.a.permissionone;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.renderscript.Sampler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.baselibrary.ioc.fixBug.FixDexManager;
import com.example.a.baselibrary.ioc.ioc.CheckNet;
import com.example.a.baselibrary.ioc.ioc.OnClick;
import com.example.a.baselibrary.ioc.ioc.ViewById;
import com.example.a.framelibrary.BaseSkinActivity;

import java.io.File;

import static com.example.a.permissionone.FiveEightView.Direction.RECTANGLE;
import static com.example.a.permissionone.FiveEightView.Direction.TRIANGLE;

public class MainActivity extends BaseSkinActivity {

    private static final int CALL_PHONE_REQUEST_CODE = 200;
    @ViewById(R.id.tv_text)
    private TextView mTextTv;
    private QQStepView qqStepView;
    private int step;
    private ProgressView progressView;
    private FiveEightView mFe;

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

        qqStepView = (QQStepView) findViewById(R.id.qq_step);
        qqStepView.setStepMax(5000);

        progressView = (ProgressView)findViewById(R.id.progress);
        progressView.setStepMax(5000);

        mFe = (FiveEightView)findViewById(R.id.five_eight);

        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3000);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                qqStepView.setCurrentStep((int)animatedValue);
                progressView.setCurrentStep((int) animatedValue);
                if (animatedValue>1000&& animatedValue<2000){
                    mFe.setType(TRIANGLE);
                }else  if (animatedValue>2000){
                    mFe.setType(RECTANGLE);
                }
            }
        });
        valueAnimator.start();

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
