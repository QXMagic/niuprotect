package accessibility;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PermissionBean implements MultiItemEntity {
    private int autoStatus;
    private int beanType;
    private String descriptor;
    private String descriptorName;

    /* renamed from: id */
    private int f11794id;
    private boolean isManual;
    private boolean isNeedCheck;
    private boolean isNeedOpen;
    private boolean isOpen;
    private int itemType;
    private String meaning;
    private String permissionName;
    private String url;

    public PermissionBean(String str) {
        this.beanType = 1;
        this.url = str;
        this.itemType = 2;
        this.isOpen = true;
    }

    public int getAutoStatus() {
        return this.autoStatus;
    }

    public int getBeanType() {
        return this.beanType;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public String getDescriptorName() {
        return this.descriptorName;
    }

    public int getId() {
        return this.f11794id;
    }

    public int getItemType() {
        return this.itemType;
    }

    public String getMeaning() {
        return this.meaning;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isManual() {
        return this.isManual;
    }

    public boolean isNeedCheck() {
        return this.isNeedCheck;
    }

    public boolean isNeedOpen() {
        return this.isNeedOpen;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isScreen() {
        return this.beanType == 2;
    }

    public boolean isVideo() {
        return this.beanType == 1;
    }

    public void setAutoStatus(int i) {
        this.autoStatus = i;
    }

    public void setBeanType(int i) {
        this.beanType = i;
    }

    public void setDescriptor(String str) {
        this.descriptor = str;
    }

    public void setDescriptorName(String str) {
        this.descriptorName = str;
    }

    public void setId(int i) {
        this.f11794id = i;
    }

    public void setManual(boolean z) {
        this.isManual = z;
    }

    public void setMeaning(String str) {
        this.meaning = str;
    }

    public void setNeedCheck(boolean z) {
        this.isNeedCheck = z;
    }

    public void setNeedOpen(boolean z) {
        this.isNeedOpen = z;
    }

    public void setOpen(boolean z) {
        this.isOpen = z;
    }

    public void setPermissionName(String str) {
        this.permissionName = str;
    }

    public String toString() {
        return "PermissionBean{permissionName='" + this.permissionName + "', meaning='" + this.meaning + "', isOpen=" + this.isOpen + ", descriptorName='" + this.descriptorName + "', descriptor='" + this.descriptor + "', id=" + this.f11794id + ", isNeedCheck=" + this.isNeedCheck + ", autoStatus=" + this.autoStatus + '}';
    }

    public PermissionBean(String str, String str2, String str3, String str4, int i, boolean z, boolean z2) {
        this.permissionName = str;
        this.meaning = str2;
        this.descriptorName = str3;
        this.descriptor = str4;
        this.f11794id = i;
        this.isNeedCheck = z;
        this.isNeedOpen = z2;
        this.itemType = 1;
    }


}
