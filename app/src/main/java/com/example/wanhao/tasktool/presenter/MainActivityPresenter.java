package com.example.wanhao.tasktool.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.wanhao.tasktool.SQLite.TaskDao;
import com.example.wanhao.tasktool.activity.OutDateTaskActivity;
import com.example.wanhao.tasktool.bean.Task;
import com.example.wanhao.tasktool.dialog.PasswordDialog;
import com.example.wanhao.tasktool.tool.Constant;
import com.example.wanhao.tasktool.tool.NotificationCompatUtil;
import com.example.wanhao.tasktool.tool.SaveDataUtil;
import com.example.wanhao.tasktool.tool.StringUtil;
import com.example.wanhao.tasktool.view.IMainActivity;

import java.util.List;

import static com.example.wanhao.tasktool.tool.Constant.FIRSTINITID;

/**
 * Created by wanhao on 2017/10/12.
 */

public class MainActivityPresenter {
    private Context context;
    private IMainActivity iMainActivity;
    boolean isTipActivityCreat;

    public MainActivityPresenter(Context context, IMainActivity iMainActivity) {
        this.context = context;
        this.iMainActivity = iMainActivity;
    }

    public void dataInit() {
        tipAcivityStart();
    }

    //初始化是否需要密码
    public void tipAcivityStart() {
        isTipActivityCreat = false;
        if (Constant.ISNEEDPASSWORD) {
            final PasswordDialog enterDialog = new PasswordDialog(context, "输入密码");
            final String token = SaveDataUtil.getValueFromSharedPreferences(context, Constant.CODE_FILE_NAME, Constant.PASSWORD_CODE);
            enterDialog.setOnClickListener(new PasswordDialog.OnClickListener() {
                @Override
                public void onClick() {
                    if (enterDialog.isAlike(StringUtil.stringToIntAr(token))) {
                        //前后密码一致，设置密码
                        isTipActivityCreat = true;
                        enterDialog.cancel();
                    } else {
                        Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            enterDialog.setOnCancelListener(new PasswordDialog.OnCancelListener() {
                @Override
                public void onCancel() {
                    if (isTipActivityCreat) {
                        iMainActivity.startTipActivity();
                    }
                }
            });
            enterDialog.show();
        } else {
            iMainActivity.startTipActivity();
        }
    }

    public void endDateTaskCheck() {
        if (!Constant.ISTODAYINIT) {
            /*
            初始化
             */
            TaskDao dao = new TaskDao(context);
            List<Task> taskList = dao.alterTaskOutDate(true);

            if (taskList.size() > 0) {
                NotificationCompatUtil.showNotification(context,
                        new Intent(context, OutDateTaskActivity.class),
                        FIRSTINITID, "这是标题", "有任务过期了，快去看看", "");
            }
//            } else {
//                Notification notification = new Notification.Builder(context)
//                        .setSmallIcon(R.drawable.app_icon)//设置小图标
//                        .setContentTitle("这是标题")
//                        .setContentText("今日第一次初始化")
//                        .build();
//                NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//                notificationManager.notify(FIRSTINITID, notification);
//            }
//        }else{
//            Notification notification = new Notification.Builder(context)
//                    .setSmallIcon(R.drawable.app_icon)//设置小图标
//                    .setContentTitle("这是标题")
//                    .setContentText("今日已经初始化")
//                    .build();
//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.notify(FIRSTINITID, notification);
//        }
        }
    }
}
