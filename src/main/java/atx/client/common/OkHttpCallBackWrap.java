package atx.client.common;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class OkHttpCallBackWrap {

    public static void post(String url) throws IOException{
        File file = new File("/Users/admin/uirecorder.log");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("application/octet-stream", "uirecorder.log", fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(1500, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }

            @Override
            public void onFailure(Call arg0, IOException e) {
                // TODO Auto-generated method stub
                System.out.println(e.toString());

            }

        });
    }


    public static void main(String args[]){
        try
        {
            post("http://192.168.0.204:7912/upload/sdcard/uiauto.txt");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}


