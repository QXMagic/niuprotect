package com.niu.protect.manager;

import android.text.TextUtils;

import com.niu.protect.network.StudentBaseUrl;
import com.tencent.mmkv.MMKV;

import java.util.UUID;

/**
 * 设备身份管理。
 *
 * device_id：设备级唯一标识，首次启动生成 UUID 并持久化，与家长账号无关
 * （换机重绑、一个家长多设备都不冲突，也是 WebSocket 连接的 uid）。
 * device_token：绑定时服务端下发的设备凭证，用于 WS 连接与解绑校验。
 */
public final class DeviceIdManager {

    private static final String MMKV_ID = "device";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String KEY_DEVICE_TOKEN = "device_token";

    private static volatile DeviceIdManager instance;

    private final MMKV mmkv = MMKV.mmkvWithID(MMKV_ID, MMKV.MULTI_PROCESS_MODE);

    private DeviceIdManager() {
    }

    public static DeviceIdManager getInstance() {
        if (instance == null) {
            synchronized (DeviceIdManager.class) {
                if (instance == null) {
                    instance = new DeviceIdManager();
                }
            }
        }
        return instance;
    }

    /** 获取设备 ID，不存在则生成并持久化。 */
    public String getDeviceId() {
        String id = mmkv.decodeString(KEY_DEVICE_ID, "");
        if (TextUtils.isEmpty(id)) {
            id = UUID.randomUUID().toString().replace("-", "");
            mmkv.encode(KEY_DEVICE_ID, id);
        }
        return id;
    }

    public String getDeviceToken() {
        return mmkv.decodeString(KEY_DEVICE_TOKEN, "");
    }

    public void saveDeviceToken(String token) {
        mmkv.encode(KEY_DEVICE_TOKEN, token == null ? "" : token);
    }

    /** 是否已完成绑定（持有设备凭证）。 */
    public boolean isBound() {
        return !TextUtils.isEmpty(getDeviceToken());
    }

    /** 解绑：清除本地凭证（device_id 保留，重绑仍是同一设备）。 */
    public void clearBinding() {
        mmkv.removeValueForKey(KEY_DEVICE_TOKEN);
    }

    /**
     * 构造 WebSocket 连接地址：wss://…/monitor/&lt;device_id&gt;?t=&lt;device_token&gt;
     * python 服务端据此校验设备凭证。
     */
    public String buildWebSocketUri() {
        return StudentBaseUrl.WEBSOCKET_URI + getDeviceId() + "?t=" + getDeviceToken();
    }
}
