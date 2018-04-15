package com.example.mint.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;

    private TextView mTimeTextView;
    private int mHour;
    private int mMinutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeTextView = findViewById(R.id.timeTextView);
        Calendar mcurrentTime = Calendar.getInstance();
        mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        mMinutes = mcurrentTime.get(Calendar.MINUTE);
        mTimeTextView.setText(formatTime(mHour, mMinutes));

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private String formatTime(int hour, int minutes) {
        if (mMinutes < 10)
            return hour + "0" + minutes;
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
            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, AlarmReceiver.class);
            mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR, mHour % 12);
            calendar.set(Calendar.MINUTE, mMinutes);
            SimpleDateFormat format1 = new SimpleDateFormat();
            Log.d("DEBUG!!!!", "OnSwitchClicked: " + format1.format(calendar.getTime()));
            long diff = calendar.getTimeInMillis() - System.currentTimeMillis();
            Log.d("DEBUG!!!!", "OnSwitchClicked, Diff: " + diff);
            long timeMillis = calendar.getTimeInMillis();
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeMillis, AlarmManager.INTERVAL_HOUR, mPendingIntent);

        } else {
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();

            mAlarmManager.cancel(mPendingIntent);
        }
    }
}
