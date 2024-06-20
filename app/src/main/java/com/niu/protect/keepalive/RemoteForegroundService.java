package com.niu.protect.keepalive;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.core.app.NotificationCompat;
import com.niu.protect.R;
import com.niu.protect.backService.IMyAidlInterface;
public class RemoteForegroundService extends Service {
    private Connection connection;
    private MyBinder myBinder;

    class MyBinder extends IMyAidlInterface.Stub {
        MyBinder() {
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.myBinder = new MyBinder();
        startService();
    }

    public void startService() {
        if (Build.VERSION.SDK_INT < 26) {
            if (Build.VERSION.SDK_INT >= 18) {
                startForeground(10, new Notification());
                startService(new Intent(this, CancelNotificationService.class));
                return;
            } else if (Build.VERSION.SDK_INT < 18) {
                startForeground(10, new Notification());
                return;
            } else {
                return;
            }
        }
        NotificationChannel channel = new NotificationChannel("service", "service", NotificationManager.IMPORTANCE_NONE);
        channel.setLightColor(-16776961);
        channel.setLockscreenVisibility(0);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(channel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "service");
        Notification notification = builder.setOngoing(true).setSmallIcon(R.mipmap.ic_launcher).setPriority(-2).setCategory("service").build();
        startForeground(10, notification);
    }

    public void bindService() {
        this.connection = new Connection();
        Intent bindIntent = new Intent(this, LocalForegroundService.class);
        bindService(bindIntent, this.connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService();
        return super.onStartCommand(intent, flags, startId);
    }

    public class Connection implements ServiceConnection {
        Connection() {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService();
            bindService();
        }
    }

    public static class CancelNotificationService extends Service {
        @Override
        public void onCreate() {
            super.onCreate();
            startForeground(10, new Notification());
            stopSelf();
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
