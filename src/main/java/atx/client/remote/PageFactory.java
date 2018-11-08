package atx.client.remote;

import atx.client.AtxClient;
import atx.client.adb.ElementAttribs;
import atx.client.exception.ElementNotFoundException;
import atx.client.internal.FindElementBy;
import atx.client.model.AndroidElement;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

public class PageFactory {

    private static final Logger logger = Logger.getLogger(PageFactory.class.getName());

    public static void initElements(Object object,AtxClient driver){
        Class clazz = object.getClass();
        String name  = clazz.getSimpleName();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 在该属性上的注释
            if (field.getAnnotation(FindElementBy.class) != null) {
                // 设置这个时是可以访问私有权限的
                field.setAccessible(true);
                FindElementBy findby = field.getAnnotation(FindElementBy.class);
                AndroidElement element;
                List<AndroidElement> elementList;
                try {
                    if(findby.verify()){
                        logger.info("校验当前页面");
//                        check(findby,driver);
                        if (String.valueOf(findby.id()) != null && !"".equals(findby.id())) {
//                        System.out.println("注解上的id" + findby.id());
//                            AndroidElement el  = driver.findElementById(findby.id());
                            AndroidElement el1 = driver.findElementById(driver.getPackageName() + ":" +findby.id());
//                            if (el!= null) {
//                                field.set(object,el);
//                            }else if( el1==null){
//                                field.set(object,el1);
//                            }else{
//                                throw new ElementNotFoundException("根据id："+ findby.id() + " 查找元素未找到，请检查当前页面。");
//                            }
                            field.set(object,el1);
                        }
                        if(String.valueOf(findby.text())!=null && !"".equals(findby.text())){
//                        System.out.println("注解上的text" + findby.text());log
                            AndroidElement el  = driver.findElementByName(findby.text());
                            if (el==null) {
                                throw new ElementNotFoundException("根据text："+ findby.text() + " 查找元素未找到，请检查当前页面。");
                            }else{
                                field.set(object,el);
                            }
                        }
                        if(String.valueOf(findby.contentDesc())!=null && !"".equals(findby.contentDesc())){
//                        System.out.println("注解上的contentDesc" + findby.contentDesc());
                            AndroidElement el  = driver.findElementByDesc(findby.contentDesc());
                            if (el==null){
                                throw new ElementNotFoundException("根据contentDesc："+ findby.contentDesc() + " 查找元素未找到，请检查当前页面。");
                            }else{
                                field.set(object,el);
                            }
                        }
                        if(String.valueOf(findby.className())!=null && !"".equals(findby.className())){
//                        System.out.println("注解上的className" + findby.className());
                            AndroidElement el  = driver.findElementByClass(findby.className());
                            if (el==null) {
                                throw new ElementNotFoundException("根据className："+ findby.className() + " 查找元素未找到，请检查当前页面。");
                            }else{
                                field.set(object,el);
                            }
                        }

                    }else{
                        if (String.valueOf(findby.id()) != null && !"".equals(findby.id())) {
//                        System.out.println("注解上的id" + findby.id());
                            elementList = driver.findElementsByXml(name + ".xml", ElementAttribs.RESOURCE_ID, findby.id(),false);
                            System.out.println("查找" + findby.id()  + "element size = " + elementList.size() );
                            if(elementList.size()==0){
                               retry(driver,name + ".xml", ElementAttribs.RESOURCE_ID, findby.id());
//
                            }

                            //未找到元素，可能页面相应慢，此时进行延时2s后删除当前xml 进行重试
//                            if(elementList.size()==0){
//                                Thread.sleep(3000);
//                                elementList = driver.findElementsByXml(name + ".xml", ElementAttribs.RESOURCE_ID, findby.id(),true);
//
//                            }
//                            System.out.println("index=" + findby.index());
                            field.set(object, elementList.get(findby.index()));
//                            element = driver.findElementByXml(name + ".xml", ElementAttribs.RESOURCE_ID, findby.id());
//                            field.set(object, element);
                        }
                        if(String.valueOf(findby.text())!=null && !"".equals(findby.text())){
//                        System.out.println("注解上的text" + findby.text());log
//                            element = driver.findElementByXml(name + ".xml", ElementAttribs.TEXT, findby.text());
//                            field.set(object, element);
                            elementList = driver.findElementsByXml(name + ".xml", ElementAttribs.TEXT, findby.text(),false);
                            if(elementList.size()==0 ){
                                retry(driver,name + ".xml", ElementAttribs.TEXT, findby.text());
                            }

//                            System.out.println("size=====" + elementList.size()+ elementList==null);
//                            if(elementList.size()==0){
//                                elementList = driver.findElementsByXml(name + ".xml", ElementAttribs.TEXT, findby.text(),true);
//
//                            }
//                            System.out.println("index=" + findby.index());
                            field.set(object, elementList.get(findby.index()));
                        }
                        if(String.valueOf(findby.contentDesc())!=null && !"".equals(findby.contentDesc())){
//                        System.out.println("注解上的contentDesc" + findby.contentDesc());
//                            element = driver.findElementByXml(name + ".xml", ElementAttribs.CONTENTDESC, findby.contentDesc());
//                            field.set(object, element);
                            elementList = driver.findElementsByXml(name + ".xml", ElementAttribs.CONTENTDESC, findby.contentDesc(),false);
                            if(elementList.size()==0){
                                retry(driver,name + ".xml", ElementAttribs.CONTENTDESC, findby.contentDesc());
                            }

//                            System.out.println("size=====" + elementList.size()+ elementList==null);
//                            if(elementList.size()==0){
//                                elementList = driver.findElementsByXml(name + ".xml", ElementAttribs.TEXT, findby.text(),true);
//                            }
//                            System.out.println("index=" + findby.index());
                            field.set(object, elementList.get(findby.index()));
                        }
                        if(String.valueOf(findby.className())!=null && !"".equals(findby.className())){
//                        System.out.println("注解上的className" + findby.className());
//                            element = driver.findElementByXml(name + ".xml", ElementAttribs.CLASS, findby.className());
//                            field.set(object, element);
                            elementList = driver.findElementsByXml(name + ".xml", ElementAttribs.CLASS, findby.className(),false);
                            if(elementList.size()==0){
                                retry(driver,name + ".xml", ElementAttribs.CLASS, findby.className());
                            }

//                            System.out.println("size=====" + elementList.size()+ elementList==null);
//                            if(elementList.size()==0){
//                                elementList = driver.findElementsByXml(name + ".xml", ElementAttribs.CLASS, findby.className(),true);
//                            }
//                            System.out.println("index=" + findby.index());
                            field.set(object, elementList.get(findby.index()));
                        }
                    }
                }catch (Exception e ){
                    logger.info(name + "类中未找到元素，当前页面判断失败");
                    e.printStackTrace();
                }
//                try {
//                    if (String.valueOf(findby.id()) != null && !"".equals(findby.id())) {
////                        System.out.println("注解上的id" + findby.id());
//
//                        element = driver.findElementByXml(name + ".xml", ElementAttribs.RESOURCE_ID, findby.id());
//                        field.set(object, element);
//                    }
//                    if(String.valueOf(findby.text())!=null && !"".equals(findby.text())){
////                        System.out.println("注解上的text" + findby.text());log
//                        element = driver.findElementByXml(name + ".xml", ElementAttribs.TEXT, findby.text());
//                        field.set(object, element);
//                    }
//                    if(String.valueOf(findby.contentDesc())!=null && !"".equals(findby.contentDesc())){
////                        System.out.println("注解上的contentDesc" + findby.contentDesc());
//                        element = driver.findElementByXml(name + ".xml", ElementAttribs.CONTENTDESC, findby.contentDesc());
//                        field.set(object, element);
//                    }
//                    if(String.valueOf(findby.className())!=null && !"".equals(findby.className())){
////                        System.out.println("注解上的className" + findby.className());
//                        element = driver.findElementByXml(name + ".xml", ElementAttribs.CLASS, findby.className());
//                        field.set(object, element);
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }
        }
    }

    public static void retry(AtxClient driver,String fileName, int attr,String findby){
        List<AndroidElement> elementList;
        try {
            elementList = driver.findElementsByXml(fileName, attr, findby ,true);
            if (elementList.size()==0 || elementList==null){
                //重试仍然找不到，抛异常并删除xml
//                driver.removeXmlFile(fileName);
                throw new ElementNotFoundException("根据"+ attr + "查找：" + " 查找元素未找到，请检查当前页面。");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
