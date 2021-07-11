        package com.test.HttpRequest;

        import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.test.MyApplication;
import com.test.ZhuYePage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

        public class ZhuCeHttpRequest extends Service {
            private String URL="/MyUser/insertUser";
            String phone_zhuce=null;
            String nicheng_zhuce=null;
            String password_zhuce=null;
            String passwordage_zhuce=null;


            String canshu2=null;
            String responseData=null;



            @Override
            public int onStartCommand(Intent intent,int flags,int startId) {
                Handler handler = new Handler(Looper.getMainLooper());
                nicheng_zhuce = intent.getStringExtra("nicheng_zhuce");
                phone_zhuce = intent.getStringExtra("phone_zhuce");
                password_zhuce = intent.getStringExtra("password_zhuce");
                passwordage_zhuce = intent.getStringExtra("passwordage_zhuce");
                System.out.println("----zhuceonstart---" + nicheng_zhuce + "------" + phone_zhuce + "-----" + password_zhuce + "-----" + passwordage_zhuce);

                //两次输入密码不相等，则清空所有传参数据
                if (password_zhuce.equals(passwordage_zhuce)) {
                    Map<String,String> map=new HashMap<>();
                    map.put("username",nicheng_zhuce);
                    map.put("userphone",phone_zhuce);
                    map.put("userpassword",password_zhuce);
                   //发送http请求，返回一个对象
                    //创建一个线程，成功返回这个用户信息，失败返回null
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String request=HttpUtil.sendHttpRequest(URL,"POST",map);
                            //处理注册返回结果(存到全局变量中)
                            if(request.isEmpty()){
                                handler.post(new Runnable(){
                                    public void run(){
                                        Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else {
                            System.out.println(request);
                            InputResponseToMyApplication(request);
                                handler.post(new Runnable(){
                                    public void run(){
                                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                                    }
                                });
                                //转跳页面
                                //转跳到主页
                                Intent intent_Rest3 = new Intent(ZhuCeHttpRequest.this, ZhuYePage.class);
                                intent_Rest3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent_Rest3);

                            }

                        }
                    }).start();

                }else {
                    intent.removeExtra("password_zhuce");
                    intent.removeExtra("passwordage_zhuce");
                    //消息提示框
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getApplicationContext(), "两次密码输入不一样", Toast.LENGTH_LONG).show();
                        }
                    });

                }

                stopSelf();
                return super.onStartCommand(intent, flags, startId);
            }

            @Nullable
            @Override
            public IBinder onBind(Intent intent) {
                return null;
            }

            private void InputResponseToMyApplication(final String response) {
                MyApplication myApplication = (MyApplication) getApplication();

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    //把string转化为int型
                    System.out.println("---------input----"+jsonObject);
                   // int shelf=Integer.parseInt(jsonObject.getString("userShelf"));
                    myApplication.user.setUserName(jsonObject.getString("userName"));
                    myApplication.user.setPassword(jsonObject.getString("userPassword"));
                    myApplication.user.setUserphone(jsonObject.getString("userPhone"));
                    //myApplication.user.setUserShelf(shelf);
                    System.out.println("-----InputResponseToMyApplication----"+myApplication.user.toString());
                        //消息提示框
                    Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable(){
                            public void run(){
                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                            }
                        });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
