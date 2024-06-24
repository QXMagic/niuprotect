package com.niu.protect.websocket

import com.google.gson.annotations.SerializedName

class ScreenAndBattaryStatusEvent(
    @field:SerializedName(
        "electricQuantity"
    ) var electricQuantity: String,

    @field:SerializedName("screenStatus") var screenStatus: Int,

    @field:SerializedName(
        "memberId"
    ) var memberId: String
)