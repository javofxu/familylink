
package com.ilop.sthome.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.xmpic.support.FunPath;

import java.io.File;
import java.io.IOException;

/**
 * ClassName:FileUtil
 * 作者：Henry on 2017/3/30 16:26
 * 邮箱：xuejunju_4595@qq.com
 * 描述:设置自动更新下载apk的位置
 */
public class FileUtil {
	
	public static File updateDir = null;
	public static File updateFile = null;

	
	public static boolean isCreateFileSucess;


	public static void createFile(String app_name) {
		
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			isCreateFileSucess = true;
			
			updateDir = new File(FunPath.PATH_DEVICE_UPDATE_FILE_PATH);
			updateFile = new File(updateDir + "/" + app_name);

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					isCreateFileSucess = false;
					e.printStackTrace();
				}
			}

		}else{
			isCreateFileSucess = false;
		}
	}
	public static File uriToFile(Context context, Uri uri) {
		String path = null;
		if ("file".equals(uri.getScheme())) {
			path = uri.getEncodedPath();
			if (path != null) {
				path = Uri.decode(path);
				ContentResolver cr = context.getContentResolver();
				StringBuffer buff = new StringBuffer();
				buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
				Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
				int index = 0;
				int dataIdx = 0;
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
					index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
					index = cur.getInt(index);
					dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					path = cur.getString(dataIdx);
				}
				cur.close();
				if (index == 0) {
				} else {
					Uri u = Uri.parse("content://media/external/images/media/" + index);
					System.out.println("temp uri is :" + u);
				}
			}
			if (path != null) {
				return new File(path);
			}
		} else if ("content".equals(uri.getScheme())) {
			// 4.2.2以后
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
			if (cursor.moveToFirst()) {
				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				path = cursor.getString(columnIndex);
			}
			cursor.close();

			return new File(path);
		} else {
			//Log.i(TAG, "Uri Scheme:" + uri.getScheme());
		}return null;
	}
}