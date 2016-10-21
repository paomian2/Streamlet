package com.streamlet.module.entity.bean;

import com.streamlet.common.util.StringUtils;

/**
 * Created by streamlet2 on 2016/10/21.
 *
 * @Description Family实体类
 */
public class Family {
    private int id;
    private String name= StringUtils.EMPTY;
    private String introduce= StringUtils.EMPTY;
    private double lat;
    private double lng;
    private int parentId;
    private String familysId= StringUtils.EMPTY;
    private String familyMusic= StringUtils.EMPTY;
    private String status= StringUtils.EMPTY;
    private String logo= StringUtils.EMPTY;;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getFamilysId() {
        return familysId;
    }

    public void setFamilysId(String familysId) {
        this.familysId = familysId;
    }

    public String getFamilyMusic() {
        return familyMusic;
    }

    public void setFamilyMusic(String familyMusic) {
        this.familyMusic = familyMusic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
