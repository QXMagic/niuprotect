package com.niuniu.babyprotect.model;

import android.util.Log;
import com.niuniu.babyprotect.tools.Tools;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
public class ParentModelInfo {
    String afternoonEndTime;
    String afternoonStartTime;
    String createdBy;
    String createdDate;
    String daily;
    String deleted;
    String id;
    String lastModifiedBy;
    String lastModifiedDate;
    String morningEndTime;
    String morningStartTime;
    String nightEndTime;
    String nightStartTime;
    String studentId;
    String type;
    String weekend;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeleted() {
        return this.deleted;
    }

    public void setDeleted(String deleted) {
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

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDaily() {
        return this.daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getWeekend() {
        return this.weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }

    public String getMorningStartTime() {
        return this.morningStartTime;
    }

    public void setMorningStartTime(String morningStartTime) {
        this.morningStartTime = morningStartTime;
    }

    public String getMorningEndTime() {
        return this.morningEndTime;
    }

    public void setMorningEndTime(String morningEndTime) {
        this.morningEndTime = morningEndTime;
    }

    public String getAfternoonStartTime() {
        return this.afternoonStartTime;
    }

    public void setAfternoonStartTime(String afternoonStartTime) {
        this.afternoonStartTime = afternoonStartTime;
    }

    public String getAfternoonEndTime() {
        return this.afternoonEndTime;
    }

    public void setAfternoonEndTime(String afternoonEndTime) {
        this.afternoonEndTime = afternoonEndTime;
    }

    public String getNightStartTime() {
        return this.nightStartTime;
    }

    public void setNightStartTime(String nightStartTime) {
        this.nightStartTime = nightStartTime;
    }

    public String getNightEndTime() {
        return this.nightEndTime;
    }

    public void setNightEndTime(String nightEndTime) {
        this.nightEndTime = nightEndTime;
    }

    public String JSONTokener(String in) {
        if (in != null && in.startsWith("\ufeff")) {
            return in.substring(1);
        }
        return in;
    }

    public boolean checkOkTime() {
        Log.e("xxxxxx", this.daily);
        if (!Tools.objIsNullStr(this.daily)) {
            String replace = this.daily.replace("\\", "");
            this.daily = replace;
            this.daily = replace.replace("\"", "");
            try {
                JSONArray array = new JSONArray(this.daily);
                Log.e("xxxxxx", "xxxx=======" + array.length());
                Calendar calendar = Calendar.getInstance();
                int wd = calendar.get(7) - 1;
                if (wd == 0) {
                    wd = 7;
                }
                boolean isok = true;
                for (int i = 0; i < array.length(); i++) {
                    int wdc = array.getInt(i);
                    if (wdc == wd) {
                        isok = false;
                        Log.e("xxxxxx", "ffffff");
                    }
                }
                if (isok) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        long nowTime = System.currentTimeMillis();
        long morningStartTimea = makeData(this.morningStartTime);
        long morningEndTimea = makeData(this.morningEndTime);
        long afternoonStartTimea = makeData(this.afternoonStartTime);
        long afternoonEndTimea = makeData(this.afternoonEndTime);
        long nightStartTimea = makeData(this.nightStartTime);
        long nightEndTimea = makeData(this.nightEndTime);
        if (nowTime <= morningStartTimea || nowTime >= morningEndTimea) {
            if (nowTime <= afternoonStartTimea || nowTime >= afternoonEndTimea) {
                return nowTime <= nightStartTimea || nowTime >= nightEndTimea;
            }
            return false;
        }
        return false;
    }

    public boolean checkWork() {
        try {
            JSONArray array = new JSONArray(this.weekend);
            Calendar calendar = Calendar.getInstance();
            int wd = calendar.get(7) - 1;
            if (wd == 0) {
                wd = 7;
            }
            boolean isok = true;
            for (int i = 0; i < array.length(); i++) {
                int wdc = array.getInt(i);
                if (wdc == wd) {
                    isok = false;
                }
            }
            return isok;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long makeData(String timeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr = format.format(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = dataStr + " " + timeStr;
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
