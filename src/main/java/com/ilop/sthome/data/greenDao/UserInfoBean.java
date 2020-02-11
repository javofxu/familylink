package com.ilop.sthome.data.greenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author skygge
 * @date 2020-02-05.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：用户信息类
 */

@Entity
public class UserInfoBean {

    @Id(autoincrement = true)
    Long id;

    String identityId;

    String loginId;

    String loginName;

    String loginSource;

    String nickName;

    String phone;

    String email;

    String avatarUrl;

    String gmtCreate;

    String gmtModified;

    @Generated(hash = 24103471)
    public UserInfoBean(Long id, String identityId, String loginId,
            String loginName, String loginSource, String nickName, String phone,
            String email, String avatarUrl, String gmtCreate, String gmtModified) {
        this.id = id;
        this.identityId = identityId;
        this.loginId = loginId;
        this.loginName = loginName;
        this.loginSource = loginSource;
        this.nickName = nickName;
        this.phone = phone;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginSource() {
        return this.loginSource;
    }

    public void setLoginSource(String loginSource) {
        this.loginSource = loginSource;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
    
}
