package com.niu.protect.model

import com.google.gson.annotations.SerializedName

class AppData {

    @SerializedName("appImage")
    private var appImage: String? = null

    @SerializedName("appName")
    private var appName: String? = null

    @SerializedName("createdBy")
    private var createdBy: String? = null

    @SerializedName("createdDate")
    private var createdDate: String? = null

    @SerializedName("deleted")
    private var deleted = false

    @SerializedName("id")
    private var id: String? = null

    @SerializedName("isBlacklist")
    private var isBlacklist = false

    @SerializedName("lastModifiedBy")
    private var lastModifiedBy: String? = null

    @SerializedName("lastModifiedDate")
    private var lastModifiedDate: String? = null

    @SerializedName("packageName")
    private var packageName: String? = null

    @SerializedName("parentId")
    private var parentId: String? = null

    @SerializedName("remark")
    private var remark: String? = null

    @SerializedName("studentId")
    private var studentId: String? = null

    fun getAppImage(): String? {
        return appImage
    }

    fun setAppImage(appImage: String?) {
        this.appImage = appImage
    }

    fun getAppName(): String? {
        return appName
    }

    fun setAppName(appName: String?) {
        this.appName = appName
    }

    fun getCreatedBy(): String? {
        return createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
    }

    fun getCreatedDate(): String? {
        return createdDate
    }

    fun setCreatedDate(createdDate: String?) {
        this.createdDate = createdDate
    }

    fun isDeleted(): Boolean {
        return deleted
    }

    fun setDeleted(deleted: Boolean) {
        this.deleted = deleted
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun isIsBlacklist(): Boolean {
        return isBlacklist
    }

    fun setIsBlacklist(isBlacklist: Boolean) {
        this.isBlacklist = isBlacklist
    }

    fun getLastModifiedBy(): String? {
        return lastModifiedBy
    }

    fun setLastModifiedBy(lastModifiedBy: String?) {
        this.lastModifiedBy = lastModifiedBy
    }

    fun getLastModifiedDate(): String? {
        return lastModifiedDate
    }

    fun setLastModifiedDate(lastModifiedDate: String?) {
        this.lastModifiedDate = lastModifiedDate
    }

    fun getPackageName(): String? {
        return packageName
    }

    fun setPackageName(packageName: String?) {
        this.packageName = packageName
    }

    fun getParentId(): String? {
        return parentId
    }

    fun setParentId(parentId: String?) {
        this.parentId = parentId
    }

    fun getRemark(): String? {
        return remark
    }

    fun setRemark(remark: String?) {
        this.remark = remark
    }

    fun getStudentId(): String? {
        return studentId
    }

    fun setStudentId(studentId: String?) {
        this.studentId = studentId
    }
}