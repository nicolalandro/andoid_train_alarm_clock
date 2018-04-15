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
import com.example.mint.alarmclock.SharedPreferences.AlarmState;
import com.example.mint.alarmclock.SharedPreferences.SharedPreferencesPresenter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SetAlarmView {
    private SetAlarmPresenter mSetAlarmPresenter;
    private SharedPreferencesPresenter mSharedPreferencesPresenter;

    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;

    private TextView mTimeTextView;
    private AlarmState mAlarmState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSetAlarmPresenter = new SetAlarmPresenter();
        mSharedPreferencesPresenter = new SharedPreferencesPresenter();
        mSharedPreferencesPresenter.bindView(this);
        mAlarmState = mSharedPreferencesPresenter.restore();

        mTimeTextView = findViewById(R.id.timeTextView);
        mTimeTextView.setText(formatTime(mAlarmState.getHour(), mAlarmState.getMinutes()));

        Switch activeAlarmSwitch = findViewById(R.id.activeAlarmSwitch);
        activeAlarmSwitch.setChecked(mAlarmState.isActive());

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSetAlarmPresenter.bindView(this);
        mSharedPreferencesPresenter.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSetAlarmPresenter.unbindView();
        mSharedPreferencesPresenter.save(mAlarmState);
        mSharedPreferencesPresenter.unbindView();
    }

    private String formatTime(int hour, int minutes) {
        if (minutes < 10)
            return hour + ":0" + minutes;
        return hour + ":" + minutes;
    }

    public void OnHourTextViewClicked(View view) {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mAlarmState.setHour(selectedHour);
                mAlarmState.setMinutes(selectedMinute);
                mTimeTextView.setText(formatTime(selectedHour, selectedMinute));
            }
        }, mAlarmState.getHour(), mAlarmState.getMinutes(), true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void OnSwitchClicked(View switchView) {
        Switch switchElement = (Switch) switchView;
        if (switchElement.isChecked()) {
            mAlarmState.setActive(true);
            mSetAlarmPresenter.onAlarmActive(mAlarmState.getHour(), mAlarmState.getMinutes());
        } else {
            mAlarmState.setActive(false);
            try {
                mAlarmManager.cancel(mPendingIntent);
                Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
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
