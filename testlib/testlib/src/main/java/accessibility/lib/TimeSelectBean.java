package accessibility.lib;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.Objects;

public class TimeSelectBean extends BaseLitePal implements Comparable<TimeSelectBean>, InterfaceC4330 {
    private String endTime;
    private String parentId;
    private int sourceType;
    private String startTime;

    //    @SerializedName("id")
    private String timeId;

    public TimeSelectBean(String str) {
        this.timeId = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TimeSelectBean timeSelectBean = (TimeSelectBean) obj;
        return ObjectUtils.equals(this.parentId, timeSelectBean.parentId) && ObjectUtils.equals(this.startTime, timeSelectBean.startTime) && ObjectUtils.equals(this.endTime, timeSelectBean.endTime) && ObjectUtils.equals(this.timeId, timeSelectBean.timeId);
    }

    @Override // p512.InterfaceC4330
    public String getEndTime() {
        return this.endTime;
    }

    public String getParentId() {
        return this.parentId;
    }

    public int getSourceType() {
        return this.sourceType;
    }

    @Override // p512.InterfaceC4330
    public String getStarTime() {
        return this.startTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getTimeId() {
        return this.timeId;
    }

    public int hashCode() {
        return Objects.hash(this.startTime, this.endTime, this.parentId, this.timeId);
    }

    @Override // p512.InterfaceC4330
    public boolean isAdd() {
        return ObjectUtils.equals("-1", this.timeId);
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public void setParentId(String str) {
        this.parentId = str;
    }

    public void setSourceType(int i) {
        this.sourceType = i;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public void setTimeId(String str) {
        this.timeId = str;
    }

    public String toString() {
        return "TimeSelectBean{timeId='" + this.timeId + "', startTime='" + this.startTime + "', endTime='" + this.endTime + "', parentId='" + this.parentId + "'}";
    }

    @Override // java.lang.Comparable
    public int compareTo(TimeSelectBean timeSelectBean) {
        return Long.compare(getLitePalId(), timeSelectBean.getLitePalId());
    }

    public TimeSelectBean() {
    }
}
