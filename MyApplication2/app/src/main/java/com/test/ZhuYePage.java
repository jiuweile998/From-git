    package com.test;

    import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

    import com.test.HttpRequest.ForNovelTxtHttpRequest;

    import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class ZhuYePage extends Activity {

        public ImageButton my=null;
        public ImageButton quanbufenlei=null;
        public EditText serarch=null;

        //更改图片的地方
        //ImageView imageView3;

        ////////////////////适配器//////////////////
        static List<Map<String,Object>> mfoodinfo = new ArrayList<>();//该列表是小说列表的数据源，是一个HashMap表
        private ListView listViewCainpin = null;         //用来显示小说的四个信息的列表
        static SimpleAdapter mlistitemAdapter = null;    //该适配器用于将数据源填充到列表视图中
        //TextView dishname=null;
        //////////////////////////////////////

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.zhuye);
            Toast.makeText(ZhuYePage.this,"登录成功",Toast.LENGTH_LONG).show();

            my=(ImageButton) findViewById(R.id.wode);
            quanbufenlei=(ImageButton) findViewById(R.id.quanbufenlei);
            serarch=(EditText)findViewById(R.id.serarch_zhuye);



            MyApplication myApplication = (MyApplication) getApplication();

            /////////////////////////////////////////////

            //生成监听器对象
           ButtonListener buttonListener =new ButtonListener();

           my.setOnClickListener(buttonListener);
           quanbufenlei.setOnClickListener(buttonListener);
           serarch.setOnClickListener(buttonListener);
   //////////////////////////////////////适配器///////////////
            listViewCainpin = findViewById(R.id.lv1);
            mfoodinfo = getFoodData();                    //得到小说列表数据源
          if(mfoodinfo!=null){
              System.out.println("----mfoodinfo---"+mfoodinfo);
              //构造适配器，将他和自定义的布局文件listitem 以及数据源mfoodinfo关联起来
              mlistitemAdapter = new SimpleAdapter(this,mfoodinfo,
                      R.layout.listitem,
                      //动态数组与ImageItem对应的子项  图片 作者 描述 id  图片地址
                      new String[] {"image","author", "dec", "dele_id","pictureURL"},
                      //ImageItem的XML文件里面的1个ImageView,3个TextView ID
                      new int[] {R.id.imgView4444, R.id.title, R.id.orderprice,R.id.ordertitle,R.id.pictureURL});

              System.out.println("适配器结束--------------------------");
              //  得到要修改图片的地方
//              for(int i=0;i<myApplication.bookShelfList.bookShelves.size();i++){
//                  ////更改图片的按钮
//                  //imageView3 = (ImageView) findViewById(R.id.imgView4444);
//                  System.out.println("----showpicture==================="+myApplication.bookShelfList.bookShelves.get(i).pictureURL);
//                  String url1=myApplication.bookShelfList.bookShelves.get(i).pictureURL;
//
//                  //Drawable drawable = LoadImageFromWebOperations(url1);
//                  //imageView3.setImageDrawable(drawable);
//
//                  //myApplication.imageView_change=imageView3;
//                  //showPicture(url1);
//              }
              /////////////
              //设置listView 选项（item）单击监听器
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


                       //通过URL请求后端服务器的图片
                      showPicture(dishname.getText().toString());
                      Intent intent_Rest2 = new Intent(ZhuYePage.this, ForNovelTxtHttpRequest.class);
                      //把书id传递到novel_zhuyeactivity这个类中
                      intent_Rest2.putExtra("bookid_fortxt",book_id.getText().toString());
                     startService(intent_Rest2);
                  }
              });
              mlistitemAdapter.notifyDataSetChanged();       //菜品数据发生改变，通知适配器刷新数据
              listViewCainpin.setAdapter(mlistitemAdapter);  //显示菜品的列表采用mlistitemAdapter

          }

   /////////////////////////////////////////////////////////////////

        }
        //定义监听类，实现监听器接口
        class ButtonListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.shujia_zhuye:
                        Toast.makeText(ZhuYePage.this,"点击了书架",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.quanbufenlei:
                        Toast.makeText(ZhuYePage.this,"点击了全部分类",Toast.LENGTH_LONG).show();
                        Intent intent_Rest2 = new Intent(ZhuYePage.this, AllBookActivity.class);
                        startActivity(intent_Rest2);
                        break;
                    case R.id.wode:
                        Toast.makeText(ZhuYePage.this,"点击了我的",Toast.LENGTH_LONG).show();
                        Intent intent_Rest = new Intent(ZhuYePage.this,WodeActivity.class);
                        startActivity(intent_Rest);
                        break;
                    case R.id.serarch_zhuye:
                        Intent intent_Rest3 = new Intent(ZhuYePage.this, SerarchActivity.class);
                        startActivity(intent_Rest3);
                        break;

                }
            }
        }

        //每次打开这个页面检查时候删除或添加图书
        @Override
        protected void onStart() {
            super.onStart();
          MyApplication myApplication=(MyApplication)getApplication();
          //得到的是delete参数则执行删除操作，得到add参数，则执行添加操作
          if(myApplication.delet_bookid!=0&&myApplication.book_delet_add.equals("delete")){
              //如果myApplication.delet_bookid!=0说明有书要添加或者删除
              //更新适配器
              System.out.println("开始了");
             for(int i = 0 ; i < mfoodinfo.size() ; i++){
                 System.out.println("========"+mfoodinfo.get(i).get("dele_id"));
                 if(mfoodinfo.get(i).get("dele_id").equals(myApplication.delet_bookid)){//查找要删除的id，相同就删除
                     System.out.println("===mfoodinfo====="+mfoodinfo.get(i).get("dele_id"));
                     System.out.println("====bookid===="+myApplication.delet_bookid);
                     //在数据源中找到相同的id，执行逻辑删除
                     mfoodinfo.remove(i);
                     myApplication.bookShelfList.bookShelves.remove(i);
                     //置0，以免重复执行
                     myApplication.delet_bookid=0;
                     break;
                 }
             }

          }else if(myApplication.delet_bookid!=0&&myApplication.book_delet_add.equals("add")){   //，得到add参数，则执行添加操作
                 System.out.println("执行添加操作");
                 Map<String, Object> map = new HashMap<String, Object>();//用来存放每一个菜品的信息封装成键值对
                 //将菜品的四个信息以键值对的形式存放到map中
                 map.put("image", myApplication.novel_bookshelf.bookShelf_image);
                 map.put("author", myApplication.novel_bookshelf.bookShelf_author);
                 map.put("dec", myApplication.novel_bookshelf.bookShelf_des);
                 map.put("dele_id", myApplication.novel_bookshelf.bookShelf_dele);
                 map.put("pictureURL",myApplication.novel_bookshelf.pictureURL);
              System.out.println("map==++"+map);
                 mfoodinfo.add(map);
              myApplication.bookShelfList.bookShelves.add(myApplication.novel_bookshelf);
              //置0，以免重复操作
              myApplication.delet_bookid=0;
             }

//            System.out.println("每次都会执行？？？");
        }

        ////////////////////////适配器//////////////////////////////
        private ArrayList<Map<String,Object>> getFoodData(){
            ArrayList<Map<String,Object>> foodData = new ArrayList<Map<String,Object>>();


            MyApplication myApplication = (MyApplication)getApplication();

           // int s = dishs.mDishes.size();                  //得到书架的数量

            if(myApplication.bookShelfList==null||myApplication.bookShelfList.bookShelves.isEmpty()){
               return  new ArrayList<>();
            }else {
                int s= myApplication.bookShelfList.bookShelves.size();
                for (int i=0; i<s; i++) {
                    BookShelf bookShelf = myApplication.bookShelfList.bookShelves.get(i);       //得到当前菜品
                    Map<String, Object> map = new HashMap<String, Object>();//用来存放每一个菜品的信息封装成键值对

                    //将菜品的四个信息以键值对的形式存放到map中
                    map.put("image", bookShelf.bookShelf_image);
                    map.put("author", bookShelf.bookShelf_author);
                    map.put("dec", bookShelf.bookShelf_des);
                    map.put("dele_id", bookShelf.bookShelf_dele);
                    map.put("pictureURL",bookShelf.pictureURL);

                    //存有菜品信息的map添加到菜品列表arraylist中
                    foodData.add(map);
                }
                return foodData;
            }
        }

        /////////////////////////从后端服务器获取图片//////////////////////////////

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
//                        System.out.println("111");
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
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmp;
        }


        private Drawable LoadImageFromWebOperations(String url)
        {
            try
            {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            }catch (Exception e) {
                System.out.println("Exc="+e);
                return null;
            }
        }
      ///////////////////////////////////////////////////////////

    }
