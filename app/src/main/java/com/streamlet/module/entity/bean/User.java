package com.streamlet.module.entity.bean;

import com.streamlet.common.util.StringUtils;

/**
 * Created by streamlet2 on 2016/10/20.
 *
 * @Description 用户实体类
 */
public class User {

    private int id;
    private String nickName= StringUtils.EMPTY;
    private String sex= StringUtils.EMPTY;;
    private String adress= StringUtils.EMPTY;;
    private int age;
    private String sign= StringUtils.EMPTY;;
    private String token= StringUtils.EMPTY;;
    private int familyId;

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(){}

    public User(int id, String nickName, String sex, String adress, int age, String sign, String token,int familyId) {
        this.id = id;
        this.nickName = nickName;
        this.sex = sex;
        this.adress = adress;
        this.age = age;
        this.sign = sign;
        this.token = token;
        this.familyId=familyId;
    }
}
