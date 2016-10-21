package com.streamlet.module.entity.response;

import com.streamlet.module.entity.base.BaseResponse;
import com.streamlet.module.entity.bean.Family;

import java.util.List;

/**
 * Created by streamlet2 on 2016/10/21.
 *
 * @Description Family实体类列表
 */
public class FamilyListResponse extends BaseResponse{

    private List<Family> familys;

    public List<Family> getFamilys() {
        return familys;
    }

    public void setFamilys(List<Family> familys) {
        this.familys = familys;
    }
}
