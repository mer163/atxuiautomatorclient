package atx.client.model;

import atx.client.AtxClient;
import atx.client.common.ElementUtils;
import atx.client.common.OkHttpClientMethod;
import atx.client.common.SortUtils;
import atx.client.enums.Const;
import atx.client.enums.MethodEnum;
import com.google.gson.JsonObject;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.List;

public class AndroidElement {

    private int childCount;
    private String className;
    private String contentDescription;
    private String packageName;
    private String resourceName;
    private String text;
    private boolean checkable;
    private boolean checked;
    private boolean clickable;
    private boolean enable;
    private boolean focuseable;
    private boolean focused;
    private boolean longClickable;

    public AndroidElement(){
        super();
    }


    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCheckable() {
        return checkable;
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isFocuseable() {
        return focuseable;
    }

    public void setFocuseable(boolean focuseable) {
        this.focuseable = focuseable;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isLongClickable() {
        return longClickable;
    }

    public void setLongClickable(boolean longClickable) {
        this.longClickable = longClickable;
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<Float> getCoordinateInfo() {
        return coordinateInfo;
    }

    public void setCoordinateInfo(List<Float> coordinateInfo) {
        this.coordinateInfo = coordinateInfo;
    }

    private boolean scrollable;
    private boolean selected;
    private List<Float> coordinateInfo; //坐标点

//    private int bottom;
//    private int top;
//    private int left;
//    private int right;


    /**
     * 点击事件
     * @throws Exception
     */
    public boolean click(){
        OkHttpClientMethod okHttpClientMethod = OkHttpClientMethod.getInstance();
        JSONObject response=null;
        if(this!=null){

            try {
                response = (JSONObject) okHttpClientMethod.postByteMethod(Remote.getHost() + Const.BASE_URI, ElementUtils.baseRequestJson(MethodEnum.CLICK.getValue(),this.getCoordinateInfo()));
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response.getBoolean("result");

        }else{
            return false;
        }

    }



}
