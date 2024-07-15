package com.niu.protect.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
public class UserBlackAppInfoModel extends BaseModel {
    @SerializedName("data")
    private List<AppData> data;

    public List<AppData> getData() {
        return this.data;
    }

    public void setData(List<AppData> data) {
        this.data = data;
    }

}
