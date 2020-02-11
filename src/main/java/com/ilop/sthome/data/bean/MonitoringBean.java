package com.ilop.sthome.data.bean;

import java.util.List;

public class MonitoringBean {

    private String userId;

    private List<AttrList> attrList;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setAttrList(List<AttrList> attrList) {
        this.attrList = attrList;
    }

    public List<AttrList> getAttrList() {
        return attrList;
    }

    public class AttrList {

        private String attrKey;
        private String attrValue;

        public void setAttrKey(String attrKey) {
            this.attrKey = attrKey;
        }

        public String getAttrKey() {
            return attrKey;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }

        public String getAttrValue() {
            return attrValue;
        }
    }
}
