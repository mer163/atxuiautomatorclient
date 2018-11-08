package atx.client;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//添加在属性上面
@Target(ElementType.FIELD)
//运行时可见
@Retention(RetentionPolicy.RUNTIME)

public @interface NoteId {
    int id();
    String name();
}
