package com.ilop.sthome.ui.adapter.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basic.G;
import com.example.xmpic.support.FunLog;
import com.example.xmpic.support.FunPath;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceFileListener;
import com.example.xmpic.support.config.OPCompressPic;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.models.FunFileData;
import com.example.xmpic.support.utils.FileDataUtils;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.siterwell.familywellplus.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Jeff on 16/5/16.
 *
 */
public class DeviceCameraPicAdapter extends RecyclerView.Adapter<DeviceCameraPicAdapter.ItemHolder> implements OnFunDeviceFileListener {

    private FunDevice mFunDevice;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<FunFileData> mDatas = null;
    private LruCache<String, Bitmap> mLruCache;
    private OPCompressPic opCompressPic;
    private RecyclerView mImagList;
    private int mPlayingIndex = -1;
    private List<Integer> mDispPosition = new ArrayList<Integer>();

    //尝试重新获取没加载的缩略图的计数器和最大次数
    private int retryCounter = 0;
    private final int RETRY_MAX_NUM = 10;

    private final int MESSAGE_SEARCH_FILE_INFO = 0x100;
    private final int MESSAGE_SEARCH_FILE_PICTURE = 0x101;
    private final int MESSAGE_SEARCH_FILE_SUCCESS = 0x102;

    public DeviceCameraPicAdapter(Context context, RecyclerView imgList, FunDevice funDevice, List<FunFileData> datas) {
        mContext = context;
        mImagList = imgList;
        mInflater = LayoutInflater.from(mContext);
        mFunDevice = funDevice;
        mDatas = datas;
        initDtata();
        FunSupport.getInstance().registerOnFunDeviceFileListener(this);
    }

    private void initDtata() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        // 设置图片缓存大小为maxMemory的1/3
        int cacheSize = maxMemory / 3;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
        opCompressPic = new OPCompressPic();
        opCompressPic.setWidth(160);
        opCompressPic.setHeight(100);
        opCompressPic.setIsGeo(1);
    }

    public void release() {
        FunSupport.getInstance().removeOnFunDeviceFileListener(this);
        
        if ( null != mHandler ) {
        	mHandler.removeCallbacksAndMessages(null);
        	mHandler = null;
        }
        
        if ( null != mLruCache ) {
            mLruCache.evictAll();
            mLruCache = null;
        }
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_device_camera_pic, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {
        FunFileData info = mDatas.get(i);
        int oldPosition = -1;
        if (oldPosition != i) {
            holder.position = i;
            retryCounter = 0;
            if (oldPosition >= 0) {
                if ( mDispPosition.contains(oldPosition) ) {
                    mDispPosition.remove((Integer)oldPosition);
                }
            }
            if ( !mDispPosition.contains(i) ) {
                mDispPosition.add(i);
            }

            if ( !info.hasSeachedFile() ) {
                // 文件信息还没搜索到,是需要搜索的.
                resetSearchFileInfo();
            }
        }

        if (info != null) {
            holder.baseLayout.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(info.getBeginTimeStr())) {
                String timeStr;
                if (info.getFileName().endsWith(".h264")) {
                    // 当前正在播放的高亮显示
                    if ( mPlayingIndex == i ) {
                        holder.event.setTextColor(mContext.getResources().getColor(R.color.text_color_selected));
                        holder.time.setTextColor(mContext.getResources().getColor(R.color.text_color_selected));
                    } else {
                        holder.event.setTextColor(mContext.getResources().getColor(R.color.delist_bk));
                        holder.time.setTextColor(mContext.getResources().getColor(R.color.delist_bk));
                    }

                    timeStr = info.getBeginTimeStr() + " - " + info.getEndTimeStr();
                } else {
                    timeStr = info.getBeginTimeStr();
                }
                holder.time.setText(timeStr);
            } else {
                holder.time.setText("");
            }
            holder.id.setText(info.getFileName());
            String type = FileDataUtils.getStrFileType(mContext, info.getFileName());
            holder.event.setText(type);
            if (!TextUtils.isEmpty(type) && type.equals(mContext.getString(R.string.device_pic_type_manual))) {
                holder.event.setCompoundDrawables(null, null,
                        setTopDrawable(R.mipmap.icon_devpicture_hand),
                        null);
            } else {
                holder.event.setCompoundDrawables(null, null, null, null);
            }
        }else {
            holder.baseLayout.setVisibility(View.INVISIBLE);
        }

        if (info.getCapTempPath() != null) {
            Bitmap bitmap = dealBitmap(info.getCapTempPath());
            holder.image.setImageBitmap(bitmap);
        }else {
            holder.image.setImageResource(R.color.text_content);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas ==null ? 0 : mDatas.size();
    }

    private void resetSearchFileInfo() {
        if ( null != mHandler ) {
            mHandler.removeMessages(MESSAGE_SEARCH_FILE_INFO);
            mHandler.sendEmptyMessageDelayed(MESSAGE_SEARCH_FILE_INFO, 1000);
        }
    }

    private void resetSearchFileBmp() {
        if ( null != mHandler ) {
            mHandler.removeMessages(MESSAGE_SEARCH_FILE_PICTURE);
            mHandler.sendEmptyMessageDelayed(MESSAGE_SEARCH_FILE_PICTURE, 1000);
        }
    }



    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_push_result_layout)
        RelativeLayout baseLayout;
        @BindView(R.id.iv_push_result_checked)
        ImageView image;
        @BindView(R.id.tv_push_result_id)
        TextView id;
        @BindView(R.id.tv_push_result_time)
        TextView time;
        @BindView(R.id.tv_push_result_event)
        TextView event;
        @BindView(R.id.tv_push_result_status)
        TextView status;
        int position;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setPlayingIndex(int index) {
        mPlayingIndex = index;
        notifyDataSetChanged();
    }
    
    public void setBitmapTempPath(String path){
    	mDatas.get(mPlayingIndex).setCapTempPath(path);
    	notifyDataSetChanged();
    }

    private Drawable setTopDrawable(int src) {
        Drawable topDrawable = mContext.getResources().getDrawable(src);
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(),
                topDrawable.getMinimumHeight());
        return topDrawable;
    }

    private Bitmap loadBitmap(int position, boolean toDownload) {

    	if ( position >= 0 && position < mDatas.size() ) {
	        FunFileData funFileData = mDatas.get(position);
	        H264_DVR_FILE_DATA info = funFileData.getFileData();
	        if (null == info || info.st_3_beginTime.st_0_year == 0) {
	            return null;
	        }

	        String fileName_thumb = FunPath.getSptTempPath()
	                + File.separator
	                + FunPath.getDownloadFileNameByData(info, 1, true);
	        String fileName_or = FunPath.getSptTempPath()
	                + File.separator
	                + FunPath.getDownloadFileNameByData(info, 1, false);
	        final long fileSize_thumb = FunPath.isFileExists(fileName_thumb);
	        final long fileSize_or = FunPath.isFileExists(fileName_or);
            String fileName = fileSize_thumb > 0 ? fileName_thumb
	                : (fileSize_or > 0 ? fileName_or : fileName_thumb);
	        if (fileSize_thumb > 0 || fileSize_or > 0) {
	            Bitmap bitmap = getBitmapFromLruCache(funFileData.getFileName());
	            if (null == bitmap) {
	                bitmap = dealBitmap(fileName);
	            }
	
	            if (null != bitmap) {
	                addBitmapToLruCache(funFileData.getFileName(), bitmap);
                    return bitmap;
	
	            } else {
	                FunPath.deleteFile(fileName);
	            }
	        }else if (toDownload) {
	            if (null == opCompressPic) {
	                return null;
	            }
	
	            opCompressPic
	                    .setPicName(G.ToString(info.st_2_fileName));
	            FunSupport.getInstance().requestDeviceSearchPicture(mFunDevice,
	                    opCompressPic, fileName_thumb, position);
	        }
    	}
        return null;
    }


    private Bitmap dealBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap == null)
            return null;
        Bitmap newBtimap = Bitmap.createScaledBitmap(bitmap, 160, 90, true);
        bitmap.recycle();
        return newBtimap;
    }

    private void checkAndLoadBmps() {
        new Thread() {

            @Override
            public void run() {
                FunLog.d("test", "-------------checkAndLoadBmps Begin");
                for ( int i = 0; i < mDispPosition.size(); i ++ ) {
                    //checkItemBitmap(mDispPosition.get(i), true);
                	if (mDispPosition.size() <= 0) {
						return;
					}
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    FunLog.d("test", "-------------Position:" + mDispPosition.get(i));
                    if ( checkItemBitmap(mDispPosition.get(i)) ) {
                        if ( null != mHandler ) {
                            Message msg = new Message();
                            msg.what = MESSAGE_SEARCH_FILE_SUCCESS;
                            msg.arg1 = mDispPosition.get(i);
                            mHandler.sendMessage(msg);
                        }
                    }
                }
                if (!checkDispItemBitmap() && retryCounter < RETRY_MAX_NUM) {
                    retryCounter++;
                    if (mHandler != null) {
                    	mHandler.removeMessages(MESSAGE_SEARCH_FILE_PICTURE);
                    	mHandler.sendEmptyMessageDelayed(MESSAGE_SEARCH_FILE_PICTURE, 5000);
					}
                }
            }

        }.start();

    }

    private boolean checkItemBitmap(int position) {
        FunFileData fileData = mDatas.get(position);
        if ( null == fileData ) {
            return false;
        }

        if ( FunPath.isValidPath(fileData.getFileName()) ) {
            Bitmap bmp = loadBitmap(position, true);
            return null != bmp;
        }
        return false;
    }

    private boolean checkDispItemBitmap() {
        for ( int i = 0; i < mDispPosition.size(); i ++ ) {
            FunFileData fileData = mDatas.get(mDispPosition.get(i));
            if (null == fileData || loadBitmap(mDispPosition.get(i), false) == null) {
                return false;
            }
        }
        return true;
    }

    private void setItemBitmap(int position) {
        FunFileData fileData = mDatas.get(position);
        if ( null == fileData ) {
            return;
        }

        if ( FunPath.isValidPath(fileData.getFileName()) ) {
            ImageView iv = mImagList.findViewWithTag(fileData.getFileName());
            if ( null != iv ) {
                Bitmap bmp = loadBitmap(position, false);
                if ( null != bmp ) {
                    iv.setImageBitmap(bmp);
                }
            }
        }
    }

    /**
     * 将图片存储到LruCache
     */
    public void addBitmapToLruCache(String key, Bitmap bitmap) {
        if (mLruCache != null) {
            synchronized (mLruCache) {
                if (getBitmapFromLruCache(key) == null && bitmap != null) {
                    mLruCache.put(key, bitmap);
                }
            }
        }
    }

    /**
     * 从LruCache缓存获取图片
     */
    public Bitmap getBitmapFromLruCache(String key) {
        if ( null == mLruCache ) {
            return null;
        }

        return mLruCache.get(key);
    }

    /**
     * 为ImageView设置图片(Image) 1 从缓存中获取图片 2 若图片不在缓存中则为其设置默认图片
     */
    private void setImageForImageView(String imagePath, ImageView imageView) {
        if ( null != imagePath && imagePath.length() > 0 ) {
            imageView.setTag(imagePath);
            Bitmap bitmap = getBitmapFromLruCache(imagePath);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.color.text_content);
                resetSearchFileBmp();
            }
        } else {
            imageView.setTag(null);
            imageView.setImageResource(R.color.text_content);
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MESSAGE_SEARCH_FILE_INFO:
                {
                    // check and search file
                    //TODO 写一个回调接口
                }
                break;
                case MESSAGE_SEARCH_FILE_PICTURE:
                {
                    // load Bitmap
                    checkAndLoadBmps();
                }
                break;
                case MESSAGE_SEARCH_FILE_SUCCESS:
                {
                    setItemBitmap(msg.arg1);
                }
                break;
            }
        }

    };


    @Override
    public void onDeviceFileDownCompleted(FunDevice funDevice, String path, int nSeq) {
        if ( null != mHandler ) {
            Message msg = new Message();
            msg.what = MESSAGE_SEARCH_FILE_SUCCESS;
            msg.arg1 = nSeq;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void onDeviceFileDownProgress(int totalSize, int progress, int nSeq) {

    }

    @Override
    public void onDeviceFileDownStart(boolean isStartSuccess, int nSeq) {

    }

}
