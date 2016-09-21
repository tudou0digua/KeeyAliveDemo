package hyapps.cmcc.com.keeyalivedemo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import hyapps.cmcc.com.keeyalivedemo.R;

public class InvisibleForegroundService extends Service {
    private static final int NOTIFICATION_ID = 10086;

    public InvisibleForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("InvisibleService", "InvisibleForegroundService created");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForegroundCompat();
        return START_STICKY;
    }

    public static void startService(Context context){
        context = context.getApplicationContext();
        context.startService(new Intent(context, InvisibleForegroundService.class));
    }

    public static void stopService(Context context){
        context = context.getApplicationContext();
        if (Build.VERSION.SDK_INT < 18) {
            context.stopService(new Intent(context, InvisibleForegroundService.class));
        } else {
            context.stopService(new Intent(context, InnerService.class));
        }
    }

    private void startForegroundCompat() {
        if (Build.VERSION.SDK_INT < 18) {
            // api 18（4.3）以下，用空的notificaiton即可
            startForeground(NOTIFICATION_ID, new Notification());
        } else {
            // api 18以上
            // 先开启一个前台服务，提供合法的参数
            startForeground(NOTIFICATION_ID, fadeNotification(this));
            // 再起一个服务，也是前台的
            startService(new Intent(this, InnerService.class));
        }
    }

    @Override
    public void onDestroy() {
        // 退出前台服务，同时清掉状态栏通知。
        // 在api 18的版本上，这时候状态栏通知没了，但InnerService还在，且仍旧是前台服务状态，目的达到。
        stopForeground(true);
        super.onDestroy();
    }

    private static Notification fadeNotification(Context context) {
        Notification notification = new Notification();
        // 随便给一个icon，假装是合法的Notification
        notification.icon = R.drawable.abc_ab_share_pack_mtrl_alpha;
        notification.contentView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
        return notification;
    }

    public static class InnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
            // 先把自己也搞成前台的，提供合法参数
            startForeground(NOTIFICATION_ID, fadeNotification(this));
            //自行退掉，或者把InvisibleForegroundService退掉。
            stopSelf();
        }

        @Override
        public void onDestroy() {
            stopForeground(true);
            super.onDestroy();
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
