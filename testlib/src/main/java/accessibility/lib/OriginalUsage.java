package accessibility.lib;

public class OriginalUsage {
    private String className;
    private String date;
    private int eventType;
    private String packageName;
    private long timeStamp;

    public OriginalUsage(String str, String str2, int i, long j) {
        this.packageName = str;
        this.className = str2;
        this.eventType = i;
        this.timeStamp = j;
        this.date = DateUtil.m22603(j, "yyyy-MM-dd HH:mm:ss");
    }

    public String getClassName() {
        return this.className;
    }

    public String getDate() {
        return this.date;
    }

    public int getEventType() {
        return this.eventType;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setClassName(String str) {
        this.className = str;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public void setEventType(int i) {
        this.eventType = i;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setTimeStamp(long j) {
        this.timeStamp = j;
    }
}
