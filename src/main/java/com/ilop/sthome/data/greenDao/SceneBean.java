package com.ilop.sthome.data.greenDao;

import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.converter.SceneRelationConverter;
import com.ilop.sthome.utils.greenDao.converter.SceneSwitchConverter;
import com.ilop.sthome.utils.tools.ByteUtil;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author skygge
 * @date 2020-02-16.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：场景类
 */
@Entity
public class SceneBean {

    @Id(autoincrement = true)
    private Long id;

    private String modleName;

    private int choice;

    private int sid;

    private String deviceName;

    private String color;

    private String code;

    private byte scene_default;

    //用到了这个Convert注解，表明它们的转换类，这样就可以转换成String保存到数据库中了
    @Convert(columnType = String.class, converter = SceneSwitchConverter.class)
    List<SceneSwitchBean> switchList; //实体类中网关list数据

    @Convert(columnType = String.class, converter = SceneRelationConverter.class)
    List<SceneRelationBean> relationList;

    @Generated(hash = 1782259755)
    public SceneBean(Long id, String modleName, int choice, int sid,
            String deviceName, String color, String code, byte scene_default,
            List<SceneSwitchBean> switchList,
            List<SceneRelationBean> relationList) {
        this.id = id;
        this.modleName = modleName;
        this.choice = choice;
        this.sid = sid;
        this.deviceName = deviceName;
        this.color = color;
        this.code = code;
        this.scene_default = scene_default;
        this.switchList = switchList;
        this.relationList = relationList;
    }

    @Generated(hash = 418765892)
    public SceneBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModleName() {
        return this.modleName;
    }

    public void setModleName(String modleName) {
        this.modleName = modleName;
    }

    public int getChoice() {
        return this.choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte getScene_default() {
        return this.scene_default;
    }

    public void setScene_default(byte scene_default) {
        this.scene_default = scene_default;
    }

    public List<SceneSwitchBean> getSwitchList() {
        return this.switchList;
    }

    public void setSwitchList(List<SceneSwitchBean> switchList) {
        this.switchList = switchList;
    }

    public List<SceneRelationBean> getRelationList() {
        return this.relationList;
    }

    public void setRelationList(List<SceneRelationBean> relationList) {
        this.relationList = relationList;
    }

    public void createScene(String deviceName){
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
                List<SceneSwitchBean> list = new ArrayList<>();
                for(int i=0;i<length_mode;i++){
                    String data =  newcode_mode.substring(0,14);
                    newcode_mode = newcode_mode.substring(14);

                    int eqid =Integer.parseInt(data.substring(0,4),16);
                    int content =  Integer.parseInt(data.substring(4,6),16);
                    SceneSwitchBean shortcutBean = new SceneSwitchBean();
                    shortcutBean.setDeviceId(eqid);
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
                setSwitchList(list);
                setRelationList(list2);
            }
        }
    }
}
