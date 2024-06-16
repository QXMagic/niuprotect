package com.niuniu.babyprotect.model;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.google.gson.annotations.SerializedName;
import com.taobao.accs.common.Constants;
import java.util.List;
public class UserBlackAppInfoModel extends BaseModel {
    @SerializedName("data")
    private List<DataDTO> data;

    public List<DataDTO> getData() {
        return this.data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("appImage")
        private String appImage;
        @SerializedName(DispatchConstants.APP_NAME)
        private String appName;
        @SerializedName("createdBy")
        private String createdBy;
        @SerializedName("createdDate")
        private String createdDate;
        @SerializedName("deleted")
        private boolean deleted;
        @SerializedName("id")
        private String id;
        @SerializedName("isBlacklist")
        private boolean isBlacklist;
        @SerializedName("lastModifiedBy")
        private String lastModifiedBy;
        @SerializedName("lastModifiedDate")
        private String lastModifiedDate;
        @SerializedName(Constants.KEY_PACKAGE_NAME)
        private String packageName;
        @SerializedName("parentId")
        private String parentId;
        @SerializedName("remark")
        private String remark;
        @SerializedName("studentId")
        private String studentId;

        public String getAppImage() {
            return this.appImage;
        }

        public void setAppImage(String appImage) {
            this.appImage = appImage;
        }

        public String getAppName() {
            return this.appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
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

        public boolean isDeleted() {
            return this.deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsBlacklist() {
            return this.isBlacklist;
        }

        public void setIsBlacklist(boolean isBlacklist) {
            this.isBlacklist = isBlacklist;
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

        public String getPackageName() {
            return this.packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getParentId() {
            return this.parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStudentId() {
            return this.studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
    }
}
