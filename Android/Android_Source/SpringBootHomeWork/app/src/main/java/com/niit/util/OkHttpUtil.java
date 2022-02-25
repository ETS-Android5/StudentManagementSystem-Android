package com.niit.util;

import android.os.Handler;
import android.os.Message;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    private static final int GET = 1;
    private static final int POST = 2;
    private static final int FAILURE = -1;
    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final Gson gson = new Gson();
    private Object obj;//要传入的对象
    private String url;//请求路径  "https://本机ip地址:8086/restful/..."
    private  Message msg;
    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //自定义getDataFromPost方法,里面创建新的子线程,用于发送POST请求
    public void getDataFromPost(Handler handler){
        msg=Message.obtain();

        new Thread(){
            @Override
            public void run() {

                String json = gson.toJson(obj);
                RequestBody formBody = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();

                try (Response response = client.newCall(request).execute()) {

                    if (!response.isSuccessful()) {
                        msg.what=FAILURE;
                        msg.obj="sorry,network busy!";
                    }else{
                    msg.what=POST;
                    msg.obj=response.body().string();}
                    handler.sendMessage(msg);
                } catch (IOException e) {
                  //  e.printStackTrace();
                    msg.what=FAILURE;
                    msg.obj="sorry,network busy!";
                    handler.sendMessage(msg);

                }
            }
        }.start();

    }

    //自定义getDataFromGet方法,里面创建新的子线程,用于发送GET请求
    public void getDataFromGet(Handler handler){
        msg=Message.obtain();
        new Thread(){
            @Override
            public void run(){
                Request request = new Request.Builder()
                        .url(url)//请求的url,要替换.
                        .build();//构建

                try (Response response = client.newCall(request).execute()) {//获取response对象

                    if (!response.isSuccessful()) {
                        msg.what=FAILURE;
                        msg.obj="sorry,network busy!";


                    }else{
                        msg.what=GET;
                        msg.obj=response.body().string();
                    }

                    handler.sendMessage(msg);
                } catch (IOException e) {
                    msg.what=FAILURE;
                    msg.obj="sorry,network busy!";
                    handler.sendMessage(msg);
                }
            }

        }.start();
    }




}
