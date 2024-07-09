package accessibility.lib;

public class TimeDbBean extends BaseLitePal {

    //    @SerializedName("id")
    private long appState_id;
    private String endTime;
    private String startTime;

    public long getAppState_id() {
        return this.appState_id;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setAppState_id(long j) {
        this.appState_id = j;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }
}
