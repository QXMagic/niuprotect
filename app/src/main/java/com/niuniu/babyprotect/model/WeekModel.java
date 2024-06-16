package com.niuniu.babyprotect.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class WeekModel {
    String dayTheWeek;
    JSONArray patternTimeScopes = new JSONArray();

    public String getDayTheWeek() {
        return this.dayTheWeek;
    }

    public void setDayTheWeek(String dayTheWeek) {
        this.dayTheWeek = dayTheWeek;
    }

    public JSONArray getPatternTimeScopes() {
        return this.patternTimeScopes;
    }

    public void setPatternTimeScopes(JSONArray patternTimeScopes) {
        this.patternTimeScopes = patternTimeScopes;
    }

    public boolean checkOkTime() {
        long nowTime = System.currentTimeMillis();
        for (int i = 0; i < this.patternTimeScopes.length(); i++) {
            try {
                JSONObject object = this.patternTimeScopes.getJSONObject(i);
                String startTime = object.getString("startTime");
                String endTime = object.getString("endTime");
                long startTimeInt = makeData(startTime);
                long endTimeInt = makeData(endTime);
                if (nowTime > startTimeInt && nowTime < endTimeInt) {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean checkNowTime() {
        Calendar calendar = Calendar.getInstance();
        int wd = calendar.get(7) - 1;
        if (wd == 0) {
            wd = 7;
        }
        if (!this.dayTheWeek.equals("0")) {
            String str = this.dayTheWeek;
            if (!str.equals(wd + "")) {
                return false;
            }
        }
        return true;
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
