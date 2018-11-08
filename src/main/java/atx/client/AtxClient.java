package atx.client;

import atx.client.common.*;
import atx.client.enums.Attribute;
import atx.client.enums.AttributeMask;
import atx.client.enums.Const;
import atx.client.enums.MethodEnum;
import atx.client.model.*;
import atx.client.remote.RemoteWebDriver;
import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;

/**
 * Created by 飞狐 on 2018/4/22.
 */
public class AtxClient extends RemoteWebDriver{

//    public static AtxDriver atxContexts = new AtxDriver();
//
//    private ElementObj element = new ElementObj(atxContexts);
//
//    private Session session = Session.getInstance(atxContexts);
////
    private OkHttpClientMethod okHttpClientMethod = OkHttpClientMethod.getInstance();

    private String host ;


    public AtxClient(DesiredCapabilities desiredCapabilities){

        super(desiredCapabilities);
        this.host = "http://" + desiredCapabilities.getRemoteHost() + ":" +Const.PORT_UI;
        Remote.setHost("http://" + desiredCapabilities.getRemoteHost() + ":" +Const.PORT_UI);
    }



//    /**
//     * 判断元素是否存在
//     * @param jsonObjectList
//     * @return
//     * @throws Exception
//     */
//    private boolean findElement(List<JSONObject> jsonObjectList) throws Exception {
//
//        List<Object> params = new ArrayList<Object>();
//        params.addAll(jsonObjectList);
//        params.add(20000);
//
//
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("jsonrpc","2.0");
//        requestParams.put("id",SortUtils.jsonrpcIdValue(MethodEnum.WAIT_FOR_EXISITS.getValue()));
//        requestParams.put("method", MethodEnum.WAIT_FOR_EXISITS.getValue());
//        requestParams.put("params",params);
//
//
//        //查找元素是否存在
//        JSONObject response = (JSONObject) okHttpClientMethod.postByteMethod(Remote.getHost() + Const.BASE_URI,requestParams);
//        boolean isExists = response.getBoolean("result");
//        if (isExists) {
//            //把元素信息存储起来
////
////            JSONArray arrsy = requestParams.getJSONArray("params");
////            List<Object> list2 = JSONArray.toList(arrsy, new Object(), new JsonConfig());
//
//            int index = getNotJSONIndex(params);
//            params.remove(index);
////            atxContexts.setSearchEleInfo(params);
//            //获取元素基础信息
//            JSONObject objInfo = (JSONObject) okHttpClientMethod.postByteMethod(Remote.getHost() + Const.BASE_URI, ElementObj.baseRequestJson(MethodEnum.OBJ_INFO.getValue(),params));
////            atxContexts.setObjInfo(objInfo);
////            atxContexts.setCoordinateInfo(null);
//            return true;
//        } else {
//            return false;
//        }
//
//    }

    /**
     * 查找出不是json对象的坐标
     * @param params
     * @return
     */
    private Integer getNotJSONIndex(List<Object> params){
        if(params != null) {
            for(int i = 0 ; i < params.size(); i++){
                if(params.get(i) instanceof JSONObject){

                }else {
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * 根据 text 查找
     * @param name
     * @return
     * @throws Exception
     */
    public AndroidElement findElementByName(String name)  {

        return super.findElementByName(name);

    }


    /**
     * 休眠
     * @param ms
     * @return
     * @throws Exception
     */
    public AtxClient sleep(int ms) throws Exception {
        Thread.sleep(ms);
        return this;
    }

    /**
     * 元素是否存在
     * @param wayToFind
     * @param value
     * @return
     * @throws Exception
     */
//    public boolean isElementExist(AttributeMask wayToFind, String value) throws Exception {
//        try {
//
//
//            JSONObject jsonObject = new JSONObject();
//            List<Integer> fields = new ArrayList<Integer>();
//            fields.add(wayToFind.getValue());
//
//            jsonObject.put("mask", SortUtils.maskValue(fields));
//            jsonObject.put("childOrSibling",new ArrayList<>());
//            jsonObject.put("childOrSiblingSelector",new ArrayList<>());
//            jsonObject.put(wayToFind.getDes(),value);
//
//            List<JSONObject> jsonObjectList = new ArrayList<>();
//            jsonObjectList.add(jsonObject);
//            return findElement(jsonObjectList);
//        } catch (Exception e) {
//            // TODO: handle exception
//            return false;
//        }

//		return element.isDisplayed();
//    }


    /**
     * 获取设备信息
     * @return
     */
    public DeviceInfo getDeviceInfo(){
         JSONObject result =  super.excute(this.getHost() + Const.BASE_URI,ElementObj.baseRequestJson(MethodEnum.DEVICE_INFO.getValue(),new JSONObject()));
//         = JSONObject.fromObject(okHttpClientMethod.postByteMethod(Remote.getHost() + Const.BASE_URI,ElementObj.baseRequestJson(MethodEnum.DEVICE_INFO.getValue(),new JSONObject())));

        DeviceInfo deviceInfo = new DeviceInfo();

        if(result.containsKey("result")){
            deviceInfo.setCurrentPackageName(result.getJSONObject("result").getString("currentPackageName"));
            deviceInfo.setDisplayHeight(result.getJSONObject("result").getInt("displayHeight"));
            deviceInfo.setDisplayWidth(result.getJSONObject("result").getInt("displayWidth"));
            deviceInfo.setDisplayRotation(result.getJSONObject("result").getInt("displayRotation"));
        }
        return deviceInfo;
    }

//    /**
//     * 获取当前app xml结构
//     */
//    public void dumpHierarchy(){
//        List<Object> params = new ArrayList<Object>();
//        params.add(false);
//        params.add(null);
//        JSONObject result = JSONObject.fromObject(okHttpClientMethod.postByteMethod(Remote.getHost() + Const.BASE_URI, ElementObj.baseRequestJson(MethodEnum.DUMP_WINDOWS_HIERARCHY.getValue(),params)));
//        FileMethodUtils.generateXML(Const.XML_PATH,result.getString("result"));
//
//    }

    /**
     * 获取当前activity
     */
    public String getCurrentActivity(){

        Map<String,Object> headers = new HashMap<String,Object>();
        headers.put("Content-Type","application/x-www-form-urlencoded");

        StringBuffer commandStr = new StringBuffer();
        commandStr.append("dumpsys activity top");

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("command",commandStr.toString());

        JSONObject result = JSONObject.fromObject(super.excute(this.getHost()+Const.SHELL_URI,params,headers));
//        JSONObject.fromObject(okHttpClientMethod.postMethod(Remote.getHost()+Const.SHELL_URI,params,headers));
        if(result.containsKey("output")){
            String value = result.getString("output").split("\\n")[1];
            return value.split(" ")[3];
        }
        return null;
    }

    /**
     * 点击 按钮事件
     * @param keyEvent
     */
    public void press(String keyEvent){
        List<Object> params = new ArrayList<Object>();
        params.add(keyEvent);
        super.excute(this.getHost() + Const.BASE_URI, ElementObj.baseRequestJson(MethodEnum.PRESS_KEY.getValue(),params));
//        okHttpClientMethod.postByteMethod(Remote.getHost() + Const.BASE_URI, ElementObj.baseRequestJson(MethodEnum.PRESS_KEY.getValue(),params));
    }


    /**
     * 截图
     * @param fileName
     * @throws Exception
     */
    public void takeScreenshot(final String fileName) throws Exception {

        okHttpClientMethod.downLoadMethod(this.getHost() + Const.SCREENSHOT_URI, fileName);

    }

    /**
     * 启动服务
     */
    public void startUiAutomator(){
        okHttpClientMethod.postMethod(this.getHost() + Const.UIAUTOMAROR_URI,new HashMap<String,Object>() ,new HashMap<String,Object>());
    }

    /**
     * 停止服务
     */
    public void stopUiAutomator(){
        okHttpClientMethod.deleteMethod(this.getHost() + Const.UIAUTOMAROR_URI,null,new HashMap<String,Object>() ,new HashMap<String,Object>());
    }


    /**
     * 设置输入法
     */
    public void setKeybord(String keybord){

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setRemoteHost(this.getHost());

        String keyboardList = ShellUtils.getInstance(desiredCapabilities).shellPost("ime list -s");

        System.out.println(keyboardList.split("\\n")[0]);
        System.out.println(keyboardList.split("\\n")[1]);
        System.out.println(keyboardList.split("\\n")[2]);

        for(String str : keyboardList.split("\\n")){
            if(str.equals(keybord)){
                ShellUtils.getInstance(desiredCapabilities).shellPost("ime set " + keybord );
            }
        }
    }


    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }
}