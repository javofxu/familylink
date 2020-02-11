package com.ilop.sthome.data.bean;
import java.util.List;

/**
 * @author skygge
 * @date 2020-01-01.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：所有产品
 */

public class ProductBean {
    private  int type;
    private List<String> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
