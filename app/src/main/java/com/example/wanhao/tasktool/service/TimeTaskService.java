package com.example.wanhao.tasktool.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.activity.TimeTaskActivity;
import com.example.wanhao.tasktool.broadcastreceiver.TimeTaskBroadcast;
import com.example.wanhao.tasktool.tool.MyDate;

import static com.example.wanhao.tasktool.tool.Constant.TIMETASKID;

public class TimeTaskService extends Service {
    private static final String TAG = "TimeTaskService";

    private Notification.Builder builer;
    private Notification notification;
    private NotificationManager mNotificationManager;
    private TimeTaskBroadcast timeTaskBroadcast;
    private Intent mIntent;

    private String title;
    private String time;
    private Long allTime;
    private CountDownTimer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();

    }

    private void init() {

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builer = new Notification.Builder(this);
        //PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,mIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builer.setContentTitle(title)//设置通知栏标题
                .setContentText(time) //设置通知栏显示内容
                //.setContentIntent(resultPendingIntent)
                //.setTicker(ticker) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                //.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //.setDefaults(Notification.DEFAULT_ALL)
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.app_icon);//设置通知小ICON

        notification = builer.build();

        timeTaskBroadcast = new TimeTaskBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("TIME_TASK_ACTION");
        mIntent = new Intent("TIME_TASK_ACTION");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        if(intent!=null) {
            this.title = intent.getStringExtra("title");
            this.time = intent.getStringExtra("time");
        }
         this.allTime = MyDate.getTimeLongByString(time);

        timer = new CountDownTimer(allTime, 1000) {// 第一个参数是总共时间，第二个参数是间隔触发时间
            @Override
            public void onTick(long millisUntilFinished) {
                String data = MyDate.getTimeByLong(millisUntilFinished);
                builer.setContentText(data);
                notification = builer.build();
                startForeground(TIMETASKID, notification);
                mIntent.putExtra("time", data);
                mIntent.putExtra("title", title);
                sendBroadcast(mIntent);
            }

            @Override
            public void onFinish() {
                Log.e(TAG, "onfinish....");

                long[] vibrate = new long[]{0, 2500, 0, 2500,0,2500};
                Intent finishIntent = new Intent(TimeTaskService.this, TimeTaskActivity.class);
                PendingIntent resultPendingIntent = PendingIntent.getActivity(TimeTaskService.this,0,finishIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                builer.setContentIntent(resultPendingIntent)
                .setContentText("00:00:00")
                //builer.setSound();
                .setVibrate(vibrate)
                .setDefaults(Notification.DEFAULT_ALL);
                notification = builer.build();
                notification.flags |= Notification.FLAG_INSISTENT;
                //notification.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"0");
                startForeground(TIMETASKID, notification);

                //TimeTaskService.this.stopSelf();
            }
        };
        timer.start();

        init();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        timer.cancel();
        super.onDestroy();
    }
}
