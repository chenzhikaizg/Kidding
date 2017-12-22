package com.example.a.baselibrary.ioc.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * View事件的注解类型
 * Created by $chenzhikai on 2017/12/19.
 *   @Target(ElementType.FIELD)代表annotation的位置 FIELD代表属性 TYPE类上
 */
  @Target(ElementType.METHOD)
  //什么时候生效 CLASS编译时 RUNTIME运行时
  @Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    int[] value();
}
