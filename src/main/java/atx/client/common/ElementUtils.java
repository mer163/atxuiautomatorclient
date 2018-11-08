package atx.client.common;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ElementUtils {

    /**
     * 基础请求数据模型
     * @param method
     * @param params
     * @return
     */
    public static JSONObject baseRequestJson(String method, Object params){
        JSONObject requestJson = new JSONObject();
        requestJson.put("jsonrpc","2.0");
        requestJson.put("id", SortUtils.jsonrpcIdValue(method));
        requestJson.put("method",method);
        requestJson.put("params",params);

        return requestJson;
    }



    /**
     *
     * [73,941][196,1038]
     * {"bottom":1038,"left":73,"right":196,"top":941}
     * 计算坐标
     */
    private List<Float>  convertCoordinate(String boundsValue){

        String[] str = boundsValue.split("[^0-9]");

        List<Integer> list = new ArrayList<Integer>();

        for(String s : str){
            if(!s.isEmpty()){
                list.add(Integer.valueOf(s));
            }
        }
        List<Float> result = new ArrayList<Float>();

        Integer left = list.get(0);
        Integer top = list.get(1);
        Integer right = list.get(2);
        Integer bottom = list.get(3);

        Float x = 0f;
        Float y = 0f;

        y = Float.valueOf(String.valueOf((bottom - top) / 2 + top));

        x = Float.valueOf(String.valueOf((right - left) / 2 + left));

        result.add(x);
        result.add(y);

        return result;
    }
}



