package accessibility;

import android.content.Context;
import android.widget.Toast;

public class App {
    public static Context mContext;

    public static void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
