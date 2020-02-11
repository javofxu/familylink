package com.ilop.sthome.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.xmpic.support.FunPath;
import com.siterwell.familywellplus.R;

import java.io.File;

/**
 * ImageView创建工厂
 */
public class ViewFactoryUtil {

	public static Bitmap getImageViews(Context mContext, String deviceId) {
		String path = FunPath.getAutoCapturePath(deviceId);
		File file = new File(path);
		Bitmap mBitmap;
		if(file.exists()){
			mBitmap = BitmapFactory.decodeFile(path);
		}else{
			mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.u2, null);
		}
		return mBitmap;
	}
}
