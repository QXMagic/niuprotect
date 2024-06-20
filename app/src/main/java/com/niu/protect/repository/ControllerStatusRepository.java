package com.niu.protect.repository;

import android.content.Context;
import android.text.TextUtils;
import com.niu.protect.manager.UserInfoManager;
import com.niu.protect.model.UserInfo;
public class ControllerStatusRepository extends BaseRepository {
    public static final int TYPE_CONTROLLER = 1;
    public static final int TYPE_CONTROLLER_OUT = 0;
    public static ControllerStatusRepository instance;

    private ControllerStatusRepository() {
    }

    public static ControllerStatusRepository getInstance() {
        if (instance == null) {
            synchronized (ControllerStatusRepository.class) {
                if (instance == null) {
                    instance = new ControllerStatusRepository();
                }
            }
        }
        return instance;
    }

    public void requestControlStatus(Context context, int type) {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(context);
        if (userInfo == null || TextUtils.isEmpty(userInfo.getId())) {
        }
    }
}
