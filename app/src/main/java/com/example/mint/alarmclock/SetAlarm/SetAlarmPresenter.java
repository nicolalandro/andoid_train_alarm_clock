package com.example.mint.alarmclock.SetAlarm;

import android.util.Log;

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
        long diff = calendar.getTimeInMillis() - System.currentTimeMillis();
        if ((int)diff < 0) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Log.d("DEBUG!!!!", "OnSwitchClicked: change date");
        }
        mAlarmView.setAlarm(calendar.getTimeInMillis());

        Log.d("DEBUG!!!!", "OnSwitchClicked: " + (new SimpleDateFormat()).format(calendar.getTime()));
        Log.d("DEBUG!!!!", "OnSwitchClicked, Diff: " + diff);
    }
}
