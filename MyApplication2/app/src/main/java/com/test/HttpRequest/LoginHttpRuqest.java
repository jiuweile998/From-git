    package com.test.HttpRequest;

    import android.app.Service;
    import android.content.Intent;
    import android.os.Handler;
    import android.os.IBinder;
    import android.os.Looper;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.test.MyApplication;
    import com.test.ZhuYePage;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.IOException;

    import androidx.annotation.Nullable;
    import okhttp3.OkHttpClient;
    import okhttp3.Request;
    import okhttp3.Response;

    public class LoginHttpRuqest extends Service {

       private String URL="http://10.0.2.2:8080/MyUser/login";
      // private String URL="http://10.136.68.128.:8080/user/all2";
        private String canshu=null;
        String phone=null;
        String password2=null;

        private Button sendRequest;
        TextView responseText;
        //请求返回值
        String  responseData=null;

        @Override
        public int onStartCommand(Intent intent,int flags,int startId){
        phone = intent.getStringExtra("phone");

         password2 = intent.getStringExtra("password");
         System.out.println("-----Login--onstart---"+phone+"--------------"+"--------------"+password2);
            canshu="phone="+phone+"&password="+password2;

          sendRequestWithOkHttp();
            stopSelf();
           intent.removeExtra("password");
           intent.removeExtra("phone");

            return super.onStartCommand(intent, flags, startId);
        }

        private void sendRequestWithOkHttp() {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request=null;
                         if(canshu!=null){
                              request = new Request.Builder().url(URL+"?"+canshu).build();
                                 System.out.println("------Loginhttprquest--------"+URL+"?"+canshu);
                         }else {
                             request = new Request.Builder().url(URL).build();
                         }
                        Response response = client.newCall(request).execute();
                          responseData = response.body().string();
                         //showResponse(responseData);
                        //String result=showResult(responseData);
                        System.out.println("responseData-----"+responseData);
                        //验证返回值是true还是false
                        Handler  handler=new Handler(Looper.getMainLooper());
                        if(responseData.equals("true")){
                            handler.post(new Runnable(){
                                public void run(){
                                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                                }
                            });
                            MyApplication myApplication = (MyApplication) getApplication();
                            myApplication.user.setPassword(password2);
                            myApplication.user.setUserphone(phone);

                            System.out.println("myapplication----------"+phone+"----"+password2);
                            //转跳到主页
                            Intent intent_Rest3 = new Intent(LoginHttpRuqest.this, ZhuYePage.class);
                            intent_Rest3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent_Rest3);
                        }else {
                            handler.post(new Runnable(){
                                public void run(){
                                    Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_LONG).show();
                                }
                            });
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();

        }

        //取出返回数据并验证账号密码
    //    private void showResponse(final String response) {
    //                try{
    //
    //                    JSONObject jsonObject = new JSONObject(response);
    //
    //                         id=jsonObject.getString("id");
    //                         name=jsonObject.getString("name");
    //                         password=jsonObject.getString("passWord");
    //                    System.out.println("----show------"+id+"-----------"+name+"--------"+password);
    //
    //                         if(id!=null&&password.equals(password2)){
    //                             //提示成功消息
    //                             //Toast.makeText(LoginHttpRuqest.this,username+password2+"密码账号没错",Toast.LENGTH_LONG).show();
    //                             Handler  handler=new Handler(Looper.getMainLooper());
    //                             handler.post(new Runnable(){
    //                                 public void run(){
    //                                     Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
    //                                 }
    //                             });
    //                             //转跳到主页
    //                             Intent intent_Rest3 = new Intent(LoginHttpRuqest.this, ZhuYePage.class);
    //                             intent_Rest3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                             startActivity(intent_Rest3);
    //
    //                         }else {
    //                             //提示账号或密码错误消息
    //                             //Toast.makeText(LoginHttpRuqest.this,username+password2+"密码账号出错",Toast.LENGTH_LONG).show();
    //                             Handler  handler=new Handler(Looper.getMainLooper());
    //                             handler.post(new Runnable(){
    //                                 public void run(){
    //                                     Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_LONG).show();
    //                                 }
    //                             });
    //
    //                         }
    //
    //                } catch (JSONException e) {
    //                    e.printStackTrace();
    //                }
    //               // responseText.setText(id+"-----"+name+"-------"+password);
    //
    //            }




        @Nullable
        @Override
        public IBinder onBind(Intent intent) {

            return null;
        }
    }
