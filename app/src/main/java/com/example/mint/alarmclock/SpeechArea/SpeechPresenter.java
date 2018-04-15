package com.example.mint.alarmclock.SpeechArea;

import android.annotation.SuppressLint;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SpeechPresenter {
    SpeechView mSpeechView = null;

    public void bindView(SpeechView view){
        mSpeechView = view;
    }

    public void unbindView(){
        mSpeechView = null;
    }

    @SuppressLint("CheckResult")
    public void getDelayTrainText(){
        String mUrl15 = "http://www.viaggiatreno.it/viaggiatrenonew/resteasy/viaggiatreno/andamentoTreno/S01708/23004";
        String[] oreUrl = {"7 e 15", mUrl15};
        Observable<String[]> delayObsevable = Observable.just(oreUrl);
        delayObsevable
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        new Consumer<String[]>() {
                            @Override
                            public void accept(String oreAndUrl[]) {
                                try {
                                    String jsonResponse = getHttpRequest(oreAndUrl[1]);

                                    String delay = jsonParseToCompRitardo(jsonResponse);

                                    String delayAnnuncementString = createDelayAnnuncementString(oreAndUrl[0], delay);
                                    mSpeechView.speakOut(delayAnnuncementString);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    mSpeechView.speakOut("Qualcosa è andato storto");
                                }
                            }
                        }

                );
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
