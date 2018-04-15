package com.example.mint.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TrainInfo extends AppCompatActivity {
    private String[] AUTOCOMPLETE = new String[]{
            "BUSTO ARSIZIO|S01031",
            "BUSTO ARSIZIO N.|S01137",
            "BUSTO ARSIZIO RFI|N00107",
            "GALLARATE|S01030",
            "VARESE|S01205",
            "VARESE CASBENO|N00025",
            "VARESE NORD|N00024"
    };
    private AutoCompleteTextView mAutocompleteFromTextView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_info);

        mAutocompleteFromTextView = findViewById(R.id.autoCompleteFromTextView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, AUTOCOMPLETE);
        mAutocompleteFromTextView.setAdapter(adapter);
    }

    public void OnSavedClicked(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "data");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}


