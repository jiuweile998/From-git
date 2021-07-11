package com.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.HttpRequest.ZhuCeHttpRequest;

public class ZhuCeActivity extends Activity {
    public Button zhuce=null;
    public Button log_out=null;

    public EditText Nicheng_page=null;
    public EditText Phone_page=null;
    public EditText Password_page=null;
    public EditText PasswordAge_page=null;

    String nicheng=null;
    String phone=null;
    String passWord=null;
    String passwordage=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce);

        log_out = (Button)findViewById(R.id.log_out);
        zhuce=(Button)findViewById(R.id.zhuce);

        Nicheng_page =(EditText) findViewById(R.id.nicheng);
        Phone_page =(EditText) findViewById(R.id.phone);
        Password_page =(EditText) findViewById(R.id.password);
        PasswordAge_page =(EditText) findViewById(R.id.passwordAge);

        //生成监听器对象
        ButtonListener2 buttonListener2 =new ButtonListener2();

        //为控件绑定监听器对象
        log_out.setOnClickListener(buttonListener2);
        zhuce.setOnClickListener(buttonListener2);

    }
    //定义监听类，实现监听器接口
    class ButtonListener2 implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.phone:
                    //电话
//                    phone=Phone_page.getText().toString();
//                    Toast.makeText(ZhuCeActivity.this,phone,Toast.LENGTH_LONG).show();
                    break;
                case R.id.nicheng:
                    //昵称
//                    nicheng=Nicheng_page.getText().toString();
//                    Toast.makeText(ZhuCeActivity.this,nicheng,Toast.LENGTH_LONG).show();
                    break;
                case R.id.password:
                    //密码
//                    passWord=Password_page.getText().toString();
//                    Toast.makeText(ZhuCeActivity.this,passWord,Toast.LENGTH_LONG).show();
                    break;
                case R.id.password2:
                    //再次输入密码
//                    passwordage=PasswordAge_page.getText().toString();
//                    Toast.makeText(ZhuCeActivity.this,passwordage,Toast.LENGTH_LONG).show();
                    break;
                case R.id.log_out:
                    //跳转到注册分页界面
                    Intent intent_Rest = new Intent(ZhuCeActivity.this,MainActivity.class);
                    startActivity(intent_Rest);
                    finish();
                    break;
                case R.id.zhuce:
                    //注册成功，跳转到主页
                    nicheng=Nicheng_page.getText().toString();
                    phone=Phone_page.getText().toString();
                    passWord=Password_page.getText().toString();
                    passwordage=PasswordAge_page.getText().toString();
                    System.out.println("nicheng："+nicheng+phone+passWord+passwordage);
                    Intent intent_Rest3 = new Intent(ZhuCeActivity.this, ZhuCeHttpRequest.class);
                    //两个activity之间传递参数
                    intent_Rest3.putExtra("nicheng_zhuce",nicheng);
                    intent_Rest3.putExtra("phone_zhuce",phone);
                    intent_Rest3.putExtra("password_zhuce",passWord);
                    intent_Rest3.putExtra("passwordage_zhuce",passwordage);
                    Toast.makeText(ZhuCeActivity.this,nicheng+passWord+passwordage,Toast.LENGTH_LONG).show();
                    startService(intent_Rest3);
                    finish();
                    break;
                default:break;
            }
        }
    }

}
