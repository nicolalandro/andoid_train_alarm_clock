package com.example.mint.alarmclock.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

public class SharedPreferencesPresenter {
    private Activity mActivity = null;
    private static final String HOUR = "hour";
    private static final String MINUTES = "minutes";
    private static final String STATE = "active";


    public void bindView(Activity view) {
        mActivity = view;
    }

    public void unbindView() {
        mActivity = null;
    }

    public void save(AlarmState alarmState) {
        SharedPreferences sharedPref = mActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(HOUR, alarmState.getHour());
        editor.putInt(MINUTES, alarmState.getMinutes());
        editor.putBoolean(STATE, alarmState.isActive());
        editor.apply();
    }

    public AlarmState restore() {
        SharedPreferences sharedPref = mActivity.getPreferences(Context.MODE_PRIVATE);
        return new AlarmState(
                sharedPref.getInt(HOUR, Calendar.HOUR_OF_DAY),
                sharedPref.getInt(MINUTES, Calendar.MINUTE),
                sharedPref.getBoolean(STATE, false)
        );

    }
}
