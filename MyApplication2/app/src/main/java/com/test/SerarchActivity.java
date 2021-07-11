package com.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.test.HttpRequest.ForNovelTxtHttpRequest;
import com.test.HttpRequest.searchHttpRequest;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class SerarchActivity extends AppCompatActivity {

    public  EditText serarch_search=null;
   public Button button_sousuo=null;
    String name;

    // 其他控件广播
    //MyReceiver broadcastreciver;

    //用于适配器填充
    private ListView listViewCainpin = null;
    static SimpleAdapter mlistitemAdapter = null;    //该适配器用于将数据源填充到列表视图中

    String url="http://10.0.2.2:8080/static/456.jpg";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.searchview_layout);

            MyApplication myApplication=(MyApplication)getApplication();

            serarch_search=(EditText)findViewById(R.id.serarch_search);
            button_sousuo=(Button)findViewById(R.id.sousuo_search);
            //name=serarch_search.getText().toString();

            //生成监听器对象
           ButtonListener buttonListener =new ButtonListener();
           serarch_search.setOnClickListener(buttonListener);
           button_sousuo.setOnClickListener(buttonListener);


           ///////////////////////适配器////////////////
            listViewCainpin = findViewById(R.id.listview_search);


            listViewCainpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,  //item所属的列表listViewCaipin
                                        View view,              //被选中的控件，就是被选中的某一个菜品item
                                        int position,           //被选中的某一个菜品item在ListView中的位置
                                        long id) {              //被选中的某一个菜品item的行号
                    ListView tempList = (ListView) parent;
                    View mView  = tempList.getChildAt(position);


                    ImageView image=(ImageView)mView.findViewById(R.id.imgView4444);
                    //System.out.println("========================="+image);

                    myApplication.imageView_change=image;
                    TextView dishname = (TextView)mView.findViewById(R.id.pictureURL);
                    TextView author=(TextView)mView.findViewById(R.id.title);
                    TextView dec_novel=(TextView)mView.findViewById(R.id.orderprice);
                    TextView book_id=(TextView)mView.findViewById(R.id.ordertitle);
                    //创建一个bookshelf对象，当点击到对应的图书时，传递过去
                    //传递具体点击了那个小说的参数
                    myApplication.novel_bookshelf=new BookShelf();
                    myApplication.novel_bookshelf.setPictureURL(dishname.getText().toString());
                    myApplication.novel_bookshelf.setBookShelf_author(author.getText().toString());
                    myApplication.novel_bookshelf.setBookShelf_des(dec_novel.getText().toString());
                    int bookid=Integer.parseInt(book_id.getText().toString());
                    myApplication.novel_bookshelf.setBookShelf_dele(bookid);
                    System.out.println("====作者===getBookShelf_author==="+ myApplication.novel_bookshelf.getBookShelf_author());
                    System.out.println("===描述====getBookShelf_des==="+ myApplication.novel_bookshelf.getBookShelf_des());
                    System.out.println("===图片路径====getPictureURL==="+ myApplication.novel_bookshelf.getPictureURL());
                    System.out.println("===id====getBookShelf_dele==="+ myApplication.novel_bookshelf.getBookShelf_dele());



                    showPicture(dishname.getText().toString());
                    System.out.println("=======dishname.getText().toString()======="+dishname.getText().toString());
                    Intent intent_Rest2 = new Intent(SerarchActivity.this, ForNovelTxtHttpRequest.class);
                    System.out.println("==把书id传递到novel_zhuyeactivity这个类中==="+book_id.getText().toString());
                    //把书id传递到novel_zhuyeactivity这个类中
                    intent_Rest2.putExtra("bookid_fortxt",book_id.getText().toString());
                    startService(intent_Rest2);
                    System.out.println("可以获取的参数-----"+dishname.getText().toString());
                    finish();

                }
            });

            /////////////////////////////

            //////////////////////////////////////////
        }

    //定义监听类，实现监听器接口
    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            switch(v.getId()){
                case R.id.sousuo_search:  //点击搜索按钮，开始搜索
                    name=serarch_search.getText().toString();
                    //开启http请求，返回数据，用适配器填充
                    Intent intent_Rest2 = new Intent(SerarchActivity.this, searchHttpRequest.class);
                    //把书id传递到novel_zhuyeactivity这个类中
                    intent_Rest2.putExtra("name_search",name);
                    startService(intent_Rest2);
                    //System.out.println("service服务结束后还执行？=========");
                    ////////////////////////////////////
                    MyApplication myApplication=(MyApplication)getApplication();
                    if(!myApplication.search_list.isEmpty()){
                        System.out.println("+myApplication.search_list不是空的"+myApplication.search_list);
                        //构造适配器，将他和自定义的布局文件listitem 以及数据源mfoodinfo关联起来
                        mlistitemAdapter = new SimpleAdapter(SerarchActivity.this,myApplication.search_list,
                                R.layout.listitem,
                                //动态数组与ImageItem对应的子项
                                new String[] {"image","author", "dec", "dele_id","pictureURL"},
                                //ImageItem的XML文件里面的1个ImageView,3个TextView ID
                                new int[] {R.id.imgView4444, R.id.title, R.id.orderprice,R.id.ordertitle,R.id.pictureURL});
                        mlistitemAdapter.notifyDataSetChanged();       //菜品数据发生改变，通知适配器刷新数据
                        listViewCainpin.setAdapter(mlistitemAdapter);  //显示菜品的列表采用mlistitemAdapter
                    }else {
                        listViewCainpin.setAdapter(null);//搜索结果返回空，不显示任何东西
                        System.out.println("searchactivity中的myApplication.search_list是空的");
                    }

                    ///////////////////////////////
                    break;
            }
        }
    }


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
                    System.out.println("111");
                    Bitmap bmp=(Bitmap)msg.obj;
                    myApplication.imageView_change.setImageBitmap(bmp);
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

    /////////////////////////////////////////////////

}
