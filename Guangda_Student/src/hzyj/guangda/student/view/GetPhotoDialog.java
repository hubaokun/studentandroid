package hzyj.guangda.student.view;

import hzyj.guangda.student.R;

import java.io.File;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.common.library.llj.base.BaseApplication;
import com.common.library.llj.base.BaseDialog;
import com.common.library.llj.utils.FileUtilLj;

public class GetPhotoDialog extends BaseDialog {
	private String mPicPath;
	private int CAMERA_CODE = 1000;
	private int ALBUM_CODE = 1001;
	private int CROP_CODE = 1002;

	public GetPhotoDialog(Activity context) {
		super(context, R.style.dim_dialog);
	}

	public GetPhotoDialog(Activity context, int theme) {
		super(context, theme);
	}

	public GetPhotoDialog(Activity context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.photograph_dialog_layout;
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.CENTER);
	}

	@Override
	protected void findViews() {
		Button takephoto = (Button) findViewById(R.id.btn_take_photo);
		Button selectphoto = (Button) findViewById(R.id.btn_select_photo);
		takephoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 调用系统拍照
				mPicPath = UUID.randomUUID().toString() + "image.jpg";// 通过uuid生成照片唯一名字
				File tempFile = new File(BaseApplication.TEMP_PATH, mPicPath);// 在该路径下构件文件对象
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 系统相机action
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));// 传入图片文件存放位置
				((Activity) mContext).startActivityForResult(intent, CAMERA_CODE);
				dismiss();
			}
		});
		selectphoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 调用系统相册
				Intent intent = new Intent(Intent.ACTION_PICK);// 系统相册action
				intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				((Activity) mContext).startActivityForResult(intent, ALBUM_CODE);
				dismiss();
			}
		});
	}

	/**
	 * 不用剪切的图片处理
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param onGetFileListener
	 * @return
	 */
	public File handleResult(int arg0, int arg1, Intent arg2, OnGetFileListener onGetFileListener) {
		File tempFile = null;
		if (arg0 == CAMERA_CODE && arg1 == Activity.RESULT_OK) {
			// 拍照返回
			tempFile = new File(BaseApplication.TEMP_PATH, mPicPath);
			if (tempFile.exists()) {
				onGetFileListener.AfterGetFile(tempFile);
			}
		} else if (arg0 == ALBUM_CODE && arg1 == Activity.RESULT_OK) {
			// 相册返回,存放在path路径的文件中
			String path = null;
			if (arg2 != null && arg2.getData() != null) {
				// 获得相册中图片的路径
				if ("file".equals(arg2.getData().getScheme())) {
					path = arg2.getData().getPath();
				} else if ("content".equals(arg2.getData().getScheme())) {
					Cursor cursor = mContext.getContentResolver().query(arg2.getData(), null, null, null, null);
					if (cursor.moveToFirst())
						path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
				}
				// 如果路径存在，就复制文件到temp文件夹中
				if (path != null && path.length() > 0) {
					// 通过uuid生成照片唯一名字
					mPicPath = UUID.randomUUID().toString() + "image.jpg";
					tempFile = new File(BaseApplication.TEMP_PATH, mPicPath);
					if (FileUtilLj.copyFile(new File(path), tempFile)) {
						onGetFileListener.AfterGetFile(tempFile);
					}
				}
			}
		}
		return tempFile;
	}

	/**
	 * 调用自定义的界面做剪切的图片处理
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param onGetFileListener
	 * @return
	 */
	public File handleResultByCrop(int arg0, int arg1, Intent arg2, OnGetFileListener onGetFileListener) {
		File tempFile = null;
		// 设置图片
		if (arg0 == CAMERA_CODE && arg1 == Activity.RESULT_OK) {
			// 拍照返回
			tempFile = new File(BaseApplication.TEMP_PATH, mPicPath);
			if (tempFile.exists()) {
				toCropActivity(tempFile);
			}
		} else if (arg0 == ALBUM_CODE && arg1 == Activity.RESULT_OK) {
			// 相册返回,存放在path路径的文件中
			String path = null;
			if (arg2 != null && arg2.getData() != null) {
				// 获得相册中图片的路径
				if ("file".equals(arg2.getData().getScheme())) {
					path = arg2.getData().getPath();
				} else if ("content".equals(arg2.getData().getScheme())) {
					Cursor cursor = mContext.getContentResolver().query(arg2.getData(), null, null, null, null);
					if (cursor.moveToFirst())
						path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
				}
				// 如果路径存在，就复制文件到temp文件夹中
				if (path != null && path.length() > 0) {
					// 通过uuid生成照片唯一名字
					mPicPath = UUID.randomUUID().toString() + "image2.jpg";
					tempFile = new File(BaseApplication.TEMP_PATH, mPicPath);
					if (FileUtilLj.copyFile(new File(path), tempFile)) {
						if (tempFile.exists()) {
							toCropActivity(tempFile);
						}
					}
				}
			}
		} else if (arg0 == CROP_CODE) {
			// 头像裁剪返回
			File outFile = new File(BaseApplication.PIC_PATH + File.separator + "cropheadImage.jpg");
			if (outFile.exists()) {
				onGetFileListener.AfterGetFile(outFile);
			}
		}
		return tempFile;
	}

	/**
	 * 调用系统的界面做剪切的图片处理
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param onGetFileListener
	 * @return
	 */
	public File handleResultBySystemCrop(int arg0, int arg1, Intent arg2, OnGetFileListener onGetFileListener) {
		File tempFile = null;
		// 设置图片
		if (arg0 == CAMERA_CODE && arg1 == Activity.RESULT_OK) {
			// 拍照返回
			tempFile = new File(BaseApplication.TEMP_PATH, mPicPath);
			if (tempFile.exists()) {
				toSystemCrop(Uri.fromFile(tempFile), 300);
			}
		} else if (arg0 == ALBUM_CODE && arg1 == Activity.RESULT_OK) {
			// 相册返回,存放在path路径的文件中
			String path = null;
			if (arg2 != null && arg2.getData() != null) {
				// 获得相册中图片的路径
				if ("file".equals(arg2.getData().getScheme())) {
					path = arg2.getData().getPath();
				} else if ("content".equals(arg2.getData().getScheme())) {
					Cursor cursor = mContext.getContentResolver().query(arg2.getData(), null, null, null, null);
					if (cursor.moveToFirst())
						path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
				}
				// 如果路径存在，就复制文件到temp文件夹中
				if (path != null && path.length() > 0) {
					// 通过uuid生成照片唯一名字
					mPicPath = UUID.randomUUID().toString() + "image2.jpg";
					tempFile = new File(BaseApplication.TEMP_PATH, mPicPath);
					if (FileUtilLj.copyFile(new File(path), tempFile)) {
						if (tempFile.exists()) {
							toSystemCrop(Uri.fromFile(tempFile), 300);
						}
					}
				}
			}
		} else if (arg0 == CROP_CODE && arg1 == Activity.RESULT_OK) {
			// 头像裁剪返回
			File outFile = new File(BaseApplication.PIC_PATH + File.separator + "cropheadImage.jpg");
			if (outFile.exists()) {
				onGetFileListener.AfterGetFile(outFile);
			}
		}
		return tempFile;
	}

	/**
	 * 裁剪图片
	 */
	private void toSystemCrop(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);// 黑边
		intent.putExtra("noFaceDetection", true); // no face detection
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);
		intent.putExtra("output", Uri.fromFile(new File(BaseApplication.PIC_PATH + File.separator + "cropheadImage.jpg"))); // 专入目标文件
		((Activity) mContext).startActivityForResult(intent, CROP_CODE);
	}

	/**
	 * 跳转到自定义的剪切界面
	 * 
	 * @param tempFile
	 */
	private void toCropActivity(File tempFile) {
		// String uuid = UUID.randomUUID().toString();
		// // 通过uuid生成照片唯一名字
		// mPicPath = uuid + "image.jpg";
		// 在该路径下构件文件对象
		File outFile = new File(BaseApplication.PIC_PATH + File.separator + "cropheadImage.jpg");
		// new Crop(Uri.fromFile(tempFile)).output(Uri.fromFile(outFile)).asSquare().start(((Activity) (mContext)), CROP_CODE);
	}

	/**
	 * 
	 * @author liulj
	 * 
	 */
	public interface OnGetFileListener {
		void AfterGetFile(File file);
	}

}
