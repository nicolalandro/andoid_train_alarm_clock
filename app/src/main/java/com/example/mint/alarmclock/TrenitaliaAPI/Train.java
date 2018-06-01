package com.example.mint.alarmclock.TrenitaliaAPI;

import com.google.gson.annotations.SerializedName;

public class Train {
    @SerializedName("numeroTreo")
    private int mTrainNumber;
    @SerializedName("codOrigine")
    private String mOriginCode;
    @SerializedName("compOrarioPartenza")
    private String mOrarioPartenza;

    public Train(int mTrainNumber, String mOriginCode, String mOrarioPartenza) {
        this.mTrainNumber = mTrainNumber;
        this.mOriginCode = mOriginCode;
        this.mOrarioPartenza = mOrarioPartenza;
    }
}
