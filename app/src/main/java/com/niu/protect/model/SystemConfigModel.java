package com.niu.protect.model;

import com.google.gson.annotations.SerializedName;
public class SystemConfigModel extends BaseModel {
    @SerializedName("data")
    private DataDTO data;

    public DataDTO getData() {
        return this.data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("appDownChannel")
        private String appDownChannel;
        @SerializedName("appType")
        private int appType;
        @SerializedName("createdBy")
        private String createdBy;
        @SerializedName("createdDate")
        private String createdDate;
        @SerializedName("deleted")
        private boolean deleted;
        @SerializedName("id")
        private String id;
        @SerializedName("isPass")
        private int isPass;
        @SerializedName("lastModifiedBy")
        private String lastModifiedBy;
        @SerializedName("lastModifiedDate")
        private String lastModifiedDate;
        @SerializedName("versionCode")
        private String versionCode;
        @SerializedName("versionName")
        private String versionName;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isDeleted() {
            return this.deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getCreatedBy() {
            return this.createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return this.createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getLastModifiedBy() {
            return this.lastModifiedBy;
        }

        public void setLastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
        }

        public String getLastModifiedDate() {
            return this.lastModifiedDate;
        }

        public void setLastModifiedDate(String lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public int getAppType() {
            return this.appType;
        }

        public void setAppType(int appType) {
            this.appType = appType;
        }

        public String getAppDownChannel() {
            return this.appDownChannel;
        }

        public void setAppDownChannel(String appDownChannel) {
            this.appDownChannel = appDownChannel;
        }

        public String getVersionName() {
            return this.versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionCode() {
            return this.versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public int getIsPass() {
            return this.isPass;
        }

        public void setIsPass(int isPass) {
            this.isPass = isPass;
        }
    }
}
