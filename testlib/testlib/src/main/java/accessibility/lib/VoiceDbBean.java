package accessibility.lib;

public class VoiceDbBean extends BaseLitePal {
    public static final int DEFAULT = 0;
    public static final int GROUP_LIMIT = 2;
    public static final int LIMIT = 1;
    public static final int MULTI_LIMIT = 3;
    public static final int STOP = 4;
    public static final int STUDY = 5;
    private String packageName;
    private int type;

    public String getPackageName() {
        return this.packageName;
    }

    public int getType() {
        return this.type;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setType(int i) {
        this.type = i;
        if (i == 0) {
            setToDefault("type");
        }
    }
}
