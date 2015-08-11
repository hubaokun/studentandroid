package hzyj.guangda.student.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImgUtil {
    private static final String TAG = "LoadImageUtil";
    private static ImgUtil instance;
    private static HashMap<String, SoftReference<Bitmap>> imgCaches;
    private static ExecutorService executorThreadPool = Executors
            .newFixedThreadPool(1);
    static {
        instance = new ImgUtil();
        imgCaches = new HashMap<String, SoftReference<Bitmap>>();
    }

    public static ImgUtil getInstance() {
        if (instance != null) {
            return instance;
        }
        return null;
    }

    public void loadBitmap(final String path,
                           final OnLoadBitmapListener listener) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bitmap bitmap = (Bitmap) msg.obj;
                listener.loadImage(bitmap, path);
            }
        };
        new Thread() {

            @Override
            public void run() {
                executorThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = loadBitmapFromCache(path);
                        if (bitmap != null) {
                            Message msg = handler.obtainMessage();
                            msg.obj = bitmap;
                            handler.sendMessage(msg);
                        }

                    }
                });
            }

        }.start();
    }

    private Bitmap loadBitmapFromCache(String path) {
        if (imgCaches == null) {
            imgCaches = new HashMap<String, SoftReference<Bitmap>>();
        }
        Bitmap bitmap = null;
        if (imgCaches.containsKey(path)) {
            bitmap = imgCaches.get(path).get();
        }
        if (bitmap == null) {
            bitmap = loadBitmapFromLocal(path);
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromLocal(String path) {
        if (path == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        float height = 800f;
        float width = 480f;
        float scale = 1;
        if (options.outWidth > width && options.outWidth > options.outHeight) {
            scale = options.outWidth / width;
        } else if (options.outHeight > height
                && options.outHeight > options.outWidth) {
            scale = options.outHeight / height;
        } else {
            scale = 1;
        }
        options.inSampleSize = (int) scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        bitmap = decodeBitmap(bitmap);
        if (!imgCaches.containsKey(path)) {
            //imgCaches.put(path, new SoftReference<Bitmap>(bitmap));
            addCache(path, bitmap);
        }
        return bitmap;
    }

    private Bitmap decodeBitmap(Bitmap bitmap) {
        int scale = 100;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, scale, bos);
        while ((bos.toByteArray().length / 1024) > 30) {
            bos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, scale, bos);
            scale -= 10;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        bitmap = BitmapFactory.decodeStream(bis);
        return bitmap;
    }

    public void addCache(String path,Bitmap bitmap){
        imgCaches.put(path, new SoftReference<Bitmap>(bitmap));
    }

    public void reomoveCache(String path){
        imgCaches.remove(path);
    }

    public interface OnLoadBitmapListener {
        void loadImage(Bitmap bitmap, String path);
    }


    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;//图片大小，设置越大，图片越不清晰，占用空间越小
            return BitmapFactory.decodeStream(fis, null, options);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取网落图片资源
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            if (conn.getDoInput() != true) {
                conn.setDoInput(true);
            }
            //不使用缓存
            /*conn.setUseCaches(false);*/
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}


