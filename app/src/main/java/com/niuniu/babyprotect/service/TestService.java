package com.niuniu.babyprotect.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
public class TestService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TestService", "********************START****************");
        return super.onStartCommand(intent, flags, startId);
    }
}
