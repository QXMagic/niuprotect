package accessibility;

public class RunnableC6160 implements Runnable {

    public final int f12847;

    public RunnableC6160(int i) {
        this.f12847 = i;
    }

    @Override
    public void run() {
        if (PermissionAutoManager.setItem(this.f12847)) {
            return;
        }
        LogHelper.m27985("Permission ID：" + this.f12847 + "--- No impl");
    }
}
