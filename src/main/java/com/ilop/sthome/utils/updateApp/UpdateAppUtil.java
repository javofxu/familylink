package com.ilop.sthome.utils.updateApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;

import com.example.common.base.OnCallBackToRefresh;
import com.ilop.sthome.app.MyApplication;
import com.ilop.sthome.service.UpdateService;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.utils.tools.CacheUtil;
import com.ilop.sthome.utils.tools.Config;
import com.ilop.sthome.utils.tools.SiterSDK;
import com.ilop.sthome.utils.tools.UnitTools;
import com.ilop.sthome.view.SettingItem;
import com.siterwell.familywellplus.BuildConfig;
import com.siterwell.familywellplus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.conn.scheme.Scheme;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.X509HostnameVerifier;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * ClassName:UpdateAppUtil
 * 作者：Henry on 2017/3/30 13:52
 * 邮箱：xuejunju_4595@qq.com
 * 描述:自动更新，HTT访问服务器地址文件中的版本号大小，与APP的版本号进行比较，若大于本地版本则弹出对话框
 */
public class UpdateAppUtil {
    private final String TAG = "UpdateAppUtil";
    private Context context;
    private Handler handlerUpdate;
    private final static int DOWN_UPDATE = 11;
    private int progress = 0;
    private static boolean flag_checkupdate = false;
    private int count=0;
    private SettingItem updateSetitem;
    private boolean isFlag_checkupdate;
    private ProgressDialog pBar;

    public UpdateAppUtil(Context context) {
        Log.i(TAG,"UpdateAppUtil create");
        this.context = context;
        handlerUpdate = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        getUpdateInfo();
                        break;
                    case 3:
                        confirm((Config.UpdateInfo) msg.obj);
                        break;
                    case DOWN_UPDATE:
                        pBar.setProgress(progress);
                        break;
                }
            }
        };
    }


    public UpdateAppUtil(Context context, SettingItem updateSetitem2, boolean flag_checkupdate) {
        Log.i(TAG,"UpdateAppUtil create");
        this.context = context;
        this.updateSetitem = updateSetitem2;
        this.isFlag_checkupdate = flag_checkupdate;
        handlerUpdate = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        getUpdateInfo();
                        break;
                    case 3:
                            updateSetitem.setNewUpdateVisibility(true);
                            updateSetitem.setTag(msg.obj);
                        break;
                    case DOWN_UPDATE:
                        pBar.setProgress(progress);
                        break;
                }
            }
        };

        if(isFlag_checkupdate){
            updateSetitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                            if(updateSetitem.getNewUpdateVisibility()==View.VISIBLE && !UpdateService.flag_updating){


                                Config.UpdateInfo info = (Config.UpdateInfo)v.getTag();
                                confirm(info);
                            }


                }
            });
        }

    }


    private void confirm(final  Config.UpdateInfo info) {
        String appname =  context.getPackageName();
        Log.i(TAG,"appname:"+appname);

        UnitTools unitTools = new UnitTools(context);
        String lang = unitTools.readLanguage();
        String ds = "";
        if("zh".equals(lang)){
            ds = info.zh;
        }else if("de".equals(lang)){
            ds = info.de;
        }else if("fr".equals(lang)){
            ds = info.fr;
        }else if("es".equals(lang)){
            ds = info.es;
        }else if("fi".equals(lang)){
            ds = info.fi;
        }else if("it".equals(lang)){
            ds = info.it;
        }else if("nl".equals(lang)){
            ds = info.nl;
        }else if("cs".equals(lang)){
            ds = info.cs;
        }else if("sl".equals(lang)){
            ds = info.sl;
        }else {
            ds = info.en;
        }
        try {
            BaseDialog mDialog = new BaseDialog(context, new OnCallBackToRefresh() {
                @Override
                public void onConfirm() {
                    if(!"CHINA".equalsIgnoreCase(BuildConfig.BUILD_COUNTRY)){
                        String url = info.url;
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        context.startActivity(intent);
                    }else{
                        pBar = new ProgressDialog(context);
                        pBar.setCanceledOnTouchOutside(false);
                        pBar.setTitle(context.getResources().getString(R.string.downing));
                        pBar.setMessage(context.getResources().getString(R.string.wait));
                        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        downFile(info.url);
                    }
                }

                @Override
                public void onCancel() {

                }
            });
            mDialog.setTitleAndButton(ds, context.getResources().getString(R.string.cancel), context.getResources().getString(R.string.update_alert));
            mDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }


    }



    /**
     * 自动安装的方法，用户点击安装时执行
     */
    private void installApk() {

        String name = CacheUtil.getString(SiterSDK.SETTINGS_CONFIG_DOWN_URL,"");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", new File(Environment
                    .getExternalStorageDirectory(), name));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), name)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public void getUpdateInfo(){
//        String appname =  context.getPackageName();
//        Log.i(TAG,"appname:"+appname);
//        Config.getUpdateInfo(context, new HekrUser.LoginListener() {
//            @Override
//            public void loginSuccess(String str) {
//                try {
//                    JSONObject object = new JSONObject(str);
//                    int code = object.getInt("code");
//                    String name = object.getString("name");
//                    Config.UpdateInfo ds = new Config.UpdateInfo();
//                    ds.setCode(code);
//                    ds.setName(name);
//                    if (Config.getVerCode(context, context.getPackageName()) < code) {
//                        handlerUpdate.sendMessage(handlerUpdate.obtainMessage(3, ds));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void loginFail(int errorCode) {
//                com.litesuits.android.log.Log.i(TAG,"更新消息获取失败");
//            }
//        });


            new Thread(){
            @Override
                public void run() {
                try {
                        String verinfo =  SiterHttpUtil.getResultForHttpGet();

                    JSONObject object = new JSONObject(verinfo);
                    int code = object.getInt("code");
                    String version = object.getString("version");
                    String zh = object.getString("zh");
                    String en = object.getString("en");
                    String fr = object.getString("fr");
                    String de = object.getString("de");
                    String es = object.getString("es");
                    String fi = object.getString("fi");
                    String sl = object.getString("sl");
                    String nl = object.getString("nl");
                    String it = object.getString("it");
                    String cs = object.getString("cs");
                    String url = object.getString("url");
                    String urlex = object.getString("url_ex");
                    Config.UpdateInfo ds = new Config.UpdateInfo();
                    ds.setUrl(url);
                    ds.setUrl_ex(urlex);
                    ds.setCode(code);
                    ds.setCs(cs);
                    ds.setVersion(version);
                    ds.setEn(en);
                    ds.setZh(zh);
                    ds.setFr(fr);
                    ds.setFi(fi);
                    ds.setEs(es);
                    ds.setNl(nl);
                    ds.setIt(it);
                    ds.setSl(sl);
                    ds.setDe(de);
                    if (Config.getVerCode(context, context.getPackageName()) < code) {
                        handlerUpdate.sendMessage(handlerUpdate.obtainMessage(3, ds));
                    }
                    } catch (IOException e) {
                    e.printStackTrace();
                    }catch (JSONException e1){
                        e1.printStackTrace();
                }catch (Exception e2){
                        e2.printStackTrace();
                }

            }
            }.start();

    }

    public void initCheckUpate(){

        if(!flag_checkupdate) {
            flag_checkupdate = true;
            new Thread() {
                public void run() {

                    while(count<2)
                    {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        count++;
                    }
                    count = 0;
                    handlerUpdate.sendMessage(handlerUpdate.obtainMessage(1));

                }
            }.start();
        }

    }


    private void downFile(final String url) {
        pBar.show();
        new Thread() {
            public void run() {
                X509TrustManager xtm = new X509TrustManager() {
                    @SuppressWarnings("unused")
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    @SuppressWarnings("unused")
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                            throws java.security.cert.CertificateException {

                    }
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                            throws java.security.cert.CertificateException {

                    }
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                //这个好像是HOST验证
                X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                    public void verify(String arg0, SSLSocket arg1) throws IOException {}
                    public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
                    @SuppressWarnings("unused")
                    public void verify(String arg0, X509Certificate arg1) throws SSLException {}
                    @Override
                    public void verify(String arg0, java.security.cert.X509Certificate arg1) throws SSLException {

                    }
                };


                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
                    SSLContext ctx = SSLContext.getInstance("TLS");
                    //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
                    ctx.init(null, new TrustManager[] { xtm }, null);
                    //创建SSLSocketFactory
                    SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
                    socketFactory.setHostnameVerifier(hostnameVerifier);
                    //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
                    client.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();
                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        System.out
                                .println("Environment.getExternalStorageDirectory()==="
                                        + Environment
                                        .getExternalStorageDirectory());
                        String name = CacheUtil.getString(SiterSDK.SETTINGS_CONFIG_DOWN_URL,"");
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                name);
                        fileOutputStream = new FileOutputStream(file);

                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            count += ch;
                            if (length > 0) {
                                progress = (int) (((float) count / length) * 100);
                                handlerUpdate.sendEmptyMessage(DOWN_UPDATE);
                            }
                        }

                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    down();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (KeyManagementException e1) {
                    e1.printStackTrace();
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                }
            }

        }.start();

    }

    private void down() {
        handlerUpdate.post(new Runnable() {
            public void run() {
                pBar.cancel();
                installApk();
            }
        });

    }


}
