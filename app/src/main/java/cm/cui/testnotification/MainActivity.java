package cm.cui.testnotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orderListHander.postDelayed(runnable, 3000);
    }

    /**
     * 默认通知栏
     * 用不同手机会有不同效果显示，需注意
     */
    private void setNotficationDemo() {
        /**
         *  创建通知栏管理工具
         */

        NotificationManager notificationManager = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);

        /**
         *  实例化通知栏构造器
         */

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        /**
         *  设置Builder
         */
        //设置标题
        mBuilder.setContentTitle("我是标题")
                //设置内容
                .setContentText("我是内容")
                //设置大图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher_round)
                //设置通知时间
                .setWhen(System.currentTimeMillis())
                //首次进入时显示效果
                .setTicker("我是测试内容")
                //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
                .setDefaults(Notification.DEFAULT_SOUND);

        notificationManager.notify(10, mBuilder.build());
    }


    /**
     * 自定义通知栏
     */
    private void setNotificationDemoSecond(int progress) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        NotificationManager notificationManager = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);
        mBuilder.setSmallIcon(R.mipmap.timg);
        Intent intent = new Intent(this, SecondeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContent(remoteViews);
        if (progress == 1) {
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        }
        remoteViews.setImageViewResource(R.id.image, R.mipmap.timg);
        remoteViews.setTextViewText(R.id.title, "我是标题");
        remoteViews.setTextViewText(R.id.content, "我是内容");
        remoteViews.setProgressBar(R.id.pBar, 10, progress, false);
        remoteViews.setTextViewText(R.id.proNum, progress + "/10");
        notificationManager.notify(10, mBuilder.build());
    }

    /**
     *
     */
    private void setNotificationDemoForAndroidO(int progress) {
        //ID
        String id = "testNotification";
        //名称
        String name = "notification";

        NotificationManager notificationManager = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);
        Notification.Builder mBuilder = new Notification.Builder(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager
                    .IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(id);
            notificationManager.createNotificationChannel(channel);

            mBuilder.setSmallIcon(R.mipmap.timg);
            mBuilder.setContent(remoteViews);
            if (progress == 1) {
                mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            }
            remoteViews.setImageViewResource(R.id.image, R.mipmap.timg);
            remoteViews.setTextViewText(R.id.title, "我是标题");
            remoteViews.setTextViewText(R.id.content, "我是内容");
            remoteViews.setProgressBar(R.id.pBar, 10, progress, false);
            remoteViews.setTextViewText(R.id.proNum, progress + "/10");
            notificationManager.notify(10, mBuilder.build());
        }
    }

    int progress = 0;
    Handler orderListHander = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (progress < 10) {
                //模拟线程，每秒加1，用来模拟下载
                progress++;
                setNotificationDemoSecond(progress);
                orderListHander.postDelayed(this, 1000);
            }
        }
    };

}
