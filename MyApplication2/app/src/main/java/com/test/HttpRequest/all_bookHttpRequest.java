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

public class all_bookHttpRequest extends Service {
    String URL="/MyUser/bookkind";

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        String bookname=intent.getStringExtra("all_book_name");
        Map<String,String> map=new HashMap<>();
        map.put("bookkind_name",bookname);
        System.out.println("=======searchHttpRequest=bookname=="+bookname);
        //创建一个线程，进行后端验证，成功返回这个用户信息，失败返回null
        new Thread(new Runnable() {
            @Override
            public void run() {
                //返回值是一个list集合的json串
                String request=HttpUtil.sendHttpRequest(URL,"POST",map);
                System.out.println("=====bookkind=="+request);


                if(!request.isEmpty()){
                    //String[] split=Util.getStringToList(request_search,"\\}");
                    MyApplication myApplication=(MyApplication)getApplication();
                    //ArrayList<BookShelf> theDishesList= Util.replaceString(request_search);
                    myApplication.all_book_list= Util.replaceString(request);
                    System.out.println(" myApplication.search_list====="+ myApplication.all_book_list);
                }else {
                    MyApplication myApplication=(MyApplication)getApplication();
                    myApplication.all_book_list=new ArrayList<>();
                    System.out.println("空空如也==="+request);
                }

            }
        }).start();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
