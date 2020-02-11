package com.ilop.sthome.utils.tools;


import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.siterwell.familywellplus.R;

public class Config {
	private static final String TAG = "Config";


	public static int getVerCode(Context context,String packageName) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					packageName, 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verCode;
	}
	
	public static String getVerName(Context context,String packageName) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verName;	

	}
	
	public static String getAppName(Context context) {
		String verName = context.getResources()
		.getText(R.string.app_name).toString();
		return verName;
	}



	public static class UpdateInfo {
		public int id;
		public int code;
		public String version;
        public String url;
		public String url_ex;
        public String en;
        public String zh;
		public String fr;
		public String de;
		public String es;
		public String fi;
		public String nl;
		public String it;
		public String sl;
		public String cs;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getEn() {
			return en;
		}

		public void setEn(String en) {
			this.en = en;
		}

		public String getZh() {
			return zh;
		}

		public void setZh(String zh) {
			this.zh = zh;
		}

		public String getFr() {
			return fr;
		}

		public void setFr(String fr) {
			this.fr = fr;
		}

		public String getDe() {
			return de;
		}

		public void setDe(String de) {
			this.de = de;
		}

		public String getEs() {
			return es;
		}

		public void setEs(String es) {
			this.es = es;
		}

		public String getFi() {
			return fi;
		}

		public void setFi(String fi) {
			this.fi = fi;
		}

		public String getNl() {
			return nl;
		}

		public void setNl(String nl) {
			this.nl = nl;
		}

		public String getIt() {
			return it;
		}

		public void setIt(String it) {
			this.it = it;
		}

		public String getSl() {
			return sl;
		}

		public void setSl(String sl) {
			this.sl = sl;
		}

		public String getCs() {
			return cs;
		}

		public void setCs(String cs) {
			this.cs = cs;
		}

		public String getUrl_ex() {
			return url_ex;
		}

		public void setUrl_ex(String url_ex) {
			this.url_ex = url_ex;
		}
	}
}

