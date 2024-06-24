package com.niu.protect.websocket

import com.google.gson.annotations.SerializedName

class SendUserOperationEvent(
    @field:SerializedName("eventName") var eventName: String,
    @field:SerializedName( "eventTime" ) var eventTime: Long,
    @field:SerializedName("eventType") var eventType: String,
    @field:SerializedName( "memberId" ) var memberId: String,
    @field:SerializedName("packageName") var packageName: String
)