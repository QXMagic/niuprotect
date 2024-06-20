package com.niu.protect.model;

import android.util.Log;
import com.niu.protect.tools.Tools;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
public class TeacherModelInfo {
    String afternoonEndTime;
    String afternoonStartTime;
    String friAfternoonBeginTime;
    String friAfternoonEndTime;
    String friMorningBeginTime;
    String friMorningEndTime;
    String friNightBeginTime;
    String friNightEndTime;
    String monAfternoonBeginTime;
    String monAfternoonEndTime;
    String monMorningBeginTime;
    String monMorningEndTime;
    String monNightBeginTime;
    String monNightEndTime;
    String morningEndTime;
    String morningStartTime;
    String nightEndTime;
    String nightStartTime;
    String satAfternoonBeginTime;
    String satAfternoonEndTime;
    String satMorningBeginTime;
    String satMorningEndTime;
    String satNightBeginTime;
    String satNightEndTime;
    String sunAfternoonBeginTime;
    String sunAfternoonEndTime;
    String sunMorningBeginTime;
    String sunMorningEndTime;
    String sunNightBeginTime;
    String sunNightEndTime;
    String thursAfternoonBeginTime;
    String thursAfternoonEndTime;
    String thursMorningBeginTime;
    String thursMorningEndTime;
    String thursNightBeginTime;
    String thursNightEndTime;
    String tuesAfternoonBeginTime;
    String tuesAfternoonEndTime;
    String tuesMorningBeginTime;
    String tuesMorningEndTime;
    String tuesNightBeginTime;
    String tuesNightEndTime;
    String wedAfternoonBeginTime;
    String wedAfternoonEndTime;
    String wedMorningBeginTime;
    String wedMorningEndTime;
    String wedNightBeginTime;
    String wedNightEndTime;
    String week;

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
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

    public String getMonMorningBeginTime() {
        return this.monMorningBeginTime;
    }

    public void setMonMorningBeginTime(String monMorningBeginTime) {
        this.monMorningBeginTime = monMorningBeginTime;
    }

    public String getMonMorningEndTime() {
        return this.monMorningEndTime;
    }

    public void setMonMorningEndTime(String monMorningEndTime) {
        this.monMorningEndTime = monMorningEndTime;
    }

    public String getMonAfternoonBeginTime() {
        return this.monAfternoonBeginTime;
    }

    public void setMonAfternoonBeginTime(String monAfternoonBeginTime) {
        this.monAfternoonBeginTime = monAfternoonBeginTime;
    }

    public String getMonAfternoonEndTime() {
        return this.monAfternoonEndTime;
    }

    public void setMonAfternoonEndTime(String monAfternoonEndTime) {
        this.monAfternoonEndTime = monAfternoonEndTime;
    }

    public String getMonNightBeginTime() {
        return this.monNightBeginTime;
    }

    public void setMonNightBeginTime(String monNightBeginTime) {
        this.monNightBeginTime = monNightBeginTime;
    }

    public String getMonNightEndTime() {
        return this.monNightEndTime;
    }

    public void setMonNightEndTime(String monNightEndTime) {
        this.monNightEndTime = monNightEndTime;
    }

    public String getTuesMorningBeginTime() {
        return this.tuesMorningBeginTime;
    }

    public void setTuesMorningBeginTime(String tuesMorningBeginTime) {
        this.tuesMorningBeginTime = tuesMorningBeginTime;
    }

    public String getTuesMorningEndTime() {
        return this.tuesMorningEndTime;
    }

    public void setTuesMorningEndTime(String tuesMorningEndTime) {
        this.tuesMorningEndTime = tuesMorningEndTime;
    }

    public String getTuesAfternoonBeginTime() {
        return this.tuesAfternoonBeginTime;
    }

    public void setTuesAfternoonBeginTime(String tuesAfternoonBeginTime) {
        this.tuesAfternoonBeginTime = tuesAfternoonBeginTime;
    }

    public String getTuesAfternoonEndTime() {
        return this.tuesAfternoonEndTime;
    }

    public void setTuesAfternoonEndTime(String tuesAfternoonEndTime) {
        this.tuesAfternoonEndTime = tuesAfternoonEndTime;
    }

    public String getTuesNightBeginTime() {
        return this.tuesNightBeginTime;
    }

    public void setTuesNightBeginTime(String tuesNightBeginTime) {
        this.tuesNightBeginTime = tuesNightBeginTime;
    }

    public String getTuesNightEndTime() {
        return this.tuesNightEndTime;
    }

    public void setTuesNightEndTime(String tuesNightEndTime) {
        this.tuesNightEndTime = tuesNightEndTime;
    }

    public String getWedMorningBeginTime() {
        return this.wedMorningBeginTime;
    }

    public void setWedMorningBeginTime(String wedMorningBeginTime) {
        this.wedMorningBeginTime = wedMorningBeginTime;
    }

    public String getWedMorningEndTime() {
        return this.wedMorningEndTime;
    }

    public void setWedMorningEndTime(String wedMorningEndTime) {
        this.wedMorningEndTime = wedMorningEndTime;
    }

    public String getWedAfternoonBeginTime() {
        return this.wedAfternoonBeginTime;
    }

    public void setWedAfternoonBeginTime(String wedAfternoonBeginTime) {
        this.wedAfternoonBeginTime = wedAfternoonBeginTime;
    }

    public String getWedAfternoonEndTime() {
        return this.wedAfternoonEndTime;
    }

    public void setWedAfternoonEndTime(String wedAfternoonEndTime) {
        this.wedAfternoonEndTime = wedAfternoonEndTime;
    }

    public String getWedNightBeginTime() {
        return this.wedNightBeginTime;
    }

    public void setWedNightBeginTime(String wedNightBeginTime) {
        this.wedNightBeginTime = wedNightBeginTime;
    }

    public String getWedNightEndTime() {
        return this.wedNightEndTime;
    }

    public void setWedNightEndTime(String wedNightEndTime) {
        this.wedNightEndTime = wedNightEndTime;
    }

    public String getThursMorningBeginTime() {
        return this.thursMorningBeginTime;
    }

    public void setThursMorningBeginTime(String thursMorningBeginTime) {
        this.thursMorningBeginTime = thursMorningBeginTime;
    }

    public String getThursMorningEndTime() {
        return this.thursMorningEndTime;
    }

    public void setThursMorningEndTime(String thursMorningEndTime) {
        this.thursMorningEndTime = thursMorningEndTime;
    }

    public String getThursAfternoonBeginTime() {
        return this.thursAfternoonBeginTime;
    }

    public void setThursAfternoonBeginTime(String thursAfternoonBeginTime) {
        this.thursAfternoonBeginTime = thursAfternoonBeginTime;
    }

    public String getThursAfternoonEndTime() {
        return this.thursAfternoonEndTime;
    }

    public void setThursAfternoonEndTime(String thursAfternoonEndTime) {
        this.thursAfternoonEndTime = thursAfternoonEndTime;
    }

    public String getThursNightBeginTime() {
        return this.thursNightBeginTime;
    }

    public void setThursNightBeginTime(String thursNightBeginTime) {
        this.thursNightBeginTime = thursNightBeginTime;
    }

    public String getThursNightEndTime() {
        return this.thursNightEndTime;
    }

    public void setThursNightEndTime(String thursNightEndTime) {
        this.thursNightEndTime = thursNightEndTime;
    }

    public String getFriMorningBeginTime() {
        return this.friMorningBeginTime;
    }

    public void setFriMorningBeginTime(String friMorningBeginTime) {
        this.friMorningBeginTime = friMorningBeginTime;
    }

    public String getFriMorningEndTime() {
        return this.friMorningEndTime;
    }

    public void setFriMorningEndTime(String friMorningEndTime) {
        this.friMorningEndTime = friMorningEndTime;
    }

    public String getFriAfternoonBeginTime() {
        return this.friAfternoonBeginTime;
    }

    public void setFriAfternoonBeginTime(String friAfternoonBeginTime) {
        this.friAfternoonBeginTime = friAfternoonBeginTime;
    }

    public String getFriAfternoonEndTime() {
        return this.friAfternoonEndTime;
    }

    public void setFriAfternoonEndTime(String friAfternoonEndTime) {
        this.friAfternoonEndTime = friAfternoonEndTime;
    }

    public String getFriNightBeginTime() {
        return this.friNightBeginTime;
    }

    public void setFriNightBeginTime(String friNightBeginTime) {
        this.friNightBeginTime = friNightBeginTime;
    }

    public String getFriNightEndTime() {
        return this.friNightEndTime;
    }

    public void setFriNightEndTime(String friNightEndTime) {
        this.friNightEndTime = friNightEndTime;
    }

    public String getSatMorningBeginTime() {
        return this.satMorningBeginTime;
    }

    public void setSatMorningBeginTime(String satMorningBeginTime) {
        this.satMorningBeginTime = satMorningBeginTime;
    }

    public String getSatMorningEndTime() {
        return this.satMorningEndTime;
    }

    public void setSatMorningEndTime(String satMorningEndTime) {
        this.satMorningEndTime = satMorningEndTime;
    }

    public String getSatAfternoonBeginTime() {
        return this.satAfternoonBeginTime;
    }

    public void setSatAfternoonBeginTime(String satAfternoonBeginTime) {
        this.satAfternoonBeginTime = satAfternoonBeginTime;
    }

    public String getSatAfternoonEndTime() {
        return this.satAfternoonEndTime;
    }

    public void setSatAfternoonEndTime(String satAfternoonEndTime) {
        this.satAfternoonEndTime = satAfternoonEndTime;
    }

    public String getSatNightBeginTime() {
        return this.satNightBeginTime;
    }

    public void setSatNightBeginTime(String satNightBeginTime) {
        this.satNightBeginTime = satNightBeginTime;
    }

    public String getSatNightEndTime() {
        return this.satNightEndTime;
    }

    public void setSatNightEndTime(String satNightEndTime) {
        this.satNightEndTime = satNightEndTime;
    }

    public String getSunMorningBeginTime() {
        return this.sunMorningBeginTime;
    }

    public void setSunMorningBeginTime(String sunMorningBeginTime) {
        this.sunMorningBeginTime = sunMorningBeginTime;
    }

    public String getSunMorningEndTime() {
        return this.sunMorningEndTime;
    }

    public void setSunMorningEndTime(String sunMorningEndTime) {
        this.sunMorningEndTime = sunMorningEndTime;
    }

    public String getSunAfternoonBeginTime() {
        return this.sunAfternoonBeginTime;
    }

    public void setSunAfternoonBeginTime(String sunAfternoonBeginTime) {
        this.sunAfternoonBeginTime = sunAfternoonBeginTime;
    }

    public String getSunAfternoonEndTime() {
        return this.sunAfternoonEndTime;
    }

    public void setSunAfternoonEndTime(String sunAfternoonEndTime) {
        this.sunAfternoonEndTime = sunAfternoonEndTime;
    }

    public String getSunNightBeginTime() {
        return this.sunNightBeginTime;
    }

    public void setSunNightBeginTime(String sunNightBeginTime) {
        this.sunNightBeginTime = sunNightBeginTime;
    }

    public String getSunNightEndTime() {
        return this.sunNightEndTime;
    }

    public void setSunNightEndTime(String sunNightEndTime) {
        this.sunNightEndTime = sunNightEndTime;
    }

    public boolean isweekend() {
        if (!Tools.objIsNullStr(this.week)) {
            try {
                String replace = this.week.replace("\\", "");
                this.week = replace;
                this.week = replace.replace("\"", "");
                JSONArray array = new JSONArray(this.week);
                Calendar calendar = Calendar.getInstance();
                int wd = calendar.get(7) - 1;
                if (wd == 0) {
                    wd = 7;
                }
                for (int i = 0; i < array.length(); i++) {
                    int wdc = array.getInt(i);
                    if (wdc == wd) {
                        return false;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public long makeData(String timeStr) {
        if (Tools.objIsNullStr(timeStr)) {
            timeStr = "00:00";
        }
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

    public boolean checkNowTime() {
        Calendar calendar = Calendar.getInstance();
        int wd = calendar.get(7) - 1;
        if (wd == 0) {
            wd = 7;
        }
        Log.e("xxxx==x=x=", wd + ":=====");
        if (wd == 1) {
            this.morningStartTime = this.monMorningBeginTime;
            this.morningEndTime = this.monMorningEndTime;
            this.afternoonStartTime = this.monAfternoonBeginTime;
            this.afternoonEndTime = this.monAfternoonEndTime;
            this.nightStartTime = this.monNightBeginTime;
            this.nightEndTime = this.monNightEndTime;
        } else if (wd == 2) {
            this.morningStartTime = this.tuesMorningBeginTime;
            this.morningEndTime = this.tuesMorningEndTime;
            this.afternoonStartTime = this.tuesAfternoonBeginTime;
            this.afternoonEndTime = this.tuesAfternoonEndTime;
            this.nightStartTime = this.tuesNightBeginTime;
            this.nightEndTime = this.tuesNightEndTime;
        } else if (wd == 3) {
            this.morningStartTime = this.wedMorningBeginTime;
            this.morningEndTime = this.wedMorningEndTime;
            this.afternoonStartTime = this.wedAfternoonBeginTime;
            this.afternoonEndTime = this.wedAfternoonEndTime;
            this.nightStartTime = this.wedNightBeginTime;
            this.nightEndTime = this.wedNightEndTime;
        } else if (wd == 4) {
            this.morningStartTime = this.thursMorningBeginTime;
            this.morningEndTime = this.thursMorningEndTime;
            this.afternoonStartTime = this.thursAfternoonBeginTime;
            this.afternoonEndTime = this.thursAfternoonEndTime;
            this.nightStartTime = this.thursNightBeginTime;
            this.nightEndTime = this.thursNightEndTime;
        } else if (wd == 5) {
            this.morningStartTime = this.friMorningBeginTime;
            this.morningEndTime = this.friMorningEndTime;
            this.afternoonStartTime = this.friAfternoonBeginTime;
            this.afternoonEndTime = this.friAfternoonEndTime;
            this.nightStartTime = this.friNightBeginTime;
            this.nightEndTime = this.friNightEndTime;
        } else if (wd == 6) {
            this.morningStartTime = this.satMorningBeginTime;
            this.morningEndTime = this.satMorningEndTime;
            this.afternoonStartTime = this.satAfternoonBeginTime;
            this.afternoonEndTime = this.satAfternoonEndTime;
            this.nightStartTime = this.satNightBeginTime;
            this.nightEndTime = this.satNightEndTime;
        } else if (wd == 7) {
            this.morningStartTime = this.sunMorningBeginTime;
            this.morningEndTime = this.sunMorningEndTime;
            this.afternoonStartTime = this.sunAfternoonBeginTime;
            this.afternoonEndTime = this.sunAfternoonEndTime;
            this.nightStartTime = this.sunNightBeginTime;
            this.nightEndTime = this.sunNightEndTime;
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
}
