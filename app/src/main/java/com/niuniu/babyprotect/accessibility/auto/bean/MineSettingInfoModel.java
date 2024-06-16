package com.niuniu.babyprotect.accessibility.auto.bean;

import java.util.List;
public class MineSettingInfoModel {
    List<CheckBoxModel> checkBoxModels;
    String itemName;

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<CheckBoxModel> getCheckBoxModels() {
        return this.checkBoxModels;
    }

    public void setCheckBoxModels(List<CheckBoxModel> checkBoxModels) {
        this.checkBoxModels = checkBoxModels;
    }
}
