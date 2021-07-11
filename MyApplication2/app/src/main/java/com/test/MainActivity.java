    package com.test;

    import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.HttpRequest.LoginHttpRuqest2;

import androidx.appcompat.app.AppCompatActivity;

    public class MainActivity extends AppCompatActivity {
        static MyApplication myApplication = null;  //全局变量

        public Button btn_login=null;
        public TextView meiyouzhanghao=null;
        public EditText etc_user_name=null;
        public EditText etc_psw=null;

        private String phone=null;
        private String password=null;

        //更改图片的地方
        ImageView imageView1;

        // private TextView textView;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            myApplication = (MyApplication)getApplication();    //获得全局变量对象
            myApplication.user=new User();
            myApplication.book=new Book();


            etc_user_name =(EditText) findViewById(R.id.et_user_name);
            etc_psw=(EditText) findViewById(R.id.et_psw);

            //获取输入框中的内容
    //        userName=etc_user_name.getText().toString();
    //        password=etc_psw.getText().toString();

            meiyouzhanghao=(TextView)findViewById(R.id.meiyouzhanghao);
            //获取代表控件的对象
            btn_login = (Button) findViewById(R.id.btn_login);

            //生成监听器对象
            ButtonListener buttonListener =new ButtonListener();

            //为控件绑定监听器对象
            btn_login.setOnClickListener(buttonListener);
            etc_user_name.setOnClickListener(buttonListener);
            etc_psw.setOnClickListener(buttonListener);
            meiyouzhanghao.setOnClickListener(buttonListener);

            //////////////////////////////////////////////////

            ////更改图片的按钮
            imageView1 = (ImageView) findViewById(R.id.imgView4444);
            //showPicture(String url);



        }
        //定义监听类，实现监听器接口
        class ButtonListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch(v.getId()){
                    case R.id.et_user_name:
                        //phone=etc_user_name.getText().toString();
                        //Toast.makeText(MainActivity.this,phone,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.et_psw:
                        //password=etc_psw.getText().toString();
                       // Toast.makeText(MainActivity.this,password,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.meiyouzhanghao:
                        //跳转到注册分页界面
                        Intent intent_Rest = new Intent(MainActivity.this,ZhuCeActivity.class);
                        startActivity(intent_Rest);
                        //finish();
                        break;
                    case R.id.btn_login:
                        phone=etc_user_name.getText().toString().trim();
                        password=etc_psw.getText().toString().trim();
                        //Toast.makeText(MainActivity.this,userName+password,Toast.LENGTH_LONG).show();
                        Intent intent_Rest2 = new Intent(MainActivity.this, LoginHttpRuqest2.class);
                        //两个activity之间传递参数
                        intent_Rest2.putExtra("phone",phone);
                        intent_Rest2.putExtra("id","id");
                        intent_Rest2.putExtra("password",password);
                        intent_Rest2.putExtra("mima","password");
//                        System.out.println("----main------"+phone+"--------------"+password);
                        //startActivity(intent_Rest2);
                        startService(intent_Rest2);
                        //finish();
                        break;
                    default:break;
                }
            }

        }



    }