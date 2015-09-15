package hzyj.guangda.student.view;


import com.common.library.llj.base.BaseDialog;

import android.content.Context;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import hzyj.guangda.student.R;
import hzyj.guangda.student.util.ImageLoader;

public class ShowAdvertisementDialog extends BaseDialog {
	public ImageView imgAdvertisement;
	private ImageLoader imgloader;
    public  ImageView imgClose;

	public ShowAdvertisementDialog(Context context) {
		super(context,R.style.dim_dialog);
		imgloader = new ImageLoader(context, R.drawable.erro);
		// TODO Auto-generated constructor stub
	}
	
	public ShowAdvertisementDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		imgloader = new ImageLoader(context, R.drawable.erro);
	}

	public ShowAdvertisementDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
		imgloader = new ImageLoader(context, R.drawable.erro);
	}
	
	public void setImageAdvertisement(String url)
	{
		imgloader.DisplayImage(url, imgAdvertisement);
	}
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_advertisement;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		imgAdvertisement = (ImageView)findViewById(R.id.img_advertisement);
		imgClose = (ImageView)findViewById(R.id.img_close);
		imgClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.CENTER);
		setCanceledOnTouchOutside(false);
	}
	
//	public void setImage(String imgurl,String advertisemetnUrl)
//	{
//		imgloader.DisplayImage(imgurl, imgAdvertisement);
//		imgAdvertisement.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	}
	
//	 public  Bitmap getHttpBitmap(String url){  
//	        URL myFileURL;  
//	        Bitmap bitmap=null;  
//	        try{  
//	            myFileURL = new URL(url);  
//	            //获得连接  
//	            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();  
//	            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制  
//	            conn.setConnectTimeout(6000);  
//	            //连接设置获得数据流  
//	            conn.setDoInput(true);  
//	            //不使用缓存  
//	            conn.setUseCaches(false);  
//	            //这句可有可无，没有影响  
//	            //conn.connect();  
//	            //得到数据流  
//	            InputStream is = conn.getInputStream();  
//	            //解析得到图片  
//	            bitmap = BitmapFactory.decodeStream(is);  
//	            //关闭数据流  
//	            is.close();  
//	        }catch(Exception e){  
//	            e.printStackTrace();  
//	        }  
//	          
//	        return bitmap;  
//	          
//	    }  
}
