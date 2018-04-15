package com.example.mint.alarmclock.SpeechArea;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mint.alarmclock.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SpeechActivity extends AppCompatActivity {
    private TextToSpeech ttobj;
    private String mUrl15 = "http://www.viaggiatreno.it/viaggiatrenonew/resteasy/viaggiatreno/andamentoTreno/S01708/23004";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

        String[] oreUrl = {"7 e 15", mUrl15};
        Observable<String[]> delayObsevable = Observable.just(oreUrl);
        delayObsevable
                .subscribeOn(Schedulers.newThread())
                .subscribe(getCompRitardo());

    }

    private void speackOutOnCreate(final String s) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                speakOut(s);  //speak after 1000ms
            }
        }, 1000);
    }

    private void speakOut(String s) {
        ttobj.setLanguage(Locale.ITALY);
        ttobj.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void OnSpeech(View view) {
        speakOut("ciao");
    }

//    From old project

    private void old() {
        String[] oreUrl = {"7 e 15", mUrl15};
        Observable<String[]> delayObsevable = Observable.just(oreUrl);
        delayObsevable
                .subscribeOn(Schedulers.newThread())
                .subscribe(getCompRitardo());
    }

    @NonNull
    private Consumer<String[]> getCompRitardo() {
        return new Consumer<String[]>() {
            @Override
            public void accept(String oreAndUrl[]) {
                try {
                    String jsonResponse = getHttpRequest(oreAndUrl[1]);

                    String delay = jsonParseToCompRitardo(jsonResponse);

                    String delayAnnuncementString = createDelayAnnuncementString(oreAndUrl[0], delay);
                    speakOut(delayAnnuncementString);
                } catch (Exception e) {
                    e.printStackTrace();
                    speakOut("Qualcosa è andato storto");
                }
            }
        };
    }

    private String getHttpRequest(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection httpClient = (HttpURLConnection) urlObj.openConnection();

        httpClient.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));

        StringBuilder bodyString = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            bodyString.append(inputLine);
        }
        in.close();

        return bodyString.toString();
    }

    private String jsonParseToCompRitardo(String output) {
        JsonElement json = new JsonParser().parse(output);

        return json.getAsJsonObject().get("compRitardo")
                .getAsJsonArray().get(0)
                .getAsString();
    }

    private String createDelayAnnuncementString(String ore, String delay) {
        return "Il treno delle ore " + ore + " arriverà con " + delay;
    }
}
