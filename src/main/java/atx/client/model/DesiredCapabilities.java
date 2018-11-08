package atx.client.model;

import org.apache.xpath.operations.Bool;

/**
 * 初始化对象数据模型
 * Created by 飞狐 on 2018/4/22.
 */
public class DesiredCapabilities {
    //平台名称
    private String platformName;

    //包名
    private String packageName;

    //远程手机地址
    private String remoteHost;

    //设备序列号
    private String udid;



    private boolean startApp;

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public boolean isStartApp() {
        return startApp;
    }

    public void setStartApp(boolean startApp) {
        this.startApp = startApp;
    }
}
