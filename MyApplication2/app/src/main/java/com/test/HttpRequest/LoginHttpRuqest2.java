package com.test.HttpRequest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.BookShelf;
import com.test.BookShelfList;
import com.test.MyApplication;
import com.test.R;
import com.test.Util;
import com.test.ZhuYePage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class LoginHttpRuqest2 extends Service {
    //private String URL="http://10.0.2.2:8080/MyUser/login";
    private String URL="/MyUser/login";
    private String URL2="/MyUser/selectBybookid";
    private String URL3="/MyUser/selectbook";

    //ArrayList<BookShelf> thebookList = new ArrayList<>();

    String phone=null;
    String password=null;

    //更改图片的地方
    ImageView imageView;


    @Override
    public int onStartCommand(Intent intent,int flags,int startId){


        //获取输入的账号和密码
        phone = intent.getStringExtra("phone");
        password = intent.getStringExtra("password");
        System.out.println("-----Login--onstart---"+phone+"--------------"+"--------------"+password);

        Map<String,String> map=new HashMap<>();
        map.put("phone",phone);
        map.put("password",password);

        Map<String,String> map2=new HashMap<>();
        map2.put("userphone",phone);

        MyApplication myApplication = (MyApplication) getApplication();
        //myApplication.bookShelfList.bookShelves=new ArrayList<>();
        myApplication.bookShelfList = new BookShelfList();
        myApplication.bookShelfList.bookShelves=new ArrayList<>();

        //创建一个线程，进行后端验证，成功返回这个用户信息，失败返回null
        new Thread(new Runnable() {
            @Override
            public void run() {
               String request=HttpUtil.sendHttpRequest(URL,"POST",map);
                Handler handler=new Handler(Looper.getMainLooper());
               if(request.isEmpty()){
                   System.out.println("----------request为空"+request);
                   handler.post(new Runnable(){
                       public void run(){
                           Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_LONG).show();
                       }
                   });
               }else{
                   System.out.println("---------request"+request);
                   //提示消息
                   handler.post(new Runnable(){
                       public void run(){
                           Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                       }
                   });
                   ////////////////查询用户的小说记录/////////1、先查出用户对应的bookid 2、再用bookid去查书的所有信息//////////////
                   String request2=HttpUtil.sendHttpRequest(URL2,"POST",map2);
                   //通过userphone查询到用户的书对象集合，再返回bookid这个字段
                   String[] split=Util.getStringToList(request2,",");
                   for(int i=0;i<split.length;i++){
//                       System.out.println("split.length----"+split.length);
                       Map<String,String> map3=new HashMap<>();
                       map3.put("bookid",split[i]);
                       String request3=HttpUtil.sendHttpRequest(URL3,"POST",map3);
                      // Book book = JSONObject.parseObject(request3,Book.class);
//                       System.out.println("======有报错======"+request3);

                       //把一本本小说装载一个list中，用于主页的适配器
                       InputResponseTobookshelf(request3);

                   }
                   ///////////////////////////////////////////////////
                   //登录成功,把获取到的个人信息存储到全局变量中
                   InputResponseToMyApplication(request);
                   //跳转页面
                   //转跳到主页
                   Intent intent_Rest3 = new Intent(LoginHttpRuqest2.this, ZhuYePage.class);
                   intent_Rest3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent_Rest3);
               }
            }}).start();


        //不管登输入账号密码登录成功与否，清除缓存
        intent.removeExtra("password");
        intent.removeExtra("phone");
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
////////////////////登录后，存储用户信息到全局变量
    private void InputResponseToMyApplication(final String response) {
        MyApplication myApplication=(MyApplication)getApplication();


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);

            //把string转化为int型

            //int shelf=Integer.parseInt(jsonObject.getString("userShelf"));
            myApplication.user.setUserName(jsonObject.getString("userName"));
            myApplication.user.setPassword(jsonObject.getString("userPassword"));
            myApplication.user.setUserphone(jsonObject.getString("userPhone"));
           // myApplication.user.setUserShelf(shelf);
            System.out.println("-----InputResponseToMyApplication----"+myApplication.user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
///////////////////////封装对象，给适配器用//////////////
    private void InputResponseTobookshelf(final String response) {
        if(!response.isEmpty()){
            MyApplication myApplication = (MyApplication) getApplication();
            //myApplication.bookShelfList.bookShelves=new ArrayList<>();
//            System.out.println("===kongkong=="+response);
            BookShelf bookShelf=new BookShelf();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);

                //把string转化为int型

                System.out.println("--解析---"+jsonObject);
                int bookid=Integer.parseInt(jsonObject.getString("bookId"));
                bookShelf.bookShelf_author=jsonObject.getString("bookAuthor");
                bookShelf.bookShelf_des=jsonObject.getString("bookDscribe");
                bookShelf.bookShelf_image= R.drawable.fengmina;
                bookShelf.bookShelf_dele=bookid;
                bookShelf.pictureURL=jsonObject.getString("bookPicture");
                System.out.println("---bookshelf----"+bookShelf.toString());
                myApplication.bookShelfList.bookShelves.add(bookShelf);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /////////////////////////从后端服务器获取图片//////////////////////////////
//
//    private void showPicture(String url){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bmp = getURLimage(url);
//                Message msg = new Message();
//                msg.what = 0;
//                msg.obj = bmp;
//                System.out.println("000");
//                handle.sendMessage(msg);
//            }
//        }).start();
//    }
//
//    //在消息队列中实现对控件的更改
//    private Handler handle = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    System.out.println("111");
//                    Bitmap bmp=(Bitmap)msg.obj;
//                    imageView.setImageBitmap(bmp);
//                    break;
//            }
//        };
//    };
//
//    //加载图片
//    public Bitmap getURLimage(String url) {
//        Bitmap bmp = null;
//        try {
//            java.net.URL myurl = new URL(url);
//            // 获得连接
//            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
//            conn.setConnectTimeout(6000);//设置超时
//            conn.setDoInput(true);
//            conn.setUseCaches(false);//不缓存
//            conn.connect();
//            InputStream is = conn.getInputStream();//获得图片的数据流
//            bmp = BitmapFactory.decodeStream(is);//读取图像数据
//            //读取文本数据
//            //byte[] buffer = new byte[100];
//            //inputStream.read(buffer);
//            //text = new String(buffer);
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bmp;
//    }
//
    ////////////////////////////////////////////////



}
