package com.example.wanhao.tasktool.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeTaskBroadcast extends BroadcastReceiver {
    private static final String TAG = "TimeTaskBroadcast";

    private onDataChange dataChange;
    private onCloseChange closeChange;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(TAG, "onReceive: ");
        if(dataChange!=null){
            dataChange.dataChange(intent.getStringExtra("time"),intent.getStringExtra("title"));
        }
    }

    public void setDataChange(onDataChange dataChange){
        this.dataChange = dataChange;
    }

    public void setCloseLinsner(onCloseChange closeChange){
        this.closeChange = closeChange;
    }

    public interface onDataChange{
        void dataChange(String msg,String title);
    }

    public interface onCloseChange{
        void closeChange();
    }
}
