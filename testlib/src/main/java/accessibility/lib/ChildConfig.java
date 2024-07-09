package accessibility.lib;

public class ChildConfig extends BaseLitePal {
    private int closeRecent;
    private int disallowInstallApp;
    private int disallowUninstallApp;
    private int isStatusBarOpen;

    public int getCloseRecent() {
        return this.closeRecent;
    }

    public int getDisallowInstallApp() {
        return this.disallowInstallApp;
    }

    public int getDisallowUninstallApp() {
        return this.disallowUninstallApp;
    }

    public int getIsStatusBarOpen() {
        return this.isStatusBarOpen;
    }

    public void setCloseRecent(int i) {
        this.closeRecent = i;
        if (i == 0) {
            setToDefault("closeRecent");
        }
    }

    public void setDisallowInstallApp(int i) {
        this.disallowInstallApp = i;
        if (i == 0) {
            setToDefault("disallowInstallApp");
        }
    }

    public void setDisallowUninstallApp(int i) {
        this.disallowUninstallApp = i;
        if (i == 0) {
            setToDefault("disallowUninstallApp");
        }
    }

    public void setIsStatusBarOpen(int i) {
        this.isStatusBarOpen = i;
        if (i == 0) {
            setToDefault("isStatusBarOpen");
        }
    }

    public String toString() {
        return "ChildConfig{closeRecent=" + this.closeRecent + ", disallowUninstallApp=" + this.disallowUninstallApp + ", disallowInstallApp=" + this.disallowInstallApp + '}';
    }
}
