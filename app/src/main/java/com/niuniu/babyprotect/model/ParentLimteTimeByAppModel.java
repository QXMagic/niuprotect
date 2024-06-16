package com.niuniu.babyprotect.model;

import com.baidu.platform.comapi.map.MapBundleKey;
import com.google.gson.annotations.SerializedName;
import java.util.List;

import atmp.consts.Constants;

public class ParentLimteTimeByAppModel extends BaseModel {
    @SerializedName("data")
    private List<DataDTO> data;

    public List<DataDTO> getData() {
        return this.data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("brand")
        private String brand;
        @SerializedName("dailyEndTime")
        private String dailyEndTime;
        @SerializedName("dailyStartTime")
        private String dailyStartTime;
        @SerializedName("dailyStatus")
        private int dailyStatus;
        @SerializedName("holidayEndTime")
        private String holidayEndTime;
        @SerializedName("holidayStartTime")
        private String holidayStartTime;
        @SerializedName("holidayStatus")
        private int holidayStatus;
        @SerializedName("id")
        private String id;
        @SerializedName("image")
        private String image;
        @SerializedName("isDisable")
        private boolean isDisable;
        @SerializedName("isSystem")
        private boolean isSystem;
        @SerializedName(MapBundleKey.OfflineMapKey.OFFLINE_CITYNAME)
        private String name;
        @SerializedName(Constants.KEY_PACKAGE_NAME)
        private String packageName;
        @SerializedName("studentId")
        private String studentId;
        @SerializedName("type")
        private int type;
        @SerializedName("weekendEndTime")
        private String weekendEndTime;
        @SerializedName("weekendStartTime")
        private String weekendStartTime;
        @SerializedName("weekendStatus")
        private int weekendStatus;

        public String getBrand() {
            return this.brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getDailyEndTime() {
            return this.dailyEndTime;
        }

        public void setDailyEndTime(String dailyEndTime) {
            this.dailyEndTime = dailyEndTime;
        }

        public String getDailyStartTime() {
            return this.dailyStartTime;
        }

        public void setDailyStartTime(String dailyStartTime) {
            this.dailyStartTime = dailyStartTime;
        }

        public int getDailyStatus() {
            return this.dailyStatus;
        }

        public void setDailyStatus(int dailyStatus) {
            this.dailyStatus = dailyStatus;
        }

        public String getHolidayEndTime() {
            return this.holidayEndTime;
        }

        public void setHolidayEndTime(String holidayEndTime) {
            this.holidayEndTime = holidayEndTime;
        }

        public String getHolidayStartTime() {
            return this.holidayStartTime;
        }

        public void setHolidayStartTime(String holidayStartTime) {
            this.holidayStartTime = holidayStartTime;
        }

        public int getHolidayStatus() {
            return this.holidayStatus;
        }

        public void setHolidayStatus(int holidayStatus) {
            this.holidayStatus = holidayStatus;
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

        public boolean isIsDisable() {
            return this.isDisable;
        }

        public void setIsDisable(boolean isDisable) {
            this.isDisable = isDisable;
        }

        public boolean isIsSystem() {
            return this.isSystem;
        }

        public void setIsSystem(boolean isSystem) {
            this.isSystem = isSystem;
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

        public String getStudentId() {
            return this.studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getWeekendEndTime() {
            return this.weekendEndTime;
        }

        public void setWeekendEndTime(String weekendEndTime) {
            this.weekendEndTime = weekendEndTime;
        }

        public String getWeekendStartTime() {
            return this.weekendStartTime;
        }

        public void setWeekendStartTime(String weekendStartTime) {
            this.weekendStartTime = weekendStartTime;
        }

        public int getWeekendStatus() {
            return this.weekendStatus;
        }

        public void setWeekendStatus(int weekendStatus) {
            this.weekendStatus = weekendStatus;
        }
    }
}
