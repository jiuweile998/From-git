        package com.test;

        import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

        public class WodeActivity extends Activity {

            public Button xiugaixinxi_wode=null;
            public ImageButton quanbufenlei_wode=null;
            public ImageButton shujia_wode=null;
            public Button logout_wode=null;


            public TextView nicheng_wode=null;
            public TextView zhanghao_wode=null;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.wode);

                MyApplication myApplication=(MyApplication) getApplication();//获取全局变量的用户信息

                nicheng_wode=findViewById(R.id.nicheng_wode); //设置用户信息
                nicheng_wode.setText( "昵称 "+myApplication.user.getUserName());
                zhanghao_wode=findViewById(R.id.zhanghao_wode); //设置用户信息
                zhanghao_wode.setText("账号 "+ myApplication.user.getUserphone());


                xiugaixinxi_wode=(Button) findViewById(R.id.xiugaixinxi_wode);
                quanbufenlei_wode=(ImageButton) findViewById(R.id.quanbufenlei_wode);
                shujia_wode=(ImageButton) findViewById(R.id.shujia_wode);
                logout_wode=(Button)findViewById(R.id.logout_wode);

                //生成监听器对象
                ButtonListener buttonListener =new ButtonListener();

                //为控件绑定监听器对象
                xiugaixinxi_wode.setOnClickListener(buttonListener);
                quanbufenlei_wode.setOnClickListener(buttonListener);
                shujia_wode.setOnClickListener(buttonListener);
                logout_wode.setOnClickListener(buttonListener);

            }

            //定义监听类，实现监听器接口
            class ButtonListener implements View.OnClickListener {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    switch(v.getId()){
                        case R.id.quanbufenlei_wode:
                            //跳转到全部分类
                            Intent intent_Rest2 = new Intent(WodeActivity.this, AllBookActivity.class);
                            startActivity(intent_Rest2);
                            finish();
                            break;
                        case R.id.shujia_wode:
                            //跳转到主页
                            Intent intent_Rest = new Intent(WodeActivity.this,ZhuYePage.class);
                            startActivity(intent_Rest);
                            finish();
                            break;
                        case R.id.logout_wode:
                            //退出app
                            Intent intent_Rest5 = new Intent(WodeActivity.this, MainActivity.class);
                            startActivity(intent_Rest5);
                            finish();
                            break;
                        case R.id.xiugaixinxi_wode:
                            //修改个人信息
                            Intent intent_Rest4 = new Intent(WodeActivity.this,XiuGai_xinxiActivity.class);
                            //startActivity(intent_Rest4);
                            startActivityForResult(intent_Rest4,0);
                            //finish();
                            break;
                        case R.id.nicheng_wode:
                            //修改昵称
                            break;
                        case R.id.zhanghao_wode:
                            //修改账号操作
                            break;
                        default:break;



                    }
                    }
                }

               @Override
                public void onResume() {
                    // common code
                    super.onResume();
                    MyApplication myApplication=(MyApplication)getApplication();
                   nicheng_wode.setText( "昵称 "+myApplication.user.getUserName());
                   zhanghao_wode.setText("账号 "+ myApplication.user.getUserphone());
                }



        }
