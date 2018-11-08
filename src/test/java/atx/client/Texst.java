package atx.client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Texst {

    public static void main(String[] args) {

        Node node = new Node();
        node.setId(2);
        node.setName("哈哈");
        show(node);

    }

    public static void show(Node node) {
        System.out.println(node.getClass().getSimpleName());
        Class cc = node.getClass();

//        Class c = Node.class;
        // 获取改类的所有属性
        Field[] fields = cc.getDeclaredFields();
        for (Field field : fields) {
            int id = -1;
            Object name = "";
            // 在该属性上的注释
            if (field.getAnnotation(NoteId.class) != null) {
                // 设置这个时是可以访问私有权限的
                field.setAccessible(true);
                NoteId noteId = field.getAnnotation(NoteId.class);
                System.out.println(field);
                System.out.println(noteId.id());
                System.out.println(noteId.name());

                try {
                    if (String.valueOf(noteId.id())!=null){
                        field.set(node,321);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }



                System.out.println("ttt" + noteId.id());
                System.out.println("ttt" + noteId.name());
                System.out.println(node.getId());
                // 此处调用从xml中取得androidElement对象的方法。
                // ANdroid element ？ 取得对象后进行赋值


                try {
                    id = field.getInt(node);
                    field.getName();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(id);
            }
            if (field.getAnnotation(NoteName.class) != null) {
                field.setAccessible(true);
                try {
                    name = field.get(node);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println((String) name);
            }

        }
//        // 取出此类的方法
//        Method[] methods = c.getDeclaredMethods();
//        for (Method method : methods) {
//            if (method.getAnnotation(NodeShow.class) != null) {
//                method.setAccessible(true);// 增加访问权限级别
//                String name = method.getName();//获取方法名
//                try {
//                    //执行反射出来的方法，第一个参数的值是你要执行那个类，第二个参数是该方法中的参数object[]
//                    method.invoke(node, null);
//                } catch (IllegalArgumentException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            }
//        }
    }



}