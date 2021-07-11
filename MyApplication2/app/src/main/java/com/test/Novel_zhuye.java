package com.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.HttpRequest.DeleteOrAddHttpRequest;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Novel_zhuye extends Activity {


    public ImageView imageView_novel;
    public TextView textView_author;
    public TextView textView_dec;

    public ImageButton fanhui_novel_zhu_ye;
    public Button yuedu;
    public Button delete;

    String id=null;
    int uid=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.novel_zhuye);

        MyApplication myApplication =(MyApplication)getApplication(); //全局变量

        myApplication.book_delet_add="delete";//默认按钮是执行删除操作

        String url=myApplication.novel_bookshelf.pictureURL;//myApplication.novel_bookshelf.getPictureURL()
//        System.out.println("novel_zhuye=====url=="+url);
        imageView_novel=(ImageView)findViewById(R.id.imgView_novel_zhuye);
        textView_author=(TextView)findViewById(R.id.author_novel_zhye);
        textView_dec=(TextView)findViewById(R.id.dec_novel_zhye);
        fanhui_novel_zhu_ye=(ImageButton)findViewById(R.id.fanhui_novel_zhu_ye);

        //判断搜索的bookid和 myApplication.bookShelfList.bookShelves集合中的id是否相同，相同

        fanhui_novel_zhu_ye=(ImageButton) findViewById(R.id.fanhui_novel_zhu_ye);
        yuedu=(Button)findViewById(R.id.yuedu_novel_zhu_ye);
        delete=(Button)findViewById(R.id.delet_novel_zhu_ye);

        textView_author.setText(myApplication.novel_bookshelf.bookShelf_author);
        textView_dec.setText(myApplication.novel_bookshelf.bookShelf_des);
         id=Integer.toString(myApplication.novel_bookshelf.bookShelf_dele);
        uid=myApplication.novel_bookshelf.bookShelf_dele;
          //设置要删除的bookid
       //myApplication.delet_bookid=myApplication.novel_bookshelf.bookShelf_dele;
        System.out.println("小说主页myApplication.delet_bookid"+myApplication.novel_bookshelf.bookShelf_dele);
        System.out.println("要删除的书的id是======="+id);
        System.out.println("设置要删除的bookid"+myApplication.delet_bookid);

        //保存可能要添加的图书,默认图片
        myApplication.novel_bookshelf.bookShelf_image=R.drawable.fengmina;
//        myApplication.bookShelf_add.bookShelf_dele=uid; //书id
//        myApplication.bookShelf_add.pictureURL=url;//图片路径
//        myApplication.bookShelf_add.bookShelf_des=null;//书描述
//        myApplication.bookShelf_add.bookShelf_image=0;//默认图片
//        myApplication.bookShelf_add.bookShelf_author=null;//作者

        showPicture(url);

        //生成监听器对象
       ButtonListener buttonListener =new ButtonListener();

        //为控件绑定监听器对象
        fanhui_novel_zhu_ye.setOnClickListener(buttonListener);
        yuedu.setOnClickListener(buttonListener);
        delete.setOnClickListener(buttonListener);
        fanhui_novel_zhu_ye.setOnClickListener(buttonListener);

    }

    //定义监听类，实现监听器接口
    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            switch(v.getId()){
                case R.id.fanhui_novel_zhu_ye:
                    finish();
                    break;
                case R.id.yuedu_novel_zhu_ye:
                    //Intent intent_Rest2 = new Intent(Novel_zhuye.this, ForNovelTxtHttpRequest.class);
                    Intent intent_Rest2 = new Intent(Novel_zhuye.this, novel_txt_activity.class);
                    startActivity(intent_Rest2);
                    finish();
                    //startService(intent_Rest2);
                    break;
                case R.id.delet_novel_zhu_ye:
                    //执行删除操作
                    MyApplication myApplication=(MyApplication)getApplication();
                    //myApplication.book_delet_add="delete";
                    Intent intent_Rest3 = new Intent(Novel_zhuye.this, DeleteOrAddHttpRequest.class);
                    startService(intent_Rest3);
                    finish();
                    break;
                default:break;
            }
        }
    }





////////////////////////////////URL请求相对应的图片//////////////////////////////
private void showPicture(String url){
    new Thread(new Runnable() {
        @Override
        public void run() {
            Bitmap bmp = getURLimage(url);
            Message msg = new Message();
            msg.what = 0;
            msg.obj = bmp;
            System.out.println("000");
            handle.sendMessage(msg);
        }
    }).start();
}

    //在消息队列中实现对控件的更改
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            MyApplication myApplication=(MyApplication)getApplication();
            switch (msg.what) {
                case 0:
//                    System.out.println("111");
                    Bitmap bmp=(Bitmap)msg.obj;
                    imageView_novel.setImageBitmap(bmp);
                    break;
            }
        };
    };

    //加载图片
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);//读取图像数据
            //读取文本数据
            //byte[] buffer = new byte[100];
            //inputStream.read(buffer);
            //text = new String(buffer);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    ///////////////////////////////////////////////////////////


    @Override
    protected void onStart() {
        super.onStart();
        MyApplication myApplication=(MyApplication)getApplication();
        if(myApplication.bookShelfList.bookShelves.isEmpty()){
            //用户刚注册可能为空
            myApplication.book_delet_add="add";
            delete.setText("添加");
           // System.out.println("不为空???=="+myApplication.bookShelfList.bookShelves.toString());
            //System.out.println("1111111==="+myApplication.bookShelfList.bookShelves.get(0));
        }else{
            myApplication.book_delet_add="delete";
            delete.setText("删除");
        }
        if(id!=null){
            //如果myApplication.delet_bookid!=0说明有书要添加或者删除
            //更新适配器
            System.out.println("开始了");
            for(int i = 0 ; i <myApplication.bookShelfList.bookShelves.size(); i++){
                //System.out.println("uid==="+uid);
                System.out.println("========"+myApplication.bookShelfList.bookShelves.get(i).bookShelf_dele);
                if(uid==myApplication.bookShelfList.bookShelves.get(i).bookShelf_dele){  //对比主页中个人的小说图书，没有相同的id
                    System.out.println("===id更改按钮的====="+id);
                    System.out.println("====bookid===="+myApplication.delet_bookid);
                    System.out.println("进入了这里这里");
                    System.out.println("zhelizheli"+myApplication.bookShelfList.bookShelves.isEmpty());
                    System.out.println("zhelizheli "+myApplication.bookShelfList.bookShelves.size());
                    break; //有相同的小说id，说明用户已经添加，直接结束
                }else if(i==(myApplication.bookShelfList.bookShelves.size()-1)){//全局变量中没有搜索到的小说id，并且是最后一个
                    //把删除按钮编程添加按钮
                    delete.setText("添加");
                    //执行添加操作
                    myApplication.book_delet_add="add";
                }
            }

        }

    }
}
