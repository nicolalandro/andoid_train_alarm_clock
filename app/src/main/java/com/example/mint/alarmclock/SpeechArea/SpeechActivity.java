package com.example.mint.alarmclock.SpeechArea;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mint.alarmclock.R;

import java.util.Locale;

public class SpeechActivity extends AppCompatActivity implements SpeechView{
    private TextToSpeech ttobj;
    private SpeechPresenter mSpeechPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

        mSpeechPresenter = new SpeechPresenter();
        mSpeechPresenter.bindView(this);
        mSpeechPresenter.getDelayTrainText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSpeechPresenter.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSpeechPresenter.unbindView();
    }

    public void speakOut(String s) {
        ttobj.setLanguage(Locale.ITALY);
        ttobj.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void OnSpeech(View view) {
        speakOut("ciao");
    }



}
