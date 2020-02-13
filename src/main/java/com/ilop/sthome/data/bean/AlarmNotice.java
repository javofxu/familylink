package com.ilop.sthome.data.bean;

/**
 * @Author skygge.
 * @Date on 2020-02-06.
 * @Dec: 设备消息推送列表
 */
public class AlarmNotice {

    private String eventId;
    private String eventName;
    private boolean noticeEnabled;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public boolean isNoticeEnabled() {
        return noticeEnabled;
    }

    public void setNoticeEnabled(boolean noticeEnabled) {
        this.noticeEnabled = noticeEnabled;
    }
}
