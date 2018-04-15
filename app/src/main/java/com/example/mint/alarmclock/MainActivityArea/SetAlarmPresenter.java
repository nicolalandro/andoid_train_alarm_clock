package com.example.mint.alarmclock.MainActivityArea;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.mint.alarmclock.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SetAlarmPresenter {
    private SetAlarmView mAlarmView = null;

    public void bindView(SetAlarmView view) {
        mAlarmView = view;
    }

    public void unbindView() {
        mAlarmView = null;
    }

    public void onAlarmActive(int hour, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, hour % 12);
        calendar.set(Calendar.MINUTE, minutes);

        mAlarmView.setAlarm(calendar.getTimeInMillis());

        Log.d("DEBUG!!!!", "OnSwitchClicked: " + (new SimpleDateFormat()).format(calendar.getTime()));
        long diff = calendar.getTimeInMillis() - System.currentTimeMillis();
        Log.d("DEBUG!!!!", "OnSwitchClicked, Diff: " + diff);
    }
}
