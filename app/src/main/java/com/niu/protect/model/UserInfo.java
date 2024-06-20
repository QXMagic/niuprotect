package com.niu.protect.model;

import java.io.Serializable;
public class UserInfo implements Serializable {
    String city;
    String cityId;
    String companyId;
    String deviceStatus;
    String district;
    String districtId;
    String expireDate;
    long expireTimeStamp;
    String grade;
    String id;
    String imageUrl;
    boolean isBindParent;
    boolean isBindTeacher;
    String latitude;
    String longitude;
    String memberType;
    String mobile;
    String mobileBrand;
    String mobileModel;
    String name;
    String nickName;
    String organizationId;
    int parentPattern;
    String payFee;
    String province;
    String provinceId;
    String qrCode;
    String registerDate;
    int sex;
    int subIconSwitch;
    String systemVersion;
    String teacherPattern;
    String teacherPatternId;
    String token;
    String username;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSubIconSwitch() {
        return this.subIconSwitch;
    }

    public void setSubIconSwitch(int subIconSwitch) {
        this.subIconSwitch = subIconSwitch;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMemberType() {
        return this.memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDeviceStatus() {
        return this.deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getSystemVersion() {
        return this.systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getMobileBrand() {
        return this.mobileBrand;
    }

    public void setMobileBrand(String mobileBrand) {
        this.mobileBrand = mobileBrand;
    }

    public String getMobileModel() {
        return this.mobileModel;
    }

    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel;
    }

    public String getRegisterDate() {
        return this.registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getPayFee() {
        return this.payFee;
    }

    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }

    public String getTeacherPattern() {
        return this.teacherPattern;
    }

    public void setTeacherPattern(String teacherPattern) {
        this.teacherPattern = teacherPattern;
    }

    public String getTeacherPatternId() {
        return this.teacherPatternId;
    }

    public void setTeacherPatternId(String teacherPatternId) {
        this.teacherPatternId = teacherPatternId;
    }

    public int getParentPattern() {
        return this.parentPattern;
    }

    public void setParentPattern(int parentPattern) {
        this.parentPattern = parentPattern;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return this.cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictId() {
        return this.districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBindParent() {
        return this.isBindParent;
    }

    public void setBindParent(boolean bindParent) {
        this.isBindParent = bindParent;
    }

    public boolean isBindTeacher() {
        return this.isBindTeacher;
    }

    public void setBindTeacher(boolean bindTeacher) {
        this.isBindTeacher = bindTeacher;
    }

    public long getExpireTimeStamp() {
        return this.expireTimeStamp;
    }

    public void setExpireTimeStamp(long expireTimeStamp) {
        this.expireTimeStamp = expireTimeStamp;
    }
}
