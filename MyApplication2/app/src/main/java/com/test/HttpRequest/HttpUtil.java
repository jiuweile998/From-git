package com.test.HttpRequest;




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;



public class HttpUtil {

    public static OkHttpClient client;
    private static final String domainName = "http://10.0.2.2:8080";//域名

    static {//创建OkHttpClient实例
        client = new OkHttpClient();
    }

    //默认发送GET请求，若发送的为GET请求，则传递参数为"GET", null, null
    //若发送的为POST请求，则需要传递一个Map对象，将需要传递的数据放在其中
    public static String sendHttpRequest(String url, String method, Map<String, String> map){
        Request.Builder requestBuilder = new Request.Builder();//创建Request.Builder
        requestBuilder.url(domainName+url);//添加URL
        if(method.equals("POST")){//若请求类型为POST，则需要获取要发送的数据，并调用post方法
            FormBody.Builder requestBodyBuilder = new FormBody.Builder();
            for(Map.Entry<String, String> entry: map.entrySet()){
                requestBodyBuilder.add(entry.getKey(), entry.getValue());
            }
            requestBuilder.post(requestBodyBuilder.build());
        }
        // 添加请求类型
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MediaType.parse("multipart/form-data"));
        Request request = requestBuilder.build();//获取Request实例
        try {//获取服务器响应，将返回数据转换为字符串并返回
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;//若获取服务器响应数据失败，则
        }
    }

    //下载文件 fileName格式:/user/admin.jpg
//    public static void downloadFile(String fileName){
//        if(fileName==null||fileName.isEmpty())
//            return;
//        RequestBody requestBody = new MultipartBody.Builder()
//                .addFormDataPart("fileName", fileName)
//                .build();
//        FutureTask<File> task = new FutureTask<>(()->{
//            ResponseBody responseBody = client.newCall(
//                    new Request.Builder()
//                            .post(requestBody)
//                            .url(domainName+"/download")
//                            .build())
//                    .execute()
//                    .body();
//            if(responseBody!=null){
//                if(!externalFilesDir.isEmpty()){
//                    File file = new File(externalFilesDir+fileName);
//                    try(
//                            InputStream in = responseBody.byteStream();
//                            FileOutputStream out = new FileOutputStream(file)
//                    ){
//                        byte[] buf = new byte[1024];
//                        int n;
//                        if((n=in.read(buf))!=-1){
//                            out.write(buf, 0, n);
//                            while ((n=in.read(buf))!=-1)
//                                out.write(buf, 0, n);
//                            return file;
//                        }else{
//                            file.delete();
//                            return null;
//                        }
//                    }catch (Exception e){ e.printStackTrace(); }
//                }
//            }
//            return null;
//        });
//        task.run();
//    }

    //向服务器上传文件
//    public static void uploadFile(Map<String, String> map){
//        String fileName = map.get("fileName");
//        if(fileName==null||fileName.isEmpty())
//            return;
//        new Thread(() -> {
//            try {
//                String url = domainName + "/upload";
//                String path = externalFilesDir+fileName;
//                File file = new File(path);
//                RequestBody fileBody = RequestBody
//                        .create(MediaType.parse("application/octet-stream"), file);
//                MultipartBody.Builder builder = new MultipartBody.Builder();
//                String userName = map.get("userName");
//                String goodId = map.get("goodId");
//                if(userName!=null&&!userName.isEmpty())
//                    builder.addFormDataPart("userName", userName);
//                if(goodId!=null&&!goodId.isEmpty())
//                    builder.addFormDataPart("goodId", goodId);
//                RequestBody requestBody = builder
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("uploadfile", fileName, fileBody)
//                        .build();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .post(requestBody)
//                        .build();
//                client.newCall(request).execute();
//            } catch (Exception e){ e.printStackTrace(); }
//        }).start();
//    }

    //向服务器上传用户头像
//    public static void uploadUserImg(String userName, String imgPath){
//        Map<String, String> map = new HashMap<>();
//        map.put("fileName", imgPath);
//        map.put("userName", userName);
//        uploadFile(map);
//    }
}