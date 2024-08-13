package accessibility.lib;

import androidx.core.app.NotificationCompat;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.List;

import accessibility.IPackageName;

public class AppState extends LitePalSupport implements IPackageName {
    private long allowTime;
    private int appType;
    private long leftTime;
    private long openTime;
    private String packageName;
    private long rewardSetTime;
    private long rewardTime;
    private int status;

    @Column(ignore = true)
    private List<TimeSelectBean> time;

    public long getAllowTime() {
        return this.allowTime * 60 * 1000;
    }

    public long getAllowTimeNoCover() {
        return this.allowTime;
    }

    public int getAppType() {
        return this.appType;
    }

    @Override // org.litepal.crud.LitePalSupport
    public long getBaseObjId() {
        return super.getBaseObjId();
    }

    public long getLeftTime() {
        return this.leftTime;
    }

    public long getOpenTime() {
        long j = this.openTime;
        return j == 0 ? System.currentTimeMillis() : j;
    }

    @Override // p469.InterfaceC4191
    public String getPackageName() {
        return this.packageName;
    }

    public long getRewardSetTime() {
        return this.rewardSetTime;
    }

    public long getRewardTime() {
        return this.rewardTime;
    }

    public int getStatus() {
        return this.status;
    }

    public List<TimeSelectBean> getTime() {
        return this.time;
    }

    public void setAllowTime(long j) {
        this.allowTime = j;
        if (j == 0) {
            setToDefault("allowTime");
        }
    }

    public void setAppType(int i) {
        this.appType = i;
        if (i == 0) {
            setToDefault("appType");
        }
    }

    public void setLeftTime(long j) {
        this.leftTime = j;
        if (j == 0) {
            setToDefault("leftTime");
        }
    }

    public void setOpenTime(long j) {
        this.openTime = j;
        if (j == 0) {
            setToDefault("openTime");
        }
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setRewardSetTime(long j) {
        this.rewardSetTime = j;
        if (j == 0) {
            setToDefault("rewardSetTime");
        }
    }

    public void setRewardTime(long j) {
        this.rewardTime = j;
        if (j == 0) {
            setToDefault("rewardTime");
        }
    }

    public void setStatus(int i) {
        this.status = i;
        if (i == 0) {
            setToDefault(NotificationCompat.CATEGORY_STATUS);
        }
    }

    public void setTime(List<TimeSelectBean> list) {
        this.time = list;
    }
}
