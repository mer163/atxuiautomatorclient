package atx.client.internal;


import org.apache.xpath.operations.Bool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//添加在属性上面
@Target(ElementType.FIELD)
//运行时可见
@Retention(RetentionPolicy.RUNTIME)
public @interface FindElementBy {

    String id() default "";
    String className() default "";
    String text() default "";
    String contentDesc() default "";
    boolean verify() default false;
    int index() default 0;
}
