package com.test.HttpRequest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.test.MyApplication;
import com.test.Novel_zhuye;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class ForNovelTxtHttpRequest extends Service {

    String URL="/MyUser/Forbooktxt";

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        //获取小说的id
        String bookidfortxt=intent.getStringExtra("bookid_fortxt");

        Map<String,String> map=new HashMap<>();
        map.put("bookid",bookidfortxt);
        //发送http请求获取小说文本的json并返回给novel_txt_activity
        //创建一个线程，进行后端验证，成功返回这个用户信息，失败返回null
        new Thread(new Runnable() {
            @Override
            public void run() {
                String request=HttpUtil.sendHttpRequest(URL,"POST",map);
                MyApplication myApplication=(MyApplication)getApplication();
                myApplication.bookdata=request;
//                System.out.println("====bookidfortxt的内容======"+request);
            }}).start();

        //转跳到主页
        Intent intent_Rest3 = new Intent(ForNovelTxtHttpRequest.this, Novel_zhuye.class);
        intent_Rest3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent_Rest3);

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
