package com.example.mint.alarmclock;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mint.alarmclock.TrenitaliaAPI.Train;
import com.example.mint.alarmclock.TrenitaliaAPI.TrenitaliaService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;

    private TextView mTimeTextView;
    private TextView mDateTextView;
    private ListView mTrainListView;
    private ArrayAdapter<String> mTrainArrayAdapter;
    private ArrayList<String> listItems = new ArrayList<>();
    private TrenitaliaService mTrenitaliaService;
    private PublishSubject<PublishSubject<List<Train>>> mNet;
    private PublishSubject<List<Train>> mGui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_info);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.viaggiatreno.it/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mTrenitaliaService = retrofit.create(TrenitaliaService.class);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, AUTOCOMPLETE);

        mFromAutocopleteView = findViewById(R.id.autoCompleteFromTextView);
        mFromAutocopleteView.setAdapter(adapter);

        mTimeTextView = findViewById(R.id.timeFromTextView);
        mDateTextView = findViewById(R.id.dateTextView);
        mTrainListView = findViewById(R.id.trainListView);
        mTrainArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listItems
        );
        mTrainListView.setAdapter(mTrainArrayAdapter);

        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinutes = calendar.get(Calendar.MINUTE);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
//        mYear = calendar.get(Calendar.DAY_OF_MONTH);
        mYear = 2018;

        mTimeTextView.setText(formatTime(mHour, mMinutes));
        mDateTextView.setText(formatDate(mYear, mMonth, mDayOfMonth));
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

    public void OnDateTextViewClicked(View view) {
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(TrainInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mYear = i;
                mMonth = i1;
                mDayOfMonth = i2;
                mDateTextView.setText(formatDate(mYear, mMonth, mDayOfMonth));
            }
        }, mYear, mMonth, mDayOfMonth);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    public void OnSavedClicked(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "data");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void OnSearchClicked(View view) {
        String fromString = mFromAutocopleteView.getText().toString().split("\\|")[1];
        Log.d("Debug!!!", "OnSearchClicked: "+ fromString);
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)", Locale.US);
        Date today = Calendar.getInstance().getTime();
        String todayString = formatter.format(today);

        Observable<List<Train>> obsevable = mTrenitaliaService.searchTrain(fromString, todayString);
        obsevable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                new Consumer<List<Train>>() {
                    @Override
                    public void accept(List<Train> trains){
                        setTrainList(trains);
                    }
                }
        );
    }

    public void setTrainList(List<Train> trains) {
        mTrainArrayAdapter.clear();
        for (Train train : trains) {
            mTrainArrayAdapter.add(train.toString());
        }
    }

    private String formatTime(int hour, int minutes) {
        if (minutes < 10)
            return hour + ":0" + minutes;
        return hour + ":" + minutes;
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        return dayOfMonth + "-" + (month + 1) + "-" + year;
    }
}


