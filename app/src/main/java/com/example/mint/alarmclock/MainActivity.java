package com.example.mint.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mint.alarmclock.SetAlarm.SetAlarmPresenter;
import com.example.mint.alarmclock.SetAlarm.SetAlarmView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SetAlarmView {
    private SetAlarmPresenter mSetAlarmPresenter;

    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;

    private TextView mTimeTextView;
    private int mHour;
    private int mMinutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSetAlarmPresenter = new SetAlarmPresenter();

        mTimeTextView = findViewById(R.id.timeTextView);
        Calendar mcurrentTime = Calendar.getInstance();
        mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        mMinutes = mcurrentTime.get(Calendar.MINUTE);
        mTimeTextView.setText(formatTime(mHour, mMinutes));

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSetAlarmPresenter.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSetAlarmPresenter.unbindView();
    }

    private String formatTime(int hour, int minutes) {
        if (mMinutes < 10)
            return hour + ":0" + minutes;
        return hour + ":" + minutes;
    }

    public void OnHourTextViewClicked(View view) {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mHour = selectedHour;
                mMinutes = selectedMinute;
                mTimeTextView.setText(formatTime(selectedHour, selectedMinute));
            }
        }, mHour, mMinutes, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void OnSwitchClicked(View switchView) {
        Switch switchElement = (Switch) switchView;
        if (switchElement.isChecked()) {
            mSetAlarmPresenter.onAlarmActive(mHour, mMinutes);
        } else {
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();

            mAlarmManager.cancel(mPendingIntent);
        }
    }

    @Override
    public void setAlarm(long millisecond) {
        Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, AlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, millisecond, AlarmManager.INTERVAL_HOUR, mPendingIntent);
    }
}
