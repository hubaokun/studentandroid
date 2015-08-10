package com.common.library.llj.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DecimalFormat;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

/**
 * 文件处理工具
 * 
 * @author liulj
 * 
 */
public class FileUtilLj {
	/**
	 * 1.从指定文件夹下查找含有关键字的文件
	 * 
	 * @param keyword
	 *            文件中的关键字
	 * @param filepath
	 *            指定文件夹
	 * @return 文件的全路径，带有文件名
	 */
	public static final String searchFile(String keyword, File filepath) {
		File[] files = filepath.listFiles();

		if (files.length > 0) {
			for (File file : files) {
				if (file.isDirectory()) {
					// 如果目录可读就执行（一定要加，不然会挂掉）
					if (file.canRead()) {
						searchFile(keyword, file); // 如果是目录，递归查找
					}
				} else {
					// 判断是文件，则进行文件名判断
					try {
						// if (file.getName().indexOf(keyword) > -1 || file.getName().indexOf(keyword.toUpperCase()) > -1) {
						// return file.getPath();
						// }
						if (file.getName().split("[.]")[0].equals(keyword)) {
							return file.getPath();
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return null;
	}

	/**
	 * 2.取得某个文件夹或者文件的bytes大小
	 * 
	 * @param file
	 *            文件或者文件夹
	 * @return 文件或者文件夹大小
	 */
	public static final long getFileSize(File file) {
		long size = 0;
		if (file.exists()) {
			for (File subFile : file.listFiles()) {
				if (subFile.isDirectory()) {
					size += getFileSize(subFile);
				} else {
					// 文件的bytes大小累加
					size += subFile.length();
				}
			}
		}
		return size;
	}

	/**
	 * 3.将个文件夹或者文件的bytes大小转换成对应格式
	 * 
	 * @param fileLength
	 *            文件或者文件夹大小
	 * @return 对应格式的字符串
	 */
	public static final String formatFileSize(long fileLength) {
		DecimalFormat df = new DecimalFormat("##.00");
		String fileSizeString = "";
		if (fileLength < 1024) {
			fileSizeString = df.format((double) fileLength) + "B";
		} else if (fileLength < 1048576) {
			fileSizeString = df.format((double) fileLength / 1024) + "K";
		} else if (fileLength < 1073741824) {
			fileSizeString = df.format((double) fileLength / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileLength / 1073741824) + "G";
		}
		if (fileSizeString.equals(".00B")) {
			fileSizeString = "0" + fileSizeString;
		}
		return fileSizeString;
	}

	/**
	 * 4.删除文件
	 * 
	 * @param file
	 *            将要删除的文件或者文件
	 */
	public static final void cleanCache(File file) {
		if (file != null && file.exists()) {
			for (File subFile : file.listFiles()) {
				if (!subFile.isDirectory()) {
					// 如果不是目录是文件则删除
					subFile.delete();
				}
			}
		}
	}

	/**
	 * 5. 保存字节流至文件
	 * 
	 * @param bytes
	 *            字节流
	 * @param file
	 *            目标文件
	 */
	public static final boolean saveBytesToFile(byte[] bytes, File file) {
		if (bytes == null) {
			return false;
		}
		ByteArrayInputStream bais = null;
		BufferedOutputStream bos = null;
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();

			bais = new ByteArrayInputStream(bytes);
			bos = new BufferedOutputStream(new FileOutputStream(file));

			int size;
			byte[] temp = new byte[1024];
			while ((size = bais.read(temp, 0, temp.length)) != -1) {
				bos.write(temp, 0, size);
			}

			bos.flush();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bos = null;
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bais = null;
			}
		}
		return false;
	}

	/**
	 * 6.保存位图至图片文件
	 * 
	 * @param bitmap
	 * @param file
	 * @return
	 */
	public static final boolean saveBitmapToFile(Bitmap bitmap, File file) {
		BufferedOutputStream bos = null;
		try {
			if (file != null) {
				file.getParentFile().mkdirs();
				bos = new BufferedOutputStream(new FileOutputStream(file));
				bitmap.compress(CompressFormat.JPEG, 80, bos);

				bos.flush();
				bos.close();
				bos = null;
				if (file.exists() && file.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bos = null;
			}
		}
		return false;
	}

	/**
	 * 7.复制文件
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 */
	public static final boolean copyFile(File srcFile, File destFile) {
		if (!srcFile.exists()) {
			return false;
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			destFile.getParentFile().mkdirs();
			destFile.createNewFile();

			bis = new BufferedInputStream(new FileInputStream(srcFile));
			bos = new BufferedOutputStream(new FileOutputStream(destFile));

			int size;
			byte[] temp = new byte[1024];
			while ((size = bis.read(temp, 0, temp.length)) != -1) {
				bos.write(temp, 0, size);
			}

			bos.flush();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bos = null;
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bis = null;
			}
		}
		return false;
	}

	/**
	 * 8.MD5加密
	 * 
	 * @param 需要加密的String
	 * @return 加密后String
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}

			return new String(str).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据文件路径获得全文件名
	 * 
	 * @param path
	 *            文件路径
	 */
	public static final String getFileFullNameByPath(String path) {
		String name = null;
		if (path != null) {
			int start = path.lastIndexOf("/");
			// 截取最后一个/后面的字符串
			name = path.substring(start == -1 ? 0 : start + 1);
		}
		return name;
	}

	/**
	 * 根据文件路径获得后缀名
	 * 
	 * @param path
	 *            文件路径
	 */
	public static final String getFileTypeByPath(String path) {
		String type = null;
		if (path != null) {
			int start = path.lastIndexOf(".");
			if (start != -1) {
				// 截取.后面的字符串
				type = path.substring(start + 1);
			}
		}
		return type;
	}

	/**
	 * 根据文件路径获得文件名
	 * 
	 * @param path
	 *            文件路径
	 */
	public static final String getFileNameByPath(String path) {
		String name = null;
		if (path != null) {
			int start = path.lastIndexOf("/");
			int end = path.lastIndexOf(".");
			// 截取最后一个/到最后一个.之间的字符串
			name = path.substring(start == -1 ? 0 : start + 1, end == -1 ? path.length() : end);
		}
		return name;
	}

	/**
	 * 根据URL获得全文件名
	 * 
	 * @param url
	 *            URL
	 */
	public static final String getFileFullNameByUrl(String url) {
		String name = null;
		if (url != null) {
			int start = url.lastIndexOf("/");
			int end = url.lastIndexOf("?");
			// 截取url中最后一个/到最后一个？之间的字符串
			name = url.substring(start == -1 ? 0 : start + 1, end == -1 ? url.length() : end);
		}
		return name;
	}

	/**
	 * 根据URL获得后缀名
	 * 
	 * @param url
	 *            URL
	 */
	public static final String getFileTypeByUrl(String url) {
		String type = null;
		if (url != null) {
			int start = url.lastIndexOf(".");
			int end = url.lastIndexOf("?");
			if (start != -1) {
				// 截取最后一个.到？的
				type = url.substring(start + 1, end == -1 ? url.length() : end);
			}
		}
		return type;
	}

	/**
	 * 根据URL获得文件名
	 * 
	 * @param url
	 *            URL
	 */
	public static final String getFileNameByUrl(String url) {
		String name = null;
		if (url != null) {
			int start = url.lastIndexOf("/");
			int end = url.lastIndexOf(".");
			int end2 = url.lastIndexOf("?");
			// 没有.就截取/到？的 有就截取/到.的
			name = url.substring(start == -1 ? 0 : start + 1, end == -1 ? (end2 == -1 ? url.length() : end2) : end);
		}
		return name;
	}

}
