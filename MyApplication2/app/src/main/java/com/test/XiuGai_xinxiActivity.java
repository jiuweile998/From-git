    package com.test;

    import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;

import com.test.HttpRequest.XiuGaiHttpReuqest;

    public class XiuGai_xinxiActivity extends Activity {

        public EditText nicheng_xiugai=null;
        public EditText mima_xiugai=null;
        public EditText newmima_xiugai=null;
        public Button queren_xiugai=null;
        public ImageView fanhui_xiugai=null;

        String nicheng=null;
        String mima=null;
        String newmima=null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.xiugaigerenxinxi);

            MyApplication myApplication=(MyApplication) getApplication();//获取全局变量的用户信息

            nicheng_xiugai=(EditText) findViewById(R.id.nicheng_xinxi); //设置用户信息
            mima_xiugai=(EditText)findViewById(R.id.mima_xinxi);
            newmima_xiugai=(EditText)findViewById(R.id.newmima_xinxi);
            queren_xiugai=(Button)findViewById(R.id.queren_xinix) ;
            fanhui_xiugai=(ImageView)findViewById(R.id.fanhui_xiugai);

            ButtonListener buttonListener=new ButtonListener();

            //设置监听
            nicheng_xiugai.setOnClickListener(buttonListener);
            mima_xiugai.setOnClickListener(buttonListener);
            newmima_xiugai.setOnClickListener(buttonListener);
            queren_xiugai.setOnClickListener(buttonListener);
            fanhui_xiugai.setOnClickListener(buttonListener);

            nicheng_xiugai.setText(myApplication.user.getUserName());

        }
        //定义监听类，实现监听器接口
        class ButtonListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch(v.getId()){
                    case R.id.nicheng_xinxi:
//                        nicheng=nicheng_xiugai.getText().toString();
//                        System.out.println("1111111111111111"+nicheng);
                        break;
                    case R.id.mima_xinxi:
//                        mima=mima_xiugai.getText().toString();
//                        System.out.println("------mima----"+mima);
                        break;
                    case R.id.newmima_xinxi:
//                       newmima=newmima_xiugai.getText().toString();
//                        System.out.println("-----newmima------"+newmima);
                        break;
                    case R.id.queren_xinix:
                        nicheng=nicheng_xiugai.getText().toString();
                        mima=mima_xiugai.getText().toString();
                        newmima=newmima_xiugai.getText().toString();
                        MyApplication myApplication=(MyApplication) getApplication();//获取全局变量的用户信息
                        System.out.println("-------全局变量中的密码-----"+myApplication.user.getPassword());
                        System.out.println("用户自己输入的密码："+mima_xiugai.getText().toString());
                        if(myApplication.user.getPassword().equals(mima_xiugai.getText().toString())){
                            Intent intent_Rest3 = new Intent(XiuGai_xinxiActivity.this, XiuGaiHttpReuqest.class);
                            intent_Rest3.putExtra("username_xiugai",nicheng);
                            intent_Rest3.putExtra("newmima_xiugai",newmima);
                            //发送http请求，修改昵称和密码
                            startService(intent_Rest3);
                            //判断server后台服务是否正确修改信息
                            if("success".equals(intent_Rest3.getStringExtra("xiugaixinxi"))){
                               //重新修改界面信息
                                //nicheng_xiugai.setText(myApplication.user.getUserName());
                                System.out.println("----if----------------"+nicheng+newmima);
                                startActivity(new Intent(XiuGai_xinxiActivity.this, XiuGai_xinxiActivity.class));
                                finish();
                            }
                            break;
                            //修改全局变量的昵称-username，和密码-userpassword
                        }else {
                            System.out.println("----else----------------"+nicheng+newmima);
                            Toast.makeText(XiuGai_xinxiActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                            break;
                        }
                    case R.id.fanhui_xiugai:
                        //执行退出功能
                        finish();
                        break;
                    default:break;

                }
            }
        }





    }
