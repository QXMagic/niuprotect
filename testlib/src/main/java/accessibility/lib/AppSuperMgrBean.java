package accessibility.lib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AppSuperMgrBean extends BaseLitePal {
    public static final int TYPE_DISABLE = 1;
    public static final int TYPE_LOCK = 2;
    private String packageName;
    private int type;


    @Retention(RetentionPolicy.SOURCE)
    public @interface Types {
    }

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
    }
}
