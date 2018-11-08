package atx.client.internal;

import atx.client.AtxClient;
import atx.client.adb.ElementAttribs;
import atx.client.model.AndroidElement;
import atx.client.model.DesiredCapabilities;
import atx.client.remote.PageFactory;

import java.lang.reflect.Field;

public class HomePage  {

    @FindElementBy(id="com.ylmall.app.ui:id/tv_allproduct",verify = true)
    private AndroidElement my;


    public static void main(String[] args){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setPackageName("com.ylmall.app.ui");
        desiredCapabilities.setRemoteHost("192.168.0.204");
        desiredCapabilities.setPlatformName("Android");
        desiredCapabilities.setUdid("da38d28");
        AtxClient driver = new AtxClient(desiredCapabilities);

        HomePage1 mHomePage1 = new HomePage1();
        PageFactory.initElements(mHomePage1, driver);
        mHomePage1.my.click();

    }


    public static void test(Object object,AtxClient driver){
        Class clazz = object.getClass();
        String name  = clazz.getSimpleName();
//        homePage.getClass()
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
//            int id = -1;
//            Object name = "";
            // 在该属性上的注释
            if (field.getAnnotation(FindElementBy.class) != null) {
                // 设置这个时是可以访问私有权限的
                field.setAccessible(true);
                FindElementBy findby = field.getAnnotation(FindElementBy.class);
                System.out.println(field);

//                System.out.println("" + field.);
//                System.out.println(noteId.name());

                try {
                    if (String.valueOf(findby.id())!=null){
                        System.out.println("注解上的id" + findby.id());
                        AndroidElement element;
                        element = driver.findElementByXml(name+ ".xml", ElementAttribs.RESOURCE_ID,findby.id());
                        field.set(object,element);
                        //重置注解内容
//                        field.set(object,321);
//                        System.out.println("注解上的id" + findby.id());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


//
//                System.out.println("ttt" + noteId.id());
//                System.out.println("ttt" + noteId.name());
//                System.out.println(node.getId());
                // 此处调用从xml中取得androidElement对象的方法。
                // ANdroid element ？ 取得对象后进行赋值

//
//                try {
//                    id = field.getInt(node);
//                    field.getName();
//                } catch (IllegalArgumentException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                System.out.println(id);
            }
//            if (field.getAnnotation(NoteName.class) != null) {
//                field.setAccessible(true);
//                try {
//                    name = field.get(node);
//                } catch (IllegalArgumentException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                System.out.println((String) name);
//            }

        }
    }
}
