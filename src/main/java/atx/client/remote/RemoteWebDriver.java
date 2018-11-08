package atx.client.remote;

import atx.client.AtxClient;
import atx.client.WebDriver;
import atx.client.adb.*;
import atx.client.common.*;
import atx.client.enums.Attribute;
import atx.client.enums.AttributeMask;
import atx.client.enums.Const;
import atx.client.enums.MethodEnum;
import atx.client.internal.*;
import atx.client.model.AndroidElement;
import atx.client.model.AtxDriver;
import atx.client.model.DesiredCapabilities;
import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import netscape.javascript.JSObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.xpath.operations.And;
import org.apache.xpath.operations.Bool;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;

public class RemoteWebDriver implements WebDriver, FindBy{

    private static final Logger logger = Logger.getLogger(RemoteWebDriver.class.getName());
    private OkHttpClientMethod okHttpClientMethod = OkHttpClientMethod.getInstance();
    private String host ;
    private String packageName;



//    public static AtxDriver atxContexts = new AtxDriver();

//    private ElementObj element = new ElementObj(atxContexts);

//    private Session session = Session.getInstance(atxContexts);

//    public RemoteWebDriver(DesiredCapabilities desiredCapabilities,String host){
//
//        session.createSession(desiredCapabilities);
//    }

    public RemoteWebDriver(){

    }

    public RemoteWebDriver(DesiredCapabilities desiredCapabilities){
        this.host = "http://" + desiredCapabilities.getRemoteHost() + ":" + Const.PORT_UI;
        this.packageName = desiredCapabilities.getPackageName();
        this.udid = desiredCapabilities.getUdid();
//        init();
//        this(desiredCapabilities,desiredCapabilities.getRemoteHost());


    }

//    public void init(){
//        if(StringUtils.isNotEmpty(this.getHost())){
//            if(StringUtils.isNotEmpty(this.getPackageName())){
//                try {
//                    startApp(this.getHost() + Const.SHELL_URI,this.getPackageName());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//    }

    public RemoteWebDriver(OkHttpClientMethod okHttpClientMethod,DesiredCapabilities desiredCapabilities){
        this.okHttpClientMethod = okHttpClientMethod;
    }



    public Object excute(String url,Map<String, Object> bodyParams, Map<String, Object> headerParams){
        Object response = okHttpClientMethod.postMethod(url,bodyParams,headerParams);
        return response;
    }


    public JSONObject excute(String url,final Object params){
        Object response = (JSONObject) okHttpClientMethod.postByteMethod(url ,params);
        JSONObject res = JSONObject.fromObject(response);
        return res;
    }


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

    @Override
    public AndroidElement findElementById(String resourceId) {

        // resourceId转jsonObject
        JSONObject jsonObject = new JSONObject();

        List<Integer> fields = new ArrayList<Integer>();
        fields.add(AttributeMask.RESOURCEID.getValue());

        jsonObject.put("mask", SortUtils.maskValue(fields));
        jsonObject.put(AttributeMask.RESOURCEID.getDes(), resourceId);

        List<JSONObject> jsonObjectList = new ArrayList<>();
        jsonObjectList.add(jsonObject);

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

        AndroidElement element=null;
        try {
            element = findElement(jsonObjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element!=null ? element : null;

//
//
//            JSONObject response = excute(atx.client.model.Remote.getHost()+Const.BASE_URI,requestParams);
////            JSONObject response1 = (JSONObject) okHttpClientMethod.postByteMethod(atx.client.model.Remote.getHost(),jsonObjectList);
//            boolean isExists = response.getBoolean("result");
//            if (isExists) {
//                //把元素信息存储起来
//
////            JSONArray arrsy = requestParams.getJSONArray("params");
////            List<Object> params = JSONArray.toList(arrsy, new Object(), new JsonConfig());
//
//                int index = getNotJSONIndex(params);
//                params.remove(index);
//
////            atxContexts.setSearchEleInfo(params);
//                //获取元素基础信息
//                JSONObject objInfo = (JSONObject) okHttpClientMethod.postByteMethod(atx.client.model.Remote.getHost() + Const.BASE_URI, ElementUtils.baseRequestJson(MethodEnum.OBJ_INFO.getValue(),params));
//                AndroidElement element = new AndroidElement();
//                if (objInfo!=null){
//                    element.setChildCount(objInfo.getJSONObject("result").getInt("childCount"));
//                    element.setClassName(objInfo.getJSONObject("result").getString("className"));
//                    element.setContentDescription(objInfo.getJSONObject("result").getString("contentDescription"));
//                    element.setPackageName(objInfo.getJSONObject("result").getString("packageName"));
//                    element.setResourceName(objInfo.getJSONObject("result").getString("resourceName"));
//                    element.setText(objInfo.getJSONObject("result").getString("text"));
//                    element.setCheckable(objInfo.getJSONObject("result").getBoolean("checkable"));
//                    element.setChecked(objInfo.getJSONObject("result").getBoolean("checked"));
//                    element.setClickable(objInfo.getJSONObject("result").getBoolean("clickable"));
//                    element.setEnable(objInfo.getJSONObject("result").getBoolean("enabled"));
//                    element.setFocuseable(objInfo.getJSONObject("result").getBoolean("focusable"));
//                    element.setFocused(objInfo.getJSONObject("result").getBoolean("focused"));
//                    element.setClickable(objInfo.getJSONObject("result").getBoolean("longClickable"));
//                    element.setScrollable(objInfo.getJSONObject("result").getBoolean("scrollable"));
//                    element.setSelected(objInfo.getJSONObject("result").getBoolean("selected"));
//
//                    //计算坐标点并保存
//                    List<Float> coordinateInfo  = new ArrayList<Float>();
//                    JSONObject bounds = objInfo.getJSONObject("result").getJSONObject("bounds");
//                    Integer right = bounds.getInt("right");
//                    Integer left = bounds.getInt("left");
//                    Integer top = bounds.getInt("top");
//                    Integer bottom = bounds.getInt("bottom");
//
//                    Float x = 0f;
//                    Float y = 0f;
//
//                    y = Float.valueOf(String.valueOf((bottom - top) / 2 + top));
//
//                    x = Float.valueOf(String.valueOf((right - left) / 2 + left));
//
//                    coordinateInfo.add(x);
//                    coordinateInfo.add(y);
//                    element.setCoordinateInfo(coordinateInfo);
//
//                }
//
//
//                return element;
//            } else {
//                return null;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  null;
    }



    /**
     * 启动packeName命令
     * @param packageName
     * @return
     */
    private String convertPackageNameShellCommand(String packageName){
        StringBuffer commandStr = new StringBuffer();
        commandStr.append("monkey -p ");
        commandStr.append(packageName);
        commandStr.append(" -c android.intent.category.LAUNCHER 1");

        return commandStr.toString();
    }


    private void startApp(String url ,String packageName) throws  Exception{
        OkHttpClientMethod okHttpClientMethod = OkHttpClientMethod.getInstance();

        Map<String,Object> headers = new HashMap<String,Object>();
        headers.put("Content-Type","application/x-www-form-urlencoded");

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("command",convertPackageNameShellCommand(packageName));

        okHttpClientMethod.postMethod(url,params,headers);
        Thread.sleep(3000); //启动app后强制休眠3s 避免其他操作冲突
    }


    @Override
    public List<AndroidElement> findElementsById(String id) {

        List<AndroidElement> elementList = findElementsBy(ElementAttribs.RESOURCE_ID,id);

        return elementList.size()>0 ? elementList : null;
    }

    @Override
    public AndroidElement findElementByName(String name) {
        JSONObject jsonObject = new JSONObject();
        List<Integer> fields = new ArrayList<Integer>();
        fields.add(AttributeMask.TEXT.getValue());

        jsonObject.put("mask", SortUtils.maskValue(fields));
        jsonObject.put("childOrSibling",new ArrayList<>());
        jsonObject.put("childOrSiblingSelector",new ArrayList<>());
        jsonObject.put(AttributeMask.TEXT.getDes(),name);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        jsonObjectList.add(jsonObject);
        AndroidElement element=null;
        try {
            element= findElement(jsonObjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        findElement(jsonObjectList);
//        return isExist ? element : null;

        return element!=null ? element : null;
    }

    @Override
    public List<AndroidElement> findElementsByName(String name) {

        List<AndroidElement> elementList = findElementsBy(ElementAttribs.TEXT,name);
        return elementList.size()>0 ? elementList : null;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    private String udid;

    private boolean findElements(List<JSONObject> jsonObjectList) throws Exception {
        return false;
    }

    /**
     * 判断元素是否存在
     * @param jsonObjectList
     * @return
     * @throws Exception
     */
    private AndroidElement findElement(List<JSONObject> jsonObjectList) throws Exception {

        List<Object> params = new ArrayList<Object>();
        params.addAll(jsonObjectList);
        params.add(20000);
        JSONObject requestParams = new JSONObject();
        requestParams.put("jsonrpc","2.0");
        requestParams.put("id",SortUtils.jsonrpcIdValue(MethodEnum.WAIT_FOR_EXISITS.getValue()));
        requestParams.put("method", MethodEnum.WAIT_FOR_EXISITS.getValue());
        requestParams.put("params",params);

        try {

//            JSONObject response = excute(atx.client.model.Remote.getHost()+Const.BASE_URI,requestParams);
//            JSONObject response1 = (JSONObject) okHttpClientMethod.postByteMethod(atx.client.model.Remote.getHost(),jsonObjectList);
//            boolean isExists = response.getBoolean("result");
            boolean isExists = true;
            if (isExists) {
                //把元素信息存储起来

//            JSONArray arrsy = requestParams.getJSONArray("params");
//            List<Object> params = JSONArray.toList(arrsy, new Object(), new JsonConfig());

                int index = getNotJSONIndex(params);
                params.remove(index);

//            atxContexts.setSearchEleInfo(params);
                //获取元素基础信息
                JSONObject objInfo = (JSONObject) okHttpClientMethod.postByteMethod(atx.client.model.Remote.getHost() + Const.BASE_URI, ElementUtils.baseRequestJson(MethodEnum.OBJ_INFO.getValue(),params));
                AndroidElement element = new AndroidElement();
                if (objInfo!=null){
                    element.setChildCount(objInfo.getJSONObject("result").getInt("childCount"));
                    element.setClassName(objInfo.getJSONObject("result").getString("className"));
                    element.setContentDescription(objInfo.getJSONObject("result").getString("contentDescription"));
                    element.setPackageName(objInfo.getJSONObject("result").getString("packageName"));
                    element.setResourceName(objInfo.getJSONObject("result").getString("resourceName"));
                    element.setText(objInfo.getJSONObject("result").getString("text"));
                    element.setCheckable(objInfo.getJSONObject("result").getBoolean("checkable"));
                    element.setChecked(objInfo.getJSONObject("result").getBoolean("checked"));
                    element.setClickable(objInfo.getJSONObject("result").getBoolean("clickable"));
                    element.setEnable(objInfo.getJSONObject("result").getBoolean("enabled"));
                    element.setFocuseable(objInfo.getJSONObject("result").getBoolean("focusable"));
                    element.setFocused(objInfo.getJSONObject("result").getBoolean("focused"));
                    element.setClickable(objInfo.getJSONObject("result").getBoolean("longClickable"));
                    element.setScrollable(objInfo.getJSONObject("result").getBoolean("scrollable"));
                    element.setSelected(objInfo.getJSONObject("result").getBoolean("selected"));

                    //计算坐标点并保存
                    List<Float> coordinateInfo  = new ArrayList<Float>();
                    JSONObject bounds = objInfo.getJSONObject("result").getJSONObject("bounds");
                    Integer right = bounds.getInt("right");
                    Integer left = bounds.getInt("left");
                    Integer top = bounds.getInt("top");
                    Integer bottom = bounds.getInt("bottom");

                    Float x = 0f;
                    Float y = 0f;

                    y = Float.valueOf(String.valueOf((bottom - top) / 2 + top));

                    x = Float.valueOf(String.valueOf((right - left) / 2 + left));

                    coordinateInfo.add(x);
                    coordinateInfo.add(y);
                    element.setCoordinateInfo(coordinateInfo);

                }


                return element;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public AndroidElement findElementByClass(String className) {
        JSONObject jsonObject = new JSONObject();

        List<Integer> fields = new ArrayList<Integer>();
        fields.add(AttributeMask.CLASS_NAME.getValue());

        jsonObject.put("mask", SortUtils.maskValue(fields));
        jsonObject.put(AttributeMask.CLASS_NAME.getDes(), className);

        List<JSONObject> jsonObjectList = new ArrayList<>();
        jsonObjectList.add(jsonObject);

        AndroidElement element =null;
        try {
            element=findElement(jsonObjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return element!=null ? element : null;
    }

    @Override
    public List<AndroidElement> findElementsByClass(String className) {
        List<AndroidElement> elementList = findElementsBy(ElementAttribs.CLASS,className);
        return elementList.size()>0 ? elementList : null;
    }

    @Override
    public AndroidElement findElementByDesc(String desc) {

        JSONObject jsonObject = new JSONObject();

        List<Integer> fields = new ArrayList<Integer>();
        fields.add(AttributeMask.DESCRIPTION.getValue());

        jsonObject.put("mask", SortUtils.maskValue(fields));
        jsonObject.put(AttributeMask.DESCRIPTION.getDes(), desc);

        List<JSONObject> jsonObjectList = new ArrayList<>();
        jsonObjectList.add(jsonObject);
        AndroidElement element =null;
        try {
            element =findElement(jsonObjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return element!=null ? element : null;
    }

    @Override
    public List<AndroidElement> findElementsByDesc(String desc) {
        List<AndroidElement> elementList = findElementsBy(ElementAttribs.CONTENTDESC,desc);
        return elementList.size()>0 ? elementList : null;
    }

    @Override
    public AndroidElement waitForElement(AttributeMask wayToFind, String value) {
        int count = 0;
        int timeLeft = Const.WAIT_ElEMENT_TIMEOUT;
        boolean satisfied = false;
        AndroidElement element =null;
        while (timeLeft > 0) {
            boolean elementExist = false;
            System.out.println(String.format("attempt to search the element for %d times", count++));
            try {
                element = isElementExist(wayToFind, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (element==null) {
                // not find element ,keep searching
                try {
                    Thread.sleep(Const.WAIT_ELEMENT_TIME_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeLeft -= Const.WAIT_ELEMENT_TIME_INTERVAL;
            } else {
                // finded , break
                satisfied = true;
//                getElement(wayToFind, value);
                break;
            }
        }
        if (!satisfied) {
            System.out.println("can't find the element:" + value);
            return null;
        }
        return element;
    }

    @Override
    public List<AndroidElement> findElementsBy(int attr,String str) {
        List<AndroidElement> elementList = new ArrayList<AndroidElement>();
        List<AndroidElement> dumps =null;
        String fileName="temp.xml";
        String filepath = this.getUdid() + "/" + fileName;

        dumpHierarchy(this.getUdid(),fileName);
        elementList = XmlUtils.parseXmlGetAndroidElements(filepath,attr,str,this.getPackageName());

        return elementList;
    }

    public void removeXmlFile(String fileName){
        String filepath = this.getUdid() + "/" + fileName;

        File directory = new File(".");
        String path = null;

        try {
            path = directory.getCanonicalPath();
            path = path + Const.XML_PATH + filepath;
            XmlUtils.removeXmlFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AndroidElement> findElementsByXml(String fileName,int att, String str,boolean remove) {
        List<AndroidElement> elementList = new ArrayList<AndroidElement>();
        List<AndroidElement> dumps =null;
        String filepath = this.getUdid() + "/" + fileName;

        //判断xml文件是否存在，不存在则进行创建。
        if(XmlUtils.ifFileExist(filepath)){
            if(remove){
                removeXmlFile(fileName);
                dumpHierarchy(this.getUdid(),fileName);
            }
            elementList = XmlUtils.parseXmlGetAndroidElements(filepath,att,str,this.getPackageName());
        }else{
            //未找到xml文件，进行dump获取xml
            dumpHierarchy(this.getUdid(),fileName);
            elementList = XmlUtils.parseXmlGetAndroidElements(filepath,att,str,this.getPackageName());
        }
        return elementList;
    }

    @Override
    public AndroidElement findElementByXml(String fileName,int att, String str) {
        List<AndroidElement> elementList = findElementsByXml(fileName,att,str,false);
        if (elementList.size()>=1){
            return elementList.get(0);
        }
        return null;
    }

    /**
     * 元素是否存在
     * @param wayToFind
     * @param value
     * @return
     * @throws Exception
     */
    public AndroidElement isElementExist(AttributeMask wayToFind, String value) throws Exception {
        try {
            JSONObject jsonObject = new JSONObject();
            List<Integer> fields = new ArrayList<Integer>();
            fields.add(wayToFind.getValue());

            jsonObject.put("mask", SortUtils.maskValue(fields));
            jsonObject.put("childOrSibling", new ArrayList<>());
            jsonObject.put("childOrSiblingSelector", new ArrayList<>());
            jsonObject.put(wayToFind.getDes(), value);

            List<JSONObject> jsonObjectList = new ArrayList<>();
            jsonObjectList.add(jsonObject);
            AndroidElement element = findElement(jsonObjectList);

            return element != null ? element : null;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public AndroidElement findElementByXpath(String xpath) {

        //第一步肯定是先获取xml咯
        dumpHierarchy("","tmp.xml");

        File directory  = new File(".");
        String path = null;
        Document document =null;
        try {
            path = directory.getCanonicalPath();
            path = path + Const.XML_PATH;
            document = new SAXReader().read(new File(path));
        }catch (Exception e){
            e.printStackTrace();
        }

        List<Node> list = document.selectNodes(xpath);

        boolean isExist = false;

        if(list != null && list.size() == 0){
            isExist = true;
        }

        if(list.size() > 1){
            System.out.println("xpath元素指定不够精确，元素过多");
            return null;
        }

        isExist = true;
        Element node = (Element) list.get(0);
        //主要目标是根据坐标进行点击，因此 计算坐标
//        convertCoordinate(node.attribute("bounds").getValue());


        Map<String,Object> searchObjs = new HashMap<String,Object>();

        if(StringUtils.isNotEmpty(node.attribute(Attribute.INDEX.getName()).getValue())) {
            searchObjs.put(AttributeMask.INDEX.getDes(), node.attribute(Attribute.INDEX.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.PACKAGE.getName()).getValue())) {
            searchObjs.put(AttributeMask.PACKAGE_NAME.getDes(), node.attribute(Attribute.PACKAGE.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.CLASS.getName()).getValue())) {
            searchObjs.put(AttributeMask.CLASS_NAME.getDes(), node.attribute(Attribute.CLASS.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.TEXT.getName()).getValue())) {
            searchObjs.put(AttributeMask.TEXT.getDes(), node.attribute(Attribute.TEXT.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.CONTENT_DESC.getName()).getValue())) {
            searchObjs.put(AttributeMask.DESCRIPTION.getDes(), node.attribute(Attribute.CONTENT_DESC.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.RESOURCE_ID.getName()).getValue())){
            searchObjs.put(AttributeMask.RESOURCEID.getDes(),node.attribute(Attribute.RESOURCE_ID.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.CHECKABLE.getName()).getValue())) {
            searchObjs.put(AttributeMask.CHECKABLE.getDes(), node.attribute(Attribute.CHECKABLE.getName()).getValue());
        }
        if(StringUtils.isNotEmpty(node.attribute(Attribute.CLICKABLE.getName()).getValue())) {
            searchObjs.put(AttributeMask.CLICKABLE.getDes(), node.attribute(Attribute.CLICKABLE.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.CHECKED.getName()).getValue())) {
            searchObjs.put(AttributeMask.CHECKED.getDes(), node.attribute(Attribute.CHECKED.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.ENABLED.getName()).getValue())) {
            searchObjs.put(AttributeMask.ENABLED.getDes(), node.attribute(Attribute.ENABLED.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.CHECKABLE.getName()).getValue())) {
            searchObjs.put(AttributeMask.CHECKABLE.getDes(), node.attribute(Attribute.CHECKABLE.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.FOCUSED.getName()).getValue())) {
            searchObjs.put(AttributeMask.FOCUSED.getDes(), node.attribute(Attribute.FOCUSED.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.LONG_CLICKABLE.getName()).getValue())) {
            searchObjs.put(AttributeMask.LONGCLICKABLE.getDes(), node.attribute(Attribute.LONG_CLICKABLE.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.SCROLLABLE.getName()).getValue())) {
            searchObjs.put(AttributeMask.SCROLLABLE.getDes(), node.attribute(Attribute.SCROLLABLE.getName()).getValue());
        }

        if(StringUtils.isNotEmpty(node.attribute(Attribute.SELECTED.getName()).getValue())) {
            searchObjs.put(AttributeMask.SELECTED.getDes(), node.attribute(Attribute.SELECTED.getName()).getValue());
        }
        AndroidElement element=null;
        try {
            element =elementByMult(searchObjs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;

    }

//    @Override
//    public List<AndroidElement> findElementsByXpath(String xpath) {
//        List<AndroidElement> elementList = findElementsBy(ElementAttribs.XPATH,xpath);
//        return elementList.size()>0 ? elementList : null;
//    }


    /**
     *
     * 多元素查找匹配
     * 判断元素是否存在
     * 详细支持对象元素查看 AttributeMask 枚举类
     * @param searchParams
     * @return
     * @throws Exception
     */
    public AndroidElement elementByMult(Map<String,Object> searchParams) throws Exception {
        JSONObject jsonObject = new JSONObject();
        List<Integer> fields = new ArrayList<Integer>();
        jsonObject.put("childOrSibling",new ArrayList<>());
        jsonObject.put("childOrSiblingSelector",new ArrayList<>());
        for (String key : searchParams.keySet()) {
            fields.add(AttributeMask.iterationFindByDes(key).getValue());
            jsonObject.put(AttributeMask.iterationFindByDes(key).getDes(),searchParams.get(key).toString());
        }
        jsonObject.put("mask", SortUtils.maskValue(fields));
        List<JSONObject> jsonObjectList = new ArrayList<>();
        jsonObjectList.add(jsonObject);
        return findElement(jsonObjectList);
    }

    /**
     * 获取当前app xml结构
     */
    public void dumpHierarchy(String udid,String fileName ){
        List<Object> params = new ArrayList<Object>();
        params.add(false);
        params.add(null);
        System.out.println(atx.client.model.Remote.getHost() );
        System.out.println(Const.BASE_URI);
        JSONObject result = JSONObject.fromObject(okHttpClientMethod.postByteMethod(atx.client.model.Remote.getHost() + Const.BASE_URI, ElementObj.baseRequestJson(MethodEnum.DUMP_WINDOWS_HIERARCHY.getValue(),params)));
        FileMethodUtils.generateXML(Const.XML_PATH+ udid +"/",  fileName,result.getString("result"));

    }
}

