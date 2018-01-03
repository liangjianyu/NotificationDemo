package com.allens.notifacationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

/**
 * @author allens
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseNotify();
            }
        });

        findViewById(R.id.btn_Action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action();
            }
        });

        findViewById(R.id.btn_notify_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotify();
            }
        });
        findViewById(R.id.btn_notify_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotify();
            }
        });
        findViewById(R.id.btn_Default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_default();
            }
        });
        findViewById(R.id.btn_sound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_sound();
            }
        });
        findViewById(R.id.btn_vibration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_vibration();
            }
        });
        findViewById(R.id.btn_lights).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_ligths();
            }
        });

        findViewById(R.id.btn_remote).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                init_remote();
            }
        });
        findViewById(R.id.btn_screen).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                init_screen();
            }
        });
        findViewById(R.id.btn_group).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                init_group();
            }
        });
        findViewById(R.id.btn_hiufu).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    init_huifu();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void init_huifu() {
        //[1]获取一个NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        //[2]创建remoteInput对象,这个对象指定了这个notification的标题和一个key
        String replyLabel = getResources().getString(R.string.app_name);
        RemoteInput remoteInput = new RemoteInput.Builder("KEY")
                .setLabel(replyLabel)
                .build();
        //[3]创建一个Action对象 可以指定用户一个友好的输入提示，可以指定跳转意图,
        Intent deleteIntent = new Intent(this, MainActivity.class);
        Notification.Action action =
                new Notification.Action.Builder(R.mipmap.ic_launcher,
                        "请输入想回复内容", PendingIntent.getActivity(this, 10002, deleteIntent, 0))
                        .addRemoteInput(remoteInput)
                        .build();

        //[3]创建一个Notification对象
        Notification notification =
                new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Title")
                        .setContentText("msg")
                        .addAction(action)
                        .build();

        //[4]发送这个notification
        mNotificationManager.notify(11, notification);

    }


    int i = 200;

    private void init_group() {
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        i++;
        String notificationContent = "相同组：" + i;
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().setSummaryText(notificationContent))
                .setGroup("EAT")
                .setGroupSummary(true);
        final Notification notification = builder.build();

        notifyManager.notify(10, notification);
    }

    private void init_screen() {
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("悬挂式")
                //通过setFullScreenIntent将一个Notification变成悬挂式Notification
                .setFullScreenIntent(pendingIntent, true)
                //设置Notification的显示等级
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setContentText("只有小图标、标题、内容");

        Notification notification = builder.build();
        if (notifyManager != null) {
            notifyManager.notify(9, notification);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init_remote() {
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("折叠式")
                .setContentText("只有小图标、标题、内容");

        Notification notification = builder.build();
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notity_remote);
        notification.bigContentView = remoteViews;
        if (notifyManager != null) {
            notifyManager.notify(8, notification);
        }


    }

    private void init_ligths() {
        final NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是带有呼吸灯效果的通知")
                .setContentText("一闪一闪亮晶晶~")
                //ledARGB 表示灯光颜色、 ledOnMS 亮持续时间、ledOffMS 暗的时间
                .setLights(0xFF0000, 3000, 3000);
        Notification notify = builder.build();
        //只有在设置了标志符Flags为Notification.FLAG_SHOW_LIGHTS的时候，才支持呼吸灯提醒。
        notify.flags = Notification.FLAG_SHOW_LIGHTS;
        //使用handler延迟发送通知,因为连接usb时,呼吸灯一直会亮着
        notifyManager.notify(7, builder.build());
    }

    private void init_vibration() {
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        long[] vibrate = new long[]{0, 500, 1000, 1500};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是伴有震动效果的通知")
                .setContentText("颤抖吧,凡人~")
                .setVibrate(vibrate);
        if (notifyManager != null) {
            notifyManager.notify(6, builder.build());
        }
    }

    private void init_sound() {
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是伴有铃声效果的通知")
                .setContentText("美妙么?安静听~")
                //调用自己提供的铃声，位于 /res/values/raw 目录下
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notice));
        if (notifyManager != null) {
            notifyManager.notify(5, builder.build());
        }
    }

    private void init_default() {
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("通知效果")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentText("自己设置 ");
        if (notifyManager != null) {
            notifyManager.notify(4, builder.build());
        }
    }

    private void updateNotify() {
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("这是更新过的")
                .setContentText("因为具有相同的ID ");
        if (notifyManager != null) {
            notifyManager.notify(3, builder.build());
        }
    }

    private void createNotify() {
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("这是初始化的")
                .setContentText("现在点击发送相同ID 的按钮");
        if (notifyManager != null) {
            notifyManager.notify(3, builder.build());
        }
    }


    /**
     * 发送一个点击跳转到MainActivity的消息
     */
    private void action() {
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //获取PendingIntent
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //创建 Notification.Builder 对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("我是带Action的Notification")
                .setContentText("点我会打开MainActivity")
                .setContentIntent(mainPendingIntent);
        //发送通知
        notifyManager.notify(2, builder.build());
    }


    private void baseNotify() {
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builder并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle("最简单的Notification")
                //设置通知内容
                .setContentText("只有小图标、标题、内容");
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        if (notifyManager != null) {
            notifyManager.notify(1, builder.build());
        }
    }

}
