package com.ilop.sthome.utils.greenDao.converter;

import com.google.gson.Gson;
import com.ilop.sthome.data.greenDao.SceneModelBean;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @author skygge
 * @date 2020-02-17.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SceneModelConverter implements PropertyConverter<SceneModelBean, String> {

    @Override
    public SceneModelBean convertToEntityProperty(String databaseValue) {
        return new Gson().fromJson(databaseValue, SceneModelBean.class);
    }

    @Override
    public String convertToDatabaseValue(SceneModelBean entityProperty) {
        return new Gson().toJson(entityProperty);
    }
}
