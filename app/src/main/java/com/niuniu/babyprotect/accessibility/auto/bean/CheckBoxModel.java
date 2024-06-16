package com.niuniu.babyprotect.accessibility.auto.bean;
public class CheckBoxModel {
    int parentStep;
    int step;
    private String widgetType = "checkbox";
    private int clickedStatus = 0;
    private int checkBoxDefaultValue = 1;
    private String clickCheckBoxItemText = "";
    private int onlyOne = 0;

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getClickedStatus() {
        return this.clickedStatus;
    }

    public void setClickedStatus(int clickedStatus) {
        this.clickedStatus = clickedStatus;
    }

    public int getCheckBoxDefaultValue() {
        return this.checkBoxDefaultValue;
    }

    public void setCheckBoxDefaultValue(int checkBoxDefaultValue) {
        this.checkBoxDefaultValue = checkBoxDefaultValue;
    }

    public String getClickCheckBoxItemText() {
        return this.clickCheckBoxItemText;
    }

    public void setClickCheckBoxItemText(String clickCheckBoxItemText) {
        this.clickCheckBoxItemText = clickCheckBoxItemText;
    }

    public int getParentStep() {
        return this.parentStep;
    }

    public void setParentStep(int parentStep) {
        this.parentStep = parentStep;
    }
}
