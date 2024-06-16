package com.niuniu.babyprotect.model;
public class OtherTimeInfo {
    int availableDailyTime;
    int availableHolidayTime;
    int availableWeekendTime;
    String dailyEndTime;
    String dailyStartTime;
    int dailyStatus;
    String holidayEndTime;
    String holidayStartTime;
    int holidayStatus;
    String id;
    String image;
    boolean isDisableDaily;
    boolean isDisableHoliday;
    boolean isDisableWeekend;
    String name;
    String packageName;
    String weekendEndTime;
    String weekendStartTime;
    int weekendStatus;

    public int getAvailableDailyTime() {
        return this.availableDailyTime;
    }

    public void setAvailableDailyTime(int availableDailyTime) {
        this.availableDailyTime = availableDailyTime;
    }

    public int getAvailableHolidayTime() {
        return this.availableHolidayTime;
    }

    public void setAvailableHolidayTime(int availableHolidayTime) {
        this.availableHolidayTime = availableHolidayTime;
    }

    public int getAvailableWeekendTime() {
        return this.availableWeekendTime;
    }

    public void setAvailableWeekendTime(int availableWeekendTime) {
        this.availableWeekendTime = availableWeekendTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isDisableDaily() {
        return this.isDisableDaily;
    }

    public void setDisableDaily(boolean disableDaily) {
        this.isDisableDaily = disableDaily;
    }

    public boolean isDisableHoliday() {
        return this.isDisableHoliday;
    }

    public void setDisableHoliday(boolean disableHoliday) {
        this.isDisableHoliday = disableHoliday;
    }

    public boolean isDisableWeekend() {
        return this.isDisableWeekend;
    }

    public void setDisableWeekend(boolean disableWeekend) {
        this.isDisableWeekend = disableWeekend;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getDailyStatus() {
        return this.dailyStatus;
    }

    public void setDailyStatus(int dailyStatus) {
        this.dailyStatus = dailyStatus;
    }

    public String getDailyStartTime() {
        return this.dailyStartTime;
    }

    public void setDailyStartTime(String dailyStartTime) {
        this.dailyStartTime = dailyStartTime;
    }

    public String getDailyEndTime() {
        return this.dailyEndTime;
    }

    public void setDailyEndTime(String dailyEndTime) {
        this.dailyEndTime = dailyEndTime;
    }

    public int getWeekendStatus() {
        return this.weekendStatus;
    }

    public void setWeekendStatus(int weekendStatus) {
        this.weekendStatus = weekendStatus;
    }

    public String getWeekendStartTime() {
        return this.weekendStartTime;
    }

    public void setWeekendStartTime(String weekendStartTime) {
        this.weekendStartTime = weekendStartTime;
    }

    public String getWeekendEndTime() {
        return this.weekendEndTime;
    }

    public void setWeekendEndTime(String weekendEndTime) {
        this.weekendEndTime = weekendEndTime;
    }

    public int getHolidayStatus() {
        return this.holidayStatus;
    }

    public void setHolidayStatus(int holidayStatus) {
        this.holidayStatus = holidayStatus;
    }

    public String getHolidayStartTime() {
        return this.holidayStartTime;
    }

    public void setHolidayStartTime(String holidayStartTime) {
        this.holidayStartTime = holidayStartTime;
    }

    public String getHolidayEndTime() {
        return this.holidayEndTime;
    }

    public void setHolidayEndTime(String holidayEndTime) {
        this.holidayEndTime = holidayEndTime;
    }
}
