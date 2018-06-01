package com.example.mint.alarmclock.TrenitaliaAPI;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InfoTrainResponse {
    @SerializedName("compRitardo")
    private List<String> mLenguagesDelayMessage;

    public InfoTrainResponse() {
        mLenguagesDelayMessage = new ArrayList<>();
    }

    public List<String> getLenguagesDelayMessage() {
        return mLenguagesDelayMessage;
    }
}
