package com.ilop.sthome.network.udp;

import android.content.Context;
import android.util.Log;

import com.ilop.sthome.data.bean.GatewayBean;
import com.ilop.sthome.data.event.EventUdpReceive;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class UDPRecData implements Runnable {
    private static final String TAG = "UDPRecData";
    private static final int PORT = 1025;
    private InetAddress hostip;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private byte[] bytes;
    private InetAddress hostAdd;
    private Context context;
    private boolean enudp = true;
    private int type = 0; //0代表接收，1代表绑定
    private static final String ACK = "NODE_ACK";//设备应答
    private static final String SEND = "NODE_SEND";//设备上报
    private static final String CMD_ACTION="action";
    private static final String CMD_DEVICEID="devID";
    private static final String CMD_MSG="msg";
    private static final String CMD_CODE="CMD_CODE";

    public UDPRecData(DatagramSocket ds, InetAddress hostAdd, Context context,int type){
        this.datagramSocket = ds;
        this.hostAdd = hostAdd;
        this.context = context;
        setType(type);
        this.enudp = true;

    }

    @Override
    public void run() {
        while (enudp){
            bytes = new byte[512];
            datagramPacket = new DatagramPacket(bytes,bytes.length,hostAdd,PORT);
            try {
                Log.i(TAG," start to receive");
                datagramSocket.receive(datagramPacket);
                String msg = null;
                msg = new String(datagramPacket.getData());
                Log.i(TAG,"get udp message:"+msg);
                hostip = datagramPacket.getAddress();
                resolveData(msg.substring(0,msg.lastIndexOf("}")+2));
            } catch (IOException e) {
                Log.i(TAG," receive failed  Socket closed");
                break;
            }catch (NullPointerException e){
                e.printStackTrace();
                Log.i(TAG," receive failed NullPointerException");
            }
        }
    }

    private void resolveData(String msg){
        Log.i(TAG,"recive resolveData:"+msg);
            try {
                JSONObject jsonObject = new JSONObject(msg);
                if(jsonObject.has(CMD_ACTION)&&jsonObject.has(CMD_MSG)) {
                    JSONObject jmsg = jsonObject.getJSONObject(CMD_MSG);
                    int command = jmsg.getInt(CMD_CODE);
                    Log.i(TAG, "resolveData: BB"+ command);
                    GatewayUdpListConstant gatewayUdpListConstant = GatewayUdpListConstant.getInstance();
                    if(0==command){
                        GatewayBean gb = new GatewayBean();
                        gb.setName(jsonObject.getString(CMD_DEVICEID));
                        gb.setIpAddress(hostip);
                        gb.setOnline(true);
                        gatewayUdpListConstant.addGateBean(gb);
                    }else{
                        if(gatewayUdpListConstant.checkUdpDeviceBelongToUser(jsonObject.getString(CMD_DEVICEID))){
                            if(ACK.equalsIgnoreCase(jsonObject.getString(CMD_ACTION))||SEND.equalsIgnoreCase(jsonObject.getString(CMD_ACTION))){
                                gatewayUdpListConstant.setReveiveDataOrNot(jsonObject.getString(CMD_DEVICEID),command);
                                Log.i(TAG,"receive right command>0: is ack"+gatewayUdpListConstant.isReveiveDataOrNot());
                            }
                            EventUdpReceive ue = new EventUdpReceive();
                            ue.setMsg(msg);
                            EventBus.getDefault().post(ue);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG,"exception =="+msg);
                EventUdpReceive ue = new EventUdpReceive();
                ue.setMsg(msg);
                EventBus.getDefault().post(ue);
            }
//        }
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void  close(){
        try {
            enudp = false;
            datagramSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
