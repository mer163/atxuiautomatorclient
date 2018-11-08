package atx.client.common;

import atx.client.adb.AndroidElementDumpService;
import atx.client.adb.ElementAttribs;
import atx.client.adb.UiDump;
import atx.client.enums.Const;
import atx.client.model.AndroidElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtils {

    public static boolean ifFileExist(String fileName){
        File directory  = new File(".");
        String path = null;
        File file = null;
        try {
            path = directory.getCanonicalPath();
            path = path + Const.XML_PATH +  fileName;
            file = new File(path);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (file.exists()) {
            return true;
        }else{
            return false;
        }
    }

    public static List<AndroidElement> getumps(String fileName){
        List<AndroidElement> dumps = null;
        File directory  = new File(".");
        String path = null;
        File file = null;
        InputStream input = null;
        try {
            path = directory.getCanonicalPath();
            path = path + Const.XML_PATH + fileName;
            file = new File(path);
            input = new FileInputStream(file);
            dumps = new AndroidElementDumpService().getDumps(input);

        } catch (Exception e){
            e.printStackTrace();
        }

        return dumps!=null ? dumps : null;
    }


    // 获取单个元素属性值集合
    private static HashMap<Integer, String> getAttrib(List<UiDump> dumps,int att, String str) {
        ArrayList<HashMap> attribs = null;
        Map<Integer, String> attrib = new HashMap<Integer, String>();
        for (UiDump dump : dumps) {
            Boolean flag = false;
            switch (att) {
                case 0:
                    flag = str.equals(dump.getText());
                    break;
                case 1:
                    flag = str.equals(dump.getResourceId());
                    break;
                case 2:
                    flag = str.equals(dump.getClassName());
                    break;
                case 3:
                    flag = str.equals(dump.getChecked());
                    break;
                case 4:
                    flag = str.equals(dump.getCheckable());
                    break;
                case 5:
                    flag = str.equals(dump.getContentDesc());
                    break;
                case 6:
                    flag = str.equals(dump.getClickable());
                    break;
                default:
                    break;
            }
            if (flag) {
                attrib.put(ElementAttribs.TEXT, dump.getText());
                attrib.put(ElementAttribs.RESOURCE_ID, dump.getResourceId());
                attrib.put(ElementAttribs.CLASS, dump.getClassName());
                attrib.put(ElementAttribs.CHECKED, dump.getChecked());
                attrib.put(ElementAttribs.CHECKABLE, dump.getCheckable());
                attrib.put(ElementAttribs.CONTENTDESC, dump.getContentDesc());
                attrib.put(ElementAttribs.CLICKABLE, dump.getCheckable());
                attrib.put(ElementAttribs.BOUNDS, dump.getBounds());

                break;
            }
        }

        return (HashMap<Integer, String>) attrib;
    }

    public static List<AndroidElement> parseXmlGetAndroidElements(String fileName,int att, String str,String packageName){
        List<AndroidElement> elementList = new ArrayList<AndroidElement>();
        List<AndroidElement> dumps =null;   //all Androidelements
        //找到xml文件，解析本地xml
        dumps = XmlUtils.getumps(fileName);
        for(AndroidElement element: dumps){
            Boolean flag = false;
            switch (att) {
                case 0:
                    flag = str.equals(element.getText());
                    break;
                case 1:
                    flag = str.equals(element.getResourceName())  || (packageName + ":" + str).equals(element.getResourceName());     // 根据id查找元素时，无论是否带packageName均可以查到
                    break;
                case 2:
                    flag = str.equals(element.getClassName());
                    break;
                case 3:
                    flag = str.equals(element.isChecked());
                    break;
                case 4:
                    flag = str.equals(element.isCheckable());
                    break;
                case 5:
                    flag = str.equals(element.getContentDescription());
                    break;
                case 6:
                    flag = str.equals(element.isClickable());
                    break;
                default:
                    break;
            }
            if (flag){
                elementList.add(element);
            }
        }
        return elementList;


    }


    public static void removeXmlFile(String fileName){
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }



//    // 获取多个元素的属性值集合
//    @SuppressWarnings("rawtypes")
//    public static List<AndroidElement> getAttribs(List<AndroidElement> dumps, int att, String str) {
//        List<AndroidElement> list= null;
//        AndroidElement element = null;
//        ArrayList<HashMap> attribs = null;
//        HashMap<Integer, String> temp = null;
//        attribs = new ArrayList<HashMap>();
//        for (AndroidElement dump : dumps) {
//            Boolean flag = false;
//            switch (att) {
//                case 0:
//                    flag = str.equals(dump.getText());
//                    break;
//                case 1:
//                    flag = str.equals(dump.getResourceName());
//                    break;
//                case 2:
//                    flag = str.equals(dump.getClassName());
//                    break;
//                case 3:
////                    flag =  Boolean.parseBoolean(str) && dump.isChecked();
//                    flag = String.valueOf(dump.isChecked()).equals(str);
////                    flag = str.equals(dump.getChecked());
//                    break;
//                case 4:
////                    flag = str.equals(dump.getCheckable());
//                    flag = String.valueOf(dump.isChecked()).equals(str);
//                    break;
//                case 5:
//                    flag = str.equals(dump.getContentDescription());
//                    break;
//                case 6:
////                    flag = str.equals(dump.getClickable());
//                    flag= String.valueOf(dump.isClickable()).equals(str);
//                    break;
//                default:
//                    break;
//            }
//            if (flag) {
////                temp = new HashMap<Integer, String>();
////                temp.put(ElementAttribs.TEXT, dump.getText());
////                temp.put(ElementAttribs.RESOURCE_ID, dump.getResourceId());
////                temp.put(ElementAttribs.CLASS, dump.getClassName());
////                temp.put(ElementAttribs.CHECKED, dump.getChecked());
////                temp.put(ElementAttribs.CHECKABLE, dump.getCheckable());
////                temp.put(ElementAttribs.CONTENTDESC, dump.getContentDesc());
////                temp.put(ElementAttribs.CLICKABLE, dump.getCheckable());
////                temp.put(ElementAttribs.BOUNDS, dump.getBounds());
////
////                attribs.add(temp);
//                element.
//
//            }
//            temp = null;
//        }
//
//        return null;
//    }
}
