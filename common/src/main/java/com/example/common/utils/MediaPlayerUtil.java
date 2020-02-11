package com.example.common.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * @Author skygge.
 * @Date on 2019-08-23.
 * @Github https://github.com/javofxu
 * @Dec: 音频播放
 * @version: ${VERSION}.
 * @Update :
 */
public class MediaPlayerUtil {

    private Context mContext;
    private AssetManager mManager;
    private MediaPlayer mPlayer;

    public MediaPlayerUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void play(String mp3_name){
        mManager = mContext.getAssets();
        try {
            AssetFileDescriptor fileDescriptor = mManager.openFd(mp3_name+".mp3");
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        if (mPlayer!=null) mPlayer.stop();
    }

    public void destroy(){
        if (mPlayer!=null) mPlayer.release();
    }
}
