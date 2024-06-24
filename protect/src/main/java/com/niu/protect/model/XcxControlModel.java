package com.niu.protect.model;

import com.baidu.platform.comapi.map.MapBundleKey;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class XcxControlModel extends BaseModel {
    @SerializedName("data")
    private List<DataDTO> data;

    public List<DataDTO> getData() {
        return this.data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("className")
        private String className;
        @SerializedName("endTime")
        private String endTime;
        @SerializedName("iconUrl")
        private String iconUrl;
        @SerializedName("id")
        private String id;
        @SerializedName(MapBundleKey.OfflineMapKey.OFFLINE_CITYNAME)
        private String name;
        @SerializedName("packageName")
        private String packageName;
        @SerializedName("smallProgramId")
        private String smallProgramId;
        @SerializedName("startTime")
        private String startTime;
        @SerializedName("studentId")
        private String studentId;
        @SerializedName("useStatus")
        private int useStatus;

        public String getClassName() {
            return this.className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getEndTime() {
            return this.endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getIconUrl() {
            return this.iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getSmallProgramId() {
            return this.smallProgramId;
        }

        public void setSmallProgramId(String smallProgramId) {
            this.smallProgramId = smallProgramId;
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

        public int getUseStatus() {
            return this.useStatus;
        }

        public void setUseStatus(int useStatus) {
            this.useStatus = useStatus;
        }
    }
}
