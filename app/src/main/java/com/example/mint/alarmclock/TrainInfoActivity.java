package com.example.mint.alarmclock;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TrainInfoActivity extends AppCompatActivity {
    private String[] AUTOCOMPLETE = new String[]{
            "BUSTO ARSIZIO|S01031",
            "BUSTO ARSIZIO N.|S01137",
            "BUSTO ARSIZIO RFI|N00107",
            "GALLARATE|S01030",
            "VARESE|S01205",
            "VARESE CASBENO|N00025",
            "VARESE NORD|N00024"
    };
    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView mFromAutocopleteView;
    private int mHour;
    private int mMinutes;
    private TextView mTimeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_info);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, AUTOCOMPLETE);

        mFromAutocopleteView = findViewById(R.id.autoCompleteFromTextView);
        mFromAutocopleteView.setAdapter(adapter);

        mTimeTextView = findViewById(R.id.timeFromTextView);
        mHour = Calendar.HOUR_OF_DAY;
        mMinutes = Calendar.MINUTE;
        mTimeTextView.setText(formatTime(mHour, mMinutes));
    }

    public void OnHourTextViewClicked(View view) {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(TrainInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    public void OnDateTextViewClicked(View view){

    }

    public void OnSavedClicked(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "data");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private String formatTime(int hour, int minutes) {
        if (minutes < 10)
            return hour + ":0" + minutes;
        return hour + ":" + minutes;
    }
}


