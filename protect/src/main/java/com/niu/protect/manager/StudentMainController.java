package com.niu.protect.manager;

import android.content.Context;
import android.text.TextUtils;
import com.niu.protect.BabyApplication;
import com.niu.protect.model.ParentLimiteTimeModel;
import com.niu.protect.model.ParentLimteTimeByAppModel;
import com.niu.protect.model.PatternTimeScopesDTO;
import com.niu.protect.model.TeacherLimiteTimeModel;
import com.niu.protect.model.UserInfo;
import com.niu.protect.repository.StudentControllerRepository;
import com.niu.protect.tools.ILog;
import java.util.Calendar;
import java.util.List;


public class StudentMainController {
    private static StudentMainController instance;
    List<ParentLimteTimeByAppModel.DataDTO> parentLimitTimesByApps;
    List<PatternTimeScopesDTO> patternTimeScopesDTOS;
    List<PatternTimeScopesDTO> ptsParentSchools;
    List<PatternTimeScopesDTO> ptsTeacherSchools;
    boolean controlModelChange = false;
    boolean parentSettingAppChange = false;
    int remenberDayOfWeek = -1;
    int controlModel = -1;
    OnRequstResultCallBack onResultBack = new OnRequstResultCallBack() {
        @Override
        public void requestTeacherResult() {
            clearData();
        }

        @Override
        public void requestParentHolidayResult() {
            clearData();
        }

        @Override
        public void requestParentSchoolResult() {
            clearData();
        }

        @Override
        public void requestOtherTimeResult() {
            clearData();
        }
    };

    private StudentMainController() {
    }

    public static StudentMainController getInstance() {
        if (instance == null) {
            synchronized (StudentMainController.class) {
                if (instance == null) {
                    instance = new StudentMainController();
                }
            }
        }
        return instance;
    }

    public boolean checkNotUseTime(Context context) {
        return checkInWeekControlTime(context);
    }

    public boolean appCanUse(String packageName) {
        return checkParentSettingAPPUseTime(packageName);
    }

    public void setParentSettingAppChange(boolean parentSettingAppChange) {
        this.parentSettingAppChange = parentSettingAppChange;
    }

    private boolean checkInWeekControlTime(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(7) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        if (dayOfWeek != this.remenberDayOfWeek) {
            this.controlModelChange = true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("patternTimeScopesDTOS");
        sb.append(this.patternTimeScopesDTOS == null);
        sb.append("");
        ILog.d("checkControlTime :", sb.toString());
        ILog.d("checkControlTime :", "controlModelChange" + this.controlModelChange + "");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("controlModelChange total ==");
        sb2.append(this.patternTimeScopesDTOS == null || this.controlModelChange);
        ILog.d("checkControlTime :", sb2.toString());
        if (this.patternTimeScopesDTOS == null || this.controlModelChange) {
            int controllerWay = getControllerWay(context);
            this.controlModel = controllerWay;
            checkWeekControlTime(controllerWay);
            ILog.d("controltime", "reload---------controlModel-" + this.controlModel);
        }
        if (this.controlModel == 5) {
            List<PatternTimeScopesDTO> list = this.ptsTeacherSchools;
            if (list == null || list.size() <= 0 || !checkPatternTimeCanNotUse(this.ptsTeacherSchools, calendar, dayOfWeek)) {
                List<PatternTimeScopesDTO> list2 = this.ptsParentSchools;
                return list2 != null && list2.size() > 0 && checkPatternTimeCanNotUse(this.ptsParentSchools, calendar, dayOfWeek);
            }
            return true;
        }
        return checkPatternTimeCanNotUse(this.patternTimeScopesDTOS, calendar, dayOfWeek);
    }

    private boolean checkPatternTimeCanNotUse(List<PatternTimeScopesDTO> patternTimeScops, Calendar calendar, int dayOfWeek) {
        if (patternTimeScops != null && patternTimeScops.size() > 0) {
            int currentHour = calendar.get(11);
            int currentMinute = calendar.get(12);
            for (int j = 0; j < patternTimeScops.size(); j++) {
                PatternTimeScopesDTO ptsd = patternTimeScops.get(j);
                String startTime = ptsd.getStartTime();
                String endTime = ptsd.getEndTime();
                ILog.d("controltime", "startTime:" + startTime + "-----------endtime:" + endTime);
                if (checkControlTime(currentHour, currentMinute, startTime, endTime)) {
                    ILog.d("control time", "-------true---");
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean checkControlTime(int currentHour, int currentMinute, String startTime, String endTime) {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            String[] startHours = startTime.split(Constants.COLON_SEPARATOR);
            int startHour = Integer.valueOf(startHours[0]).intValue();
            int startMinute = Integer.valueOf(startHours[1]).intValue();
            String[] endHours = endTime.split(Constants.COLON_SEPARATOR);
            int endHour = Integer.valueOf(endHours[0]).intValue();
            int endMinute = Integer.valueOf(endHours[1]).intValue();
            int currentTotalTime = (currentHour * 60) + currentMinute;
            int limitTotalTimeStart = (startHour * 60) + startMinute;
            int limitEndTimes = (endHour * 60) + endMinute;
            ILog.d("limitTime", "currentTime=" + currentTotalTime + "--limitTotalTimeStart:" + limitTotalTimeStart + "---limitEndTimes:" + limitEndTimes);
            return currentTotalTime <= limitEndTimes && currentTotalTime >= limitTotalTimeStart;
        }
        return false;
    }

    private boolean checkParentSettingAPPUseTime(String packageName) {
        ParentLimteTimeByAppModel mParentLimteTimeByAppModel;
        if (this.parentLimitTimesByApps == null && (mParentLimteTimeByAppModel = StudentControlTimeLimitManager.getInstance().getmParentLimteTimeByAppModel()) != null) {
            this.parentLimitTimesByApps = mParentLimteTimeByAppModel.getData();
        }
        List<ParentLimteTimeByAppModel.DataDTO> list = this.parentLimitTimesByApps;
        if (list != null && list.size() > 0) {
            int size = this.parentLimitTimesByApps.size();
            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(11);
            int currentMinute = calendar.get(12);
            for (int i = 0; i < size; i++) {
                ParentLimteTimeByAppModel.DataDTO dataDTO = this.parentLimitTimesByApps.get(i);
                String mPackageName = dataDTO.getPackageName();
                if (mPackageName.equals(packageName)) {
                    String startTime = dataDTO.getDailyStartTime();
                    String endTime = dataDTO.getDailyEndTime();
                    if (this.controlModel == 2) {
                        startTime = dataDTO.getHolidayStartTime();
                        endTime = dataDTO.getHolidayEndTime();
                        int holidayStatus = dataDTO.getHolidayStatus();
                        if (holidayStatus == 1) {
                            return true;
                        }
                        if (holidayStatus == 2) {
                            return false;
                        }
                    } else {
                        int dailyStatus = dataDTO.getDailyStatus();
                        if (dailyStatus == 1) {
                            return true;
                        }
                        if (dailyStatus == 2) {
                            return false;
                        }
                    }
                    if (startTime == null && endTime == null) {
                        return false;
                    }
                    ILog.d("checkParentSettingAPPUseTime---", packageName + "startTime:" + startTime + "----------endTime----" + endTime);
                    return !checkControlTime(currentHour, currentMinute, startTime, endTime);
                }
            }
        }
        return false;
    }

    public void getBlackApp() {
    }

    private void getSystemBlackApp() {
        SystemBlackAppListManager.getInstance().requestSystemBlacklist(BabyApplication.getInstance());
    }

    private void getSystemWhiteApp() {
        SystemWhiteAppListManager.getInstance().requestSystemWhitelist(BabyApplication.getInstance());
    }

    private void getUserBlackApp() {
        UserBlackAppListManager.getInstance().reqeustUserBlacklist(BabyApplication.getInstance());
    }

    public void getUserWhiteApp() {
        UserWhiteAppListManager.getInstance().reqeustUserWhitelist(BabyApplication.getInstance());
    }

    public void getXcxControl() {
        TempOutControlManager.getInstance().requestOutControl(BabyApplication.getInstance());
    }

    private void requestParentSettingAppLimit() {
        StudentControllerRepository.getInstance().requestOtherTimeModel(BabyApplication.getInstance(), this.onResultBack);
    }

    private int getControllerWay(Context context) {
        int controllerWay = -1;
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(context);
        if (userInfo == null) {
            return -1;
        }
        boolean isVip = VipCheckManager.checkVip(context, false);
        boolean bindParent = userInfo.isBindParent();
        boolean bindTeacher = userInfo.isBindTeacher();
        if (!bindParent && !bindTeacher) {
            UserProtectManager.getInstance().setProtect(-2);
        } else {
            int parentControllerMode = userInfo.getParentPattern();
            ILog.d("parentControllerModel--", parentControllerMode + "");
            if (parentControllerMode == 1) {
                if (bindTeacher && !bindParent) {
                    controllerWay = 3;
                } else if (!bindTeacher && bindParent) {
                    controllerWay = 1;
                } else if (bindParent && bindTeacher) {
                    controllerWay = isVip ? 5 : 3;
                }
            } else if (parentControllerMode == 2) {
                if (!bindParent && bindTeacher) {
                    controllerWay = 4;
                } else if (bindParent) {
                    controllerWay = 2;
                }
            }
            if (isVip) {
                UserProtectManager.getInstance().setProtect(1);
            } else {
                UserProtectManager.getInstance().setProtect(-1);
            }
        }
        return controllerWay;
    }

    public void requstWeekControlTime(Context context) {
        this.controlModel = getControllerWay(context);
        ILog.d("--controlModel----------", this.controlModel + "");
        int i = this.controlModel;
        if (i == 1) {
            executePrentOfSchool();
        } else if (i == 2) {
            executePrentOfHoliday();
        } else if (i == 3) {
            executeTeacherOrder();
        } else if (i == 5) {
            executePrentOfSchool();
            executeTeacherOrder();
        }
        requestParentSettingAppLimit();
    }

    private void executeTeacherOrder() {
        StudentControllerRepository.getInstance().requestTeacherModel(BabyApplication.getInstance(), this.onResultBack);
    }

    private void executePrentOfHoliday() {
        StudentControllerRepository.getInstance().requestParentHolidayModel(BabyApplication.getInstance(), this.onResultBack);
    }

    private void executePrentOfSchool() {
        StudentControllerRepository.getInstance().requestParentSchoolModel(BabyApplication.getInstance(), this.onResultBack);
    }

    private void checkWeekControlTime(int controlModel) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.patternTimeScopesDTOS == null);
        sb.append("");
        ILog.d("--patternTimeScopesDTOS----------", sb.toString());
        if (controlModel == 1) {
            this.patternTimeScopesDTOS = getParentTodayLimitTime(controlModel);
        } else if (controlModel == 2) {
            this.patternTimeScopesDTOS = getParentTodayLimitTime(controlModel);
        } else if (controlModel == 3) {
            this.patternTimeScopesDTOS = getTeacherTodayLimitTime();
        } else if (controlModel == 5) {
            this.ptsTeacherSchools = getTeacherTodayLimitTime();
            this.ptsParentSchools = getParentTodayLimitTime(1);
        }
    }

    private List<PatternTimeScopesDTO> getParentTodayLimitTime(int type) {
        ParentLimiteTimeModel parentLimiteTimeModel;
        List<ParentLimiteTimeModel.DataDTO.PatternParentSlotsDTO> parentDatas;
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(7) - 1;
        this.remenberDayOfWeek = i;
        if (i == 0) {
            this.remenberDayOfWeek = 7;
        }
        if (type == 1) {
            parentLimiteTimeModel = StudentControlTimeLimitManager.getInstance().getmParentLimiteSchool();
        } else {
            parentLimiteTimeModel = StudentControlTimeLimitManager.getInstance().getParentLimiteHoliday();
        }
        if (parentLimiteTimeModel != null && (parentDatas = parentLimiteTimeModel.getData().getPatternParentSlots()) != null && parentDatas.size() > 0) {
            if (type == 2) {
                List<PatternTimeScopesDTO> patternTimeScopesDTOS = parentDatas.get(0).getPatternTimeScopes();
                if (patternTimeScopesDTOS != null && patternTimeScopesDTOS.size() > 0) {
                    return patternTimeScopesDTOS;
                }
                return null;
            }
            int size = parentDatas.size();
            for (int i2 = 0; i2 < size; i2++) {
                ParentLimiteTimeModel.DataDTO.PatternParentSlotsDTO pt = parentDatas.get(i2);
                int week = pt.getDayTheWeek();
                if (week == this.remenberDayOfWeek) {
                    List<PatternTimeScopesDTO> patternTimeScopesDTOS2 = pt.getPatternTimeScopes();
                    if (patternTimeScopesDTOS2 != null && patternTimeScopesDTOS2.size() > 0) {
                        return patternTimeScopesDTOS2;
                    }
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    private List<PatternTimeScopesDTO> getTeacherTodayLimitTime() {
        List<TeacherLimiteTimeModel.DataDTO.PatternTeacherSlotsDTO> teacherDatas;
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(7) - 1;
        this.remenberDayOfWeek = i;
        if (i == 0) {
            this.remenberDayOfWeek = 7;
        }
        TeacherLimiteTimeModel teacherLimiteTimeModel = StudentControlTimeLimitManager.getInstance().getmTeacherLimiteTimeModel();
        if (teacherLimiteTimeModel != null && (teacherDatas = teacherLimiteTimeModel.getData().getPatternTeacherSlots()) != null && teacherDatas.size() > 0) {
            int size = teacherDatas.size();
            for (int i2 = 0; i2 < size; i2++) {
                TeacherLimiteTimeModel.DataDTO.PatternTeacherSlotsDTO pt = teacherDatas.get(i2);
                int week = pt.getDayTheWeek();
                if (week == this.remenberDayOfWeek) {
                    List<PatternTimeScopesDTO> patternTimeScopesDTOS = pt.getPatternTimeScopes();
                    if (patternTimeScopesDTOS != null && patternTimeScopesDTOS.size() > 0) {
                        return patternTimeScopesDTOS;
                    }
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    public void clearData() {
        StudentControlTimeLimitManager.getInstance().clearData();
        this.parentLimitTimesByApps = null;
        this.patternTimeScopesDTOS = null;
    }

    public void requestMainControl() {
        ILog.d("-----requestMainControl---", "requestMainControl");
        UserInfoManager.getInstance().refreshUserInfo(BabyApplication.getInstance(), () -> requstWeekControlTime(BabyApplication.getInstance()));
    }

    public void requestSystemBlackAndWhiteApp() {
        getBlackApp();
    }
}
