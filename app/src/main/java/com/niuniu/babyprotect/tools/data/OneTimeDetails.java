package com.niuniu.babyprotect.tools.data;

import android.app.usage.UsageEvents;
import java.util.ArrayList;
public class OneTimeDetails {
    private ArrayList<UsageEvents.Event> OneTimeDetailEventList;
    private String pkgName;
    private long useTime;

    public OneTimeDetails(String pkg, long useTime, ArrayList<UsageEvents.Event> oneTimeDetailList) {
        this.pkgName = pkg;
        this.useTime = useTime;
        this.OneTimeDetailEventList = oneTimeDetailList;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public long getUseTime() {
        return this.useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public ArrayList<UsageEvents.Event> getOneTimeDetailEventList() {
        return this.OneTimeDetailEventList;
    }

    public void setOneTimeDetailEventList(ArrayList<UsageEvents.Event> oneTimeDetailEventList) {
        this.OneTimeDetailEventList = oneTimeDetailEventList;
    }

    public String getStartTime() {
        if (this.OneTimeDetailEventList.size() <= 0) {
            return null;
        }
        String startTime = DateTransUtils.stampToDate(this.OneTimeDetailEventList.get(0).getTimeStamp());
        return startTime;
    }

    public String getStopTime() {
        if (this.OneTimeDetailEventList.size() <= 0) {
            return null;
        }
        ArrayList<UsageEvents.Event> arrayList = this.OneTimeDetailEventList;
        String stopTime = DateTransUtils.stampToDate(arrayList.get(arrayList.size() - 1).getTimeStamp());
        return stopTime;
    }
}
