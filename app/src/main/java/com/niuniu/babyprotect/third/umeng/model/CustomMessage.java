package com.niuniu.babyprotect.third.umeng.model;

import com.google.gson.annotations.SerializedName;
import com.umeng.message.entity.UMessage;
import org.android.agoo.common.AgooConstants;
public class CustomMessage {
    @SerializedName(AgooConstants.MESSAGE_BODY)
    private BodyDTO body;
    @SerializedName("display_type")
    private String displayType;
    @SerializedName("msg_id")
    private String msgId;

    public String getDisplayType() {
        return this.displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public BodyDTO getBody() {
        return this.body;
    }

    public void setBody(BodyDTO body) {
        this.body = body;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public static class BodyDTO {
        @SerializedName(UMessage.DISPLAY_TYPE_CUSTOM)
        private String custom;

        public String getCustom() {
            return this.custom;
        }

        public void setCustom(String custom) {
            this.custom = custom;
        }
    }
}
