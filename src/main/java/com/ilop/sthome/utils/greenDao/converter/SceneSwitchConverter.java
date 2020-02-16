package com.ilop.sthome.utils.greenDao.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilop.sthome.data.greenDao.SceneSwitchBean;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-08.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SceneSwitchConverter implements PropertyConverter<List<SceneSwitchBean>, String> {
    @Override
    public List<SceneSwitchBean> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        // 先得获得这个，然后再typeToken.getType()，否则会异常
        TypeToken<List<SceneSwitchBean>> typeToken = new TypeToken<List<SceneSwitchBean>>(){};
        return new Gson().fromJson(databaseValue, typeToken.getType());
    }

    @Override
    public String convertToDatabaseValue(List<SceneSwitchBean> entityProperty) {
        if (entityProperty == null||entityProperty.size()==0) {
            return null;
        } else {
            String sb = new Gson().toJson(entityProperty);
            return sb;
        }
    }
}
