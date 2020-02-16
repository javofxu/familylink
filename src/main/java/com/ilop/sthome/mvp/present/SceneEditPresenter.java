package com.ilop.sthome.mvp.present;

import android.content.Context;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.bean.ShortcutAliBean;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.db.SceneAliDAO;
import com.ilop.sthome.data.db.SceneRelaitonAliDAO;
import com.ilop.sthome.data.db.ShortcutAliDAO;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.contract.SceneEditContract;
import com.ilop.sthome.network.api.SendSceneGroupDataAli;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.siterwell.familywellplus.R;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-03.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SceneEditPresenter extends BasePresenterImpl<SceneEditContract.IView> implements SceneEditContract.IPresent {

    private Context mContext;
    private int mSceneId;
    private String mDeviceName;
    private String mName;
    private String mColors;
    private SceneAliDAO mSceneAliDao;
    private ShortcutAliDAO mShortcutDAO;
    private SysmodelAliDAO mSysModelAliDAO;
    private SendSceneGroupDataAli mSendSceneDataAli;

    public SceneEditPresenter(Context mContext, String mDeviceName, int sceneId) {
        this.mContext = mContext;
        this.mSceneId = sceneId;
        this.mDeviceName = mDeviceName;
        mSceneAliDao = new SceneAliDAO(mContext);
        mShortcutDAO = new ShortcutAliDAO(mContext);
        mSysModelAliDAO = new SysmodelAliDAO(mContext);
        DeviceInfoBean mDeviceInfo = DeviceDaoUtil.getInstance().findGatewayByDeviceName(mDeviceName);
        mSendSceneDataAli = new SendSceneGroupDataAli(mContext, mDeviceInfo);
    }

    @Override
    public void refreshName() {
        SysModelAliBean sysModelBean = mSysModelAliDAO.findBySid(mSceneId, mDeviceName);
        mColors = sysModelBean.getColor();
        switch (mSceneId){
            case 0:
                mName = mContext.getString(R.string.home_mode);
                break;
            case 1:
                mName = mContext.getString(R.string.out_mode);
                break;
            case 2:
                mName = mContext.getString(R.string.sleep_mode);
                break;
            case 3:
                mName = mContext.getString(R.string.custom_scene);
                break;
            default:
                mName = sysModelBean.getModleName();
                break;

        }
        mView.showSceneName(mName);
        mView.showSceneColor(Integer.parseInt(mColors.substring(1)));
    }

    @Override
    public void setSceneColor(int color) {
        mColors = "F" + color;
    }

    @Override
    public void onSaveScene() {
        List<SceneAliBean> mSceneList = mSceneAliDao.findAllAmWithoutDefault(mDeviceName);
        createCode(mSceneList);
    }

    @Override
    public void onSaveSuccess() {
        SysModelAliBean sys= new SysModelAliBean();
        sys.setSid(mSceneId);
        sys.setDeviceName(mDeviceName);
        sys.setColor(mColors);
        sys.setModleName(mName);
        mSysModelAliDAO.insertSysmodel(sys);
        mView.onSuccess();
    }

    @Override
    public void deleteScene() {
        List<SceneAliBean> sceneList = mSceneAliDao.findAllAmBySid(mSceneId, mDeviceName);
        if (sceneList.size()>0){
            mView.showToastMsg(mContext.getString(R.string.current_mode_not_allow_delete));
        }else {
            String sd = String.format(mContext.getResources().getString(R.string.want_to_delete_confirm_eq), mName);
            TipDialog dialog = new TipDialog(mContext, ()->{
                mView.showProgress();
                mSendSceneDataAli.deleteSceneGroup(mSceneId);
            });
            dialog.setMsg(sd);
            dialog.show();
        }
    }

    @Override
    public void deleteSceneSuccess(int sceneId) {
        SceneRelaitonAliDAO sceneRelaitonAliDAO = new SceneRelaitonAliDAO(mContext);
        ShortcutAliDAO shortcutAliDAO = new ShortcutAliDAO(mContext);
        mSysModelAliDAO.delete(sceneId, mDeviceName);
        sceneRelaitonAliDAO.deleteAllShortcurt(sceneId, mDeviceName);
        shortcutAliDAO.deleteAllShortcurt(sceneId, mDeviceName);
        mView.onSuccess();
    }

    private void createCode(List<SceneAliBean> mSceneLists) {
        byte scene_default = 0;
        String name;
        if(mSceneId>3){
            name = mName;
        }else{
            name = "";
        }

        int length = 0;
        length +=2;//the total num length

        String id2 = String.valueOf(mSceneId);
        length += 1;//the scene id

        String btnNum;
        List<ShortcutAliBean> shortcutBeans = mShortcutDAO.findShorcutsBysid(mDeviceName, mSceneId);

        if (Integer.toHexString(shortcutBeans.size()).length()<2){  //new mid
            btnNum = "0"+Integer.toHexString(shortcutBeans.size());
        }else{
            btnNum =Integer.toHexString(shortcutBeans.size());
        }
        length +=1;//button num

        String shortcut = "";

        for (int i = 0;i<shortcutBeans.size();i++){
            String eqid ;
            String dessid ;
            if (Integer.toHexString(shortcutBeans.get(i).getEqid()).length()<2){  //new mid
                eqid = "000"+Integer.toHexString(shortcutBeans.get(i).getEqid());
            }else{
                eqid ="00"+Integer.toHexString(shortcutBeans.get(i).getEqid());
            }

            if (Integer.toHexString(shortcutBeans.get(i).getDes_sid()).length()<2){  //new mid
                dessid = "0"+Integer.toHexString(shortcutBeans.get(i).getDes_sid())+"000000";
            }else{
                dessid =Integer.toHexString(shortcutBeans.get(i).getDes_sid())+"000000";
            }

            shortcut+=(eqid+dessid+"00");
            length += 7;
        }


        //self-define scene num
        length +=1;
        int scene =0;
        //scene id
        String sceneCode ="";
        if(mSceneLists.size()>0){
            for(int i = 0; i< mSceneLists.size();i++){
                scene++;
                length++;
                String singleCode;
                if (Integer.toHexString(mSceneLists.get(i).getMid()).length()<2){  //new mid
                    singleCode = "0"+Integer.toHexString(mSceneLists.get(i).getMid());
                }else{
                    singleCode =Integer.toHexString(mSceneLists.get(i).getMid());
                }
                sceneCode += singleCode;
            }
        }else {
            length +=0;
            sceneCode = "";
        }

        scene_default = (byte)(scene_default|0x80);
        //增加了颜色的定制
        length +=2;

        String oooo ="0";
        if(Integer.toHexString(length +32).length() < 4){
            for (int i = 0; i<4-Integer.toHexString(length +32).length()-1; i++ ){
                oooo += 0;
            }
            oooo += Integer.toHexString(length +32);
        }else{
            oooo = Integer.toHexString(length +32);
        }

        String oo = "0";
        if(Integer.toHexString(scene).length()<2){
            oo = oo + Integer.toHexString(scene);
        }else{
            oo = Integer.toHexString(scene);
        }
        String ds = CoderALiUtils.getAscii(name);
        String str_default_scene = ByteUtil.convertByte2HexString(scene_default);
        String fullCode = oooo +"0"+id2 + ds + btnNum  + oo + shortcut + sceneCode + mColors +str_default_scene;
        String crc = ByteUtil.CRCmakerCharAndCode(fullCode);
        mSendSceneDataAli.increaceSceneGroup(fullCode + crc);
    }
}
