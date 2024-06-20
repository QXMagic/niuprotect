package com.niu.protect.model;

import com.google.gson.annotations.SerializedName;
public class TempOutControlModel extends BaseModel {
    @SerializedName("data")
    private DataDTO data;

    public DataDTO getData() {
        return this.data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("createdBy")
        private String createdBy;
        @SerializedName("createdDate")
        private String createdDate;
        @SerializedName("deleted")
        private boolean deleted;
        @SerializedName("endTime")
        private String endTime;
        @SerializedName("id")
        private String id;
        @SerializedName("lastModifiedBy")
        private String lastModifiedBy;
        @SerializedName("lastModifiedDate")
        private String lastModifiedDate;
        @SerializedName("startTime")
        private String startTime;
        @SerializedName("studentId")
        private String studentId;

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

        public String getEndTime() {
            return this.endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getStartTime() {
            return this.startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getStudentId() {
            return this.studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
    }
}
