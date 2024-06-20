package com.niu.protect.accessibility.auto.device;

import android.content.Context;
import com.niu.protect.accessibility.auto.bean.CheckBoxModel;
import com.niu.protect.accessibility.auto.bean.MineSettingInfoModel;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import java.util.ArrayList;
import java.util.List;
public abstract class BaseCreatePageInfo {
    List<CheckBoxModel> checkBoxModels;
    MineSettingInfoModel mMineSettingInfoModel;
    protected List<MineSettingInfoModel> settingInfoModels = new ArrayList<>();
    protected int totalSteps;

    protected abstract BaseCreatePageInfo createInfo();

    public abstract List<PageInfoModel> createPage(Context context);

    public PageInfoModel createMineSettingPage(String pageTag, String nextClickText, int parentStep, int step) {
        PageInfoModel pInfo = createNormal(pageTag, parentStep, step);
        pInfo.setNextPageClick(nextClickText);
        pInfo.setParentPage(2);
        MineSettingInfoModel mineSettingInfoModel = new MineSettingInfoModel();
        this.mMineSettingInfoModel = mineSettingInfoModel;
        mineSettingInfoModel.setItemName(pageTag);
        this.checkBoxModels = new ArrayList<>();
        return pInfo;
    }

    public PageInfoModel createParentPage(String pageTag, String nextClickText, int parentStep, int step) {
        PageInfoModel pInfo = createNormal(pageTag, parentStep, step);
        pInfo.setNextPageClick(nextClickText);
        pInfo.setParentPage(1);
        return pInfo;
    }

    public PageInfoModel createCenterPage(String pageTag, String nextClickText, int parentStep, int step) {
        PageInfoModel pInfo = createNormal(pageTag, parentStep, step);
        pInfo.setNextPageClick(nextClickText);
        pInfo.setParentPage(1);
        return pInfo;
    }

    public PageInfoModel createLastPage(String pageTag, int parentStep, int step, int clickBackTimes, String maiPageInfo, int gotoSettingType) {
        PageInfoModel pInfo = createNormal(pageTag, parentStep, step);
        pInfo.setBackTimes(clickBackTimes);
        pInfo.setParentPage(0);
        pInfo.setGotoSettingType(gotoSettingType);
        pInfo.setMainPageInfo(maiPageInfo);
        this.totalSteps = step;
        return pInfo;
    }

    public PageInfoModel createLastAlterWindowsPage(String pageTag, int parentStep, int step, String nextText, int clickBackTimes, String maiPageInfo, int gotoSettingType) {
        PageInfoModel pInfo = createNormal(pageTag, parentStep, step);
        pInfo.setBackTimes(clickBackTimes);
        pInfo.setParentPage(0);
        pInfo.setNextPageClick(nextText);
        pInfo.setGotoSettingType(gotoSettingType);
        pInfo.setMainPageInfo(maiPageInfo);
        this.totalSteps = step;
        return pInfo;
    }

//    protected PageInfoModel createCenterCheckboxPage(String pageTag, int parentStep, int step) {
//        PageInfoModel pInfo = createNormal(pageTag, parentStep, step);
//        pInfo.setParentPage(0);
//        this.totalSteps = step;
//        return pInfo;
//    }

    public CheckBoxModel createCheckBoxStep(String checkBoxText, int defaultCheckBoxValue, boolean isLastCheckbox) {
        CheckBoxModel checkBoxModel = new CheckBoxModel();
        checkBoxModel.setClickCheckBoxItemText(checkBoxText);
        checkBoxModel.setCheckBoxDefaultValue(defaultCheckBoxValue);
        this.checkBoxModels.add(checkBoxModel);
        if (isLastCheckbox) {
            this.mMineSettingInfoModel.setCheckBoxModels(this.checkBoxModels);
            this.settingInfoModels.add(this.mMineSettingInfoModel);
        }
        return checkBoxModel;
    }

    private PageInfoModel createNormal(String pageTag, int parentStep, int step) {
        PageInfoModel mPageInfoModel = new PageInfoModel();
        mPageInfoModel.setPageTagInfo(pageTag);
        mPageInfoModel.setParentStep(parentStep);
        mPageInfoModel.setStep(step);
        return mPageInfoModel;
    }
}
