package com.test.HttpRequest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.test.MyApplication;
import com.test.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class searchHttpRequest extends Service {

    String URL="/MyUser/selectbookbyname";

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
       String bookname=intent.getStringExtra("name_search");
        Map<String,String> map=new HashMap<>();
        map.put("name",bookname);
        System.out.println("=======searchHttpRequest=bookname=="+bookname);
        //创建一个线程，进行后端验证，成功返回这个用户信息，失败返回null
        new Thread(new Runnable() {
            @Override
            public void run() {
                //返回值是一个list集合的json串
                String request_search=HttpUtil.sendHttpRequest(URL,"POST",map);
                System.out.println("=======searchHttpRequest==="+request_search);

                if(!request_search.isEmpty()){
                    MyApplication myApplication=(MyApplication)getApplication();
                    myApplication.search_list=Util.replaceString(request_search);
                    System.out.println(" myApplication.search_list====="+ myApplication.search_list);
                }else {
                    MyApplication myApplication=(MyApplication)getApplication();
                    myApplication.search_list=new ArrayList<>();
                }

            }

        }).start();
///////////////////////////广播/////////////////
// TODO Auto-generated method stub
        //发送广播更新UI界面
//        Intent broadcase=new Intent();
//        broadcase.setAction("com.view.test.TestActivity.MyReceiver");
//        Bundle bundle=new Bundle();
//        bundle.putString("message", "0");
//        broadcase.putExtras(bundle);
//        sendBroadcast(broadcase);
        ////////////////////
        //转跳到主页

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
