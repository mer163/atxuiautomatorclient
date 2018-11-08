package atx.client.internal;

import atx.client.enums.AttributeMask;
import atx.client.model.AndroidElement;

import java.util.List;

public interface FindBy {

    AndroidElement findElementById(String using);
    List<AndroidElement> findElementsById(String using);

    AndroidElement findElementByName(String using);
    List<AndroidElement> findElementsByName(String using);

    AndroidElement findElementByClass(String className);
    List<AndroidElement> findElementsByClass(String className);

    AndroidElement findElementByXpath(String xpath);
//    List<AndroidElement> findElementsByXpath(String xpath);   //暂不支持

    AndroidElement findElementByDesc(String desc);
    List<AndroidElement> findElementsByDesc(String desc);

    AndroidElement waitForElement(AttributeMask wayToFind, String value);

    List<AndroidElement> findElementsBy(int attr,String str);


    List<AndroidElement> findElementsByXml(String fileName,int att, String str, boolean remove);
    AndroidElement findElementByXml(String fileName,int att, String str);

}

