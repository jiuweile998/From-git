package com.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBaseAdapter extends BaseAdapter {
    private List<Map<String, Object>> mData;
    Context context;
    //构造器
    public MyBaseAdapter(List<Map<String, Object>> mData,Context context) {
        this.mData = mData;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //新建一个类用于获取item中的控件
    public class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }
    ViewHolder viewHolder;
    String url="";
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Map thismessage = mData.get(position);//获取该item所要显示数据

        //不是第一个item的话就重写item
        if (convertView == null) {
            viewHolder = new ViewHolder();
            //获取item.xml文件
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem, null);
            //获取item中的控件
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imgView4444);
            viewHolder.textView=(TextView)convertView.findViewById(R.id.ordertitle);
            url=viewHolder.textView.toString();
            System.out.println("mybaseAdapter================"+url);
            convertView.setTag(viewHolder);
        } else {
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imgView4444);
            viewHolder.textView=(TextView)convertView.findViewById(R.id.ordertitle);
            viewHolder = (ViewHolder) convertView.getTag();
            System.out.println("mybaseAdapter================"+url);
        }
        showPicture(url);

        return null;
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
            switch (msg.what) {
                case 0:
                    System.out.println("111");
                    Bitmap bmp=(Bitmap)msg.obj;
                    viewHolder.imageView.setImageBitmap(bmp);
                    break;
            }
        };
    };

    //加载图片
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            java.net.URL myurl = new URL(url);
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

}
