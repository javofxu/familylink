package com.ilop.sthome.data.bean;

import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.tools.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jishu0001 on 2016/8/19.
 */
public class SysModelAliBean {
    private String modleName;
    private int choice;
    private int sid;
    private String deviceName;
    private String color;

    private String code;

    private byte scene_default;

    private List<ShortcutAliBean> shortcutAliBeanList;
    private List<SceneRelationBean> sceneRelationBeanList;

    public String getModleName() {
        return modleName;
    }

    public void setModleName(String modleName) {
        this.modleName = modleName;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte getScene_default() {
        return scene_default;
    }

    public void setScene_default(byte scene_default) {
        this.scene_default = scene_default;
    }

    public List<ShortcutAliBean> getShortcutAliBeanList() {
        return shortcutAliBeanList;
    }

    public void setShortcutAliBeanList(List<ShortcutAliBean> shortcutAliBeanList) {
        this.shortcutAliBeanList = shortcutAliBeanList;
    }

    public List<SceneRelationBean> getSceneRelationBeanList() {
        return sceneRelationBeanList;
    }

    public void setSceneRelationBeanList(List<SceneRelationBean> sceneRelationBeanList) {
        this.sceneRelationBeanList = sceneRelationBeanList;
    }

    @Override
    public String toString() {
        return "SysModelAliBean{" +
                "modleName='" + modleName + '\'' +
                ", choice=" + choice +
                ", sid=" + sid +
                ", deviceName='" + deviceName + '\'' +
                ", color='" + color + '\'' +
                ", code='" + code + '\'' +
                ", scene_default=" + scene_default +
                '}';
    }

    public void create(String deviceName){
        String value = getCode();
        byte default_scene_code = 0;
        if(value.length()>=32){
            String initname = value.substring(6,38);
            int sid = Integer.parseInt(getCode().substring(4,6),16);
            int modeNo = Integer.parseInt(getCode().substring(38,40),16);
            int listNo = Integer.parseInt(getCode().substring(40,42),16);
            int totalLength = 42 + modeNo * 14 +  listNo * 2;
            String color = "F"+ sid;
            if(value.length()>totalLength){
                if("F".equals(value.substring(totalLength,totalLength+1))){
                    color = value.substring(totalLength,totalLength+2);
                }
                if(value.length()==(totalLength+4)){
                    String scene_code = value.substring(totalLength+2,totalLength+4);
                    default_scene_code = ByteUtil.hexStr2Bytes(scene_code)[0];
                }
            }

            String name =  CoderALiUtils.getStringFromAscii(initname);
            if(name != null){
                setSid(sid);
                setModleName(name);
                setChoice(0);
                setColor(color);
                setDeviceName(deviceName);
                setScene_default(default_scene_code);
                String buf = code;
                if(code.length()>=38 && (code.length()%2)==0){

                    int length_mode = Integer.parseInt(buf.substring(38,40),16);
                    String newcode_mode = buf.substring(38+4,38+4+length_mode*14);
                    List<ShortcutAliBean> list = new ArrayList<>();
                    for(int i=0;i<length_mode;i++){
                        String data =  newcode_mode.substring(0,14);
                        newcode_mode = newcode_mode.substring(14);

                        int eqid =Integer.parseInt(data.substring(0,4),16);
                        int content =  Integer.parseInt(data.substring(4,6),16);
                        ShortcutAliBean shortcutBean = new ShortcutAliBean();
                        shortcutBean.setEqid(eqid);
                        shortcutBean.setDes_sid(content);
                        shortcutBean.setDelay(0);
                        shortcutBean.setSrc_sid(sid);
                        shortcutBean.setDeviceName(deviceName);
                        list.add(shortcutBean);
                    }

                    int length = Integer.parseInt(buf.substring(40,42),16);
                    String newcode = buf.substring(38+4+length_mode*14);
                    List<SceneRelationBean> list2 = new ArrayList<>();
                    for(int i=0;i<length;i++){
                        String data =  newcode.substring(0,2);
                        int ds = Integer.parseInt(data,16);
                        newcode = newcode.substring(2);
                        SceneRelationBean sceneRelationBean = new SceneRelationBean();
                        sceneRelationBean.setMid(ds);
                        sceneRelationBean.setSid(sid);
                        sceneRelationBean.setDeviceName(deviceName);
                        list2.add(sceneRelationBean);
                    }
                 setSceneRelationBeanList(list2);
                 setShortcutAliBeanList(list);
                }

            }
        }
    }
}
