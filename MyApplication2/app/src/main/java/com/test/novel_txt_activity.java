package com.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class novel_txt_activity extends Activity {

    public TextView bookdata=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novel_txt);

        MyApplication myApplication=(MyApplication)getApplication();

        bookdata=(TextView)findViewById(R.id.novelTxt);
        String  str=myApplication.bookdata;
        str=str.replace("。","。\n    ");

        bookdata.setText(str);
    }
    //定义监听类，实现监听器接口
    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            switch(v.getId()){

            }
        }
    }

}
