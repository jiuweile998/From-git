    package com.test;

    import android.app.Activity;
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
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.SimpleAdapter;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.test.HttpRequest.ForNovelTxtHttpRequest;
    import com.test.HttpRequest.all_bookHttpRequest;

    import java.io.InputStream;
    import java.net.HttpURLConnection;
    import java.net.URL;

    import static com.test.MainActivity.myApplication;

    public class AllBookActivity extends Activity {

        public ImageButton shujia_allbook=null;
        public ImageButton wode_allbook=null;

        public EditText search_all_book;
        public Button history_book;
        public Button xuanhuan_book;

        static SimpleAdapter mlistitemAdapter = null;    //该适配器用于将数据源填充到列表视图中
        //用于适配器填充
        private ListView listViewCainpin = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.all_book);

            shujia_allbook=(ImageButton)findViewById(R.id.shujia_allbook);
            wode_allbook=(ImageButton)findViewById(R.id.wode_allbook);
            search_all_book=(EditText)findViewById(R.id.search_all_book);
            history_book=(Button)findViewById(R.id.history_all_book);
            xuanhuan_book=(Button)findViewById(R.id.xuanhuan_all_book);

            //生成监听器对象
            ButtonListener buttonListener =new ButtonListener();

            shujia_allbook.setOnClickListener(buttonListener);
            wode_allbook.setOnClickListener(buttonListener);
            search_all_book.setOnClickListener(buttonListener);
            history_book.setOnClickListener(buttonListener);
            xuanhuan_book.setOnClickListener(buttonListener);

            ///////////////////////适配器////////////////
            listViewCainpin = findViewById(R.id.listview_all_book);

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
                    Intent intent_Rest2 = new Intent(AllBookActivity.this, ForNovelTxtHttpRequest.class);
                    System.out.println("==把书id传递到novel_zhuyeactivity这个类中==="+book_id.getText().toString());
                    //把书id传递到novel_zhuyeactivity这个类中
                    intent_Rest2.putExtra("bookid_fortxt",book_id.getText().toString());
                    startService(intent_Rest2);
                    //finish();
                    System.out.println("可以获取的参数-----"+dishname.getText().toString());
                    //Toast.makeText(ZhuYePage.this,dishname.getText().toString(),Toast.LENGTH_LONG);
//                    ImageButton imageButton=(ImageButton)mView.findViewById(R.id.ordertitle);
//                    System.out.println("0000000000000"+imageButton);
                    //////////////////////////
                }
            });

            /////////////////////////////


        }

        //定义监听类，实现监听器接口
        class ButtonListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch(v.getId()){
                    case R.id.shujia_allbook:
                        //Toast.makeText(AllBookActivity.this,"点击了全部分类",Toast.LENGTH_LONG).show();
                        Intent intent_Rest2 = new Intent(AllBookActivity.this, ZhuYePage.class);
                        startActivity(intent_Rest2);
                        finish();
                        break;
                    case R.id.wode_allbook:
                        Toast.makeText(AllBookActivity.this,"点击了全部分类",Toast.LENGTH_LONG).show();
                        Intent intent_Rest = new Intent(AllBookActivity.this, WodeActivity.class);
                        startActivity(intent_Rest);
                        finish();
                        break;
                    case R.id.search_all_book:
                        Toast.makeText(AllBookActivity.this,"点击了全部分类",Toast.LENGTH_LONG).show();
                        Intent intent_Rest3 = new Intent(AllBookActivity.this, SerarchActivity.class);
                        startActivity(intent_Rest3);
                        //finish();
                        break;
                    case R.id.history_all_book:
                        //处理点击历史按钮
                        Intent intent_Rest4 = new Intent(AllBookActivity.this, all_bookHttpRequest.class);
                        intent_Rest4.putExtra("all_book_name","历史");
                        startService(intent_Rest4);
                       // finish();
                        ////////////////////////////////////
                        MyApplication myApplication=(MyApplication)getApplication();
                        if(!myApplication.all_book_list.isEmpty()){
                            System.out.println("+myApplication.search_list不是空的"+myApplication.all_book_list);
                            //构造适配器，将他和自定义的布局文件listitem 以及数据源mfoodinfo关联起来
                            mlistitemAdapter = new SimpleAdapter(AllBookActivity.this,myApplication.all_book_list,
                                    R.layout.listitem,
                                    //动态数组与ImageItem对应的子项
                                    new String[] {"image","author", "dec", "dele_id","pictureURL"},
                                    //ImageItem的XML文件里面的1个ImageView,3个TextView ID
                                    new int[] {R.id.imgView4444, R.id.title, R.id.orderprice,R.id.ordertitle,R.id.pictureURL});
                            mlistitemAdapter.notifyDataSetChanged();       //菜品数据发生改变，通知适配器刷新数据
                            listViewCainpin.setAdapter(mlistitemAdapter);  //显示菜品的列表采用mlistitemAdapter
                        }else {
                            listViewCainpin.setAdapter(null);
                            System.out.println("searchactivity中的myApplication.search_list是空的");
                        }

                        ///////////////////////////////
                        break;
                    case R.id.xuanhuan_all_book:
                        //处理点击玄幻按钮
                        Intent intent_Rest5 = new Intent(AllBookActivity.this, all_bookHttpRequest.class);
                        intent_Rest5.putExtra("all_book_name","玄幻");
                        startService(intent_Rest5);
                        //finish();
                        ////////////////////////////////////
                        MyApplication myApplication2=(MyApplication)getApplication();
                        if(!myApplication2.all_book_list.isEmpty()){
                            System.out.println("+myApplication.search_list不是空的"+myApplication2.all_book_list);
                            //构造适配器，将他和自定义的布局文件listitem 以及数据源mfoodinfo关联起来
                            mlistitemAdapter = new SimpleAdapter(AllBookActivity.this,myApplication2.all_book_list,
                                    R.layout.listitem,
                                    //动态数组与ImageItem对应的子项
                                    new String[] {"image","author", "dec", "dele_id","pictureURL"},
                                    //ImageItem的XML文件里面的1个ImageView,3个TextView ID
                                    new int[] {R.id.imgView4444, R.id.title, R.id.orderprice,R.id.ordertitle,R.id.pictureURL});
                            mlistitemAdapter.notifyDataSetChanged();       //菜品数据发生改变，通知适配器刷新数据
                            listViewCainpin.setAdapter(mlistitemAdapter);  //显示菜品的列表采用mlistitemAdapter
                        }else {
                            listViewCainpin.setAdapter(null);
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
