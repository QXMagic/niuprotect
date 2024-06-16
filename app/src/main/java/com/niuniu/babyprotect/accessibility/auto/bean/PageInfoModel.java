package com.niuniu.babyprotect.accessibility.auto.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class PageInfoModel implements Serializable {
    List<CheckBoxModel> checkBoxModels;
    private int gotoSettingType;
    private String mainPageInfo;
    int step = 0;
    int parentStep = 0;
    private int backTimes = 0;
    private String pagePackageName = "";
    private String pageClassName = "";
    private String pageTagInfo = "";
    private String nextPageClick = "";
    private int parentPage = -1;

    public int getGotoSettingType() {
        return this.gotoSettingType;
    }

    public void setGotoSettingType(int gotoSettingType) {
        this.gotoSettingType = gotoSettingType;
    }

    public int getBackTimes() {
        return this.backTimes;
    }

    public void setBackTimes(int backTimes) {
        this.backTimes = backTimes;
    }

    public String getPagePackageName() {
        return this.pagePackageName;
    }

    public String getMainPageInfo() {
        return this.mainPageInfo;
    }

    public void setMainPageInfo(String mainPageInfo) {
        this.mainPageInfo = mainPageInfo;
    }

    public void setPagePackageName(String pagePackageName) {
        this.pagePackageName = pagePackageName;
    }

    public String getPageClassName() {
        return this.pageClassName;
    }

    public void setPageClassName(String pageClassName) {
        this.pageClassName = pageClassName;
    }

    public String getPageTagInfo() {
        return this.pageTagInfo;
    }

    public void setPageTagInfo(String pageTagInfo) {
        this.pageTagInfo = pageTagInfo;
    }

    public String getNextPageClick() {
        return this.nextPageClick;
    }

    public void setNextPageClick(String nextPageClick) {
        this.nextPageClick = nextPageClick;
    }

    public int getParentPage() {
        return this.parentPage;
    }

    public void setParentPage(int parentPage) {
        this.parentPage = parentPage;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getParentStep() {
        return this.parentStep;
    }

    public void setParentStep(int parentStep) {
        this.parentStep = parentStep;
    }

    public List<CheckBoxModel> getCheckBoxModels() {
        return this.checkBoxModels;
    }

    public void addCheckBox(CheckBoxModel mCheckBoxModel) {
        if (this.checkBoxModels == null) {
            this.checkBoxModels = new ArrayList();
        }
        this.checkBoxModels.add(mCheckBoxModel);
    }
}
