package com.test.HttpRequest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.test.MyApplication;
import com.test.ZhuYePage;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class DeleteOrAddHttpRequest extends Service {

    String URL="/MyUser/";


    @Override
    public int onStartCommand(Intent intent,int flags,int startId){

        MyApplication myApplication=(MyApplication)getApplication();
        String bookid=Integer.toString(myApplication.novel_bookshelf.bookShelf_dele);
        System.out.println("myApplication.delet_bookid"+myApplication.delet_bookid);
        myApplication.delet_bookid=myApplication.novel_bookshelf.bookShelf_dele;
        URL=URL+myApplication.book_delet_add;
        System.out.println("发送请求的id===="+bookid);
        System.out.println(" myApplication.delet_bookid==="+ myApplication.delet_bookid);
        Map<String,String> map=new HashMap<>();
        map.put("bookid",bookid);
         if(myApplication.book_delet_add.equals("add")){
             map.put("bookuserid",myApplication.user.getUserphone());
             System.out.println("bookuserid----->"+myApplication.user.getUserphone());
         }
        //创建一个线程，进行后端删除书或添加书，成功返回这个用户信息，失败返回null
        new  Thread(new Runnable() {
            @Override
            public void run() {
                String request=HttpUtil.sendHttpRequest(URL,"POST",map);
                System.out.println("删除或者添加===="+request);
            }}).start();
        Intent intent_Rest3 = new Intent(DeleteOrAddHttpRequest.this, ZhuYePage.class);
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
