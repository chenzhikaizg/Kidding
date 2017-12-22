package com.example.a.baselibrary.ioc.ioc;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.example.a.baselibrary.ioc.ioc.CheckNet;
import com.example.a.baselibrary.ioc.ioc.OnClick;
import com.example.a.baselibrary.ioc.ioc.ViewById;
import com.example.a.baselibrary.ioc.ioc.ViewFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by $chenzhikai on 2017/12/19.
 */

public class ViewUtils {
    //目前
    public static void inject(Activity activity){
        inject(new ViewFinder(activity) ,activity);
        }

    public static void inject(View view){
        inject(new ViewFinder(view) ,view);
    }

    public static void inject(View view,Object object){
        inject(new ViewFinder(view) ,object);
    }

    //兼容上面的三个方法 object 是反射要执行的类
    public static void inject(ViewFinder finder,Object object){

        injectFiled(finder ,object);
        injectEvent(finder,object);

        }

    /***
     * 注入属性
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1.获取类里面的所有属性
        Class<?> clazz = object.getClass();
        //获取所有的属性包括共有和私有
        Field[] fields = clazz.getDeclaredFields();


        //2.获取VIewById的里面的value直
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById!=null){
                //获取注解里面的id值 R.id.tv_text
                int viewId = viewById.value();
                //3.findViewById 找打View
                View view = finder.findViewById(viewId);
                if (view != null){
                    //能够注入所有有修饰符
                    field.setAccessible(true);
                    //4.动态的注入找到的view
                    try {
                        field.set(object,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }


            }
        }


    }

    /**
     * 事件注入
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //1.获取类里面的所有方法
        Class<?> clazz = object.getClass();
        Method[] mMethods = clazz.getDeclaredMethods();


        //2.获取onlick里面的value值
        for (Method method:mMethods){
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick !=null){
                int[] valueIds = onClick.value();
                for (int valueId : valueIds) {

                    //3.findViewById找到View
                    View view = finder.findViewById(valueId);
                    //扩展功能 检测网络
                    boolean isCheckNet = method.getAnnotation(CheckNet.class)!=null;


                    if (view!=null){

                        //4.view.setOnclickListener
                        view.setOnClickListener(new DeclaredOnclickListener(method,object,isCheckNet));
                    }
                }
            }

        }




    }

    private static class DeclaredOnclickListener implements View.OnClickListener{
        private Object mObject;
        private Method mMethod;
        private boolean misChecknet;
        public DeclaredOnclickListener(Method method, Object object, boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.misChecknet = isCheckNet;

        }

        @Override
        public void onClick(View v) {
            //需不需要检测网络
            if (misChecknet){
             //需要
                if (1==1){
                    Toast.makeText(v.getContext(),"meiwang",Toast.LENGTH_SHORT).show();
                    return;
                }
            }


            //点击会调用该方法
            try {
                //所有方法都可以，包括共有私有
                mMethod.setAccessible(true);
                //5.反射执行方法
                mMethod.invoke(mObject,v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject,null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}



