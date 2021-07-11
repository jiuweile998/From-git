package com.test.HttpRequest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.test.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class XiuGaiHttpReuqest extends Service {
   String URL="/MyUser/updataUser";

    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        MyApplication myApplication=(MyApplication)getApplication();

        Handler handler = new Handler(Looper.getMainLooper());
        Map<String,String> map=new HashMap<>();
        map.put("username",intent.getStringExtra("username_xiugai"));
        map.put("userphone",myApplication.user.getUserphone());
        map.put("userpassword",intent.getStringExtra("newmima_xiugai"));
        System.out.println("--------XiuGaiHttpReuqest-------"+intent.getStringExtra("username_xiugai")+intent.getStringExtra("newmima_xiugai"));
       // String request=HttpUtil.sendHttpRequest(URL,"POST",map);
        //创建一个线程，进行后端修改，成功返回这个用户信息，失败返回null
        new Thread(new Runnable() {
            @Override
            public void run() {
                String request=HttpUtil.sendHttpRequest(URL,"POST",map);
                Handler handler=new Handler(Looper.getMainLooper());
                if(request.isEmpty()){
                    //提示修改失败
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getApplicationContext(), "修改信息失败", Toast.LENGTH_LONG).show();
                        }
                    });
                    //设置失败信息
                    intent.putExtra("xiugaixinxi","false");
                }else {
                    //提示修改成功
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getApplicationContext(), "修改信息成功", Toast.LENGTH_LONG).show();
                        }
                    });
                    //设置失败信息
                    intent.putExtra("xiugaixinxi","success");
                    //更新修改信息，包括当前页面以及别的页面

                    //修改全局变量信息
                    InputResponseToMyApplication(request);
                }
            }}).start();
        ///////////////////////////////////////////
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    private void InputResponseToMyApplication(final String response) {
        MyApplication myApplication = (MyApplication) getApplication();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            //把string转化为int型
            //int shelf=Integer.parseInt(jsonObject.getString("userShelf"));
            myApplication.user.setUserName(jsonObject.getString("userName"));
            myApplication.user.setPassword(jsonObject.getString("userPassword"));
            myApplication.user.setUserphone(jsonObject.getString("userPhone"));
            //myApplication.user.setUserShelf(shelf);
            System.out.println("-----InputResponseToMyApplication----"+myApplication.user.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
