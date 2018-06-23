package com.example.mint.alarmclock.TrenitaliaAPI;

import com.google.gson.annotations.SerializedName;

public class Train {
    @SerializedName("numeroTreno")
    private int mTrainNumber;
    @SerializedName("codOrigine")
    private String mOriginCode;
    @SerializedName("compOrarioPartenza")
    private String mOrarioPartenza;
    @SerializedName("destinazione")
    private String mDestinazione;
    @SerializedName("categoria")
    private String mTrainCategory;

    public Train(int mTrainNumber, String mOriginCode, String mOrarioPartenza, String mDestinazione, String mTrainCategory) {
        this.mTrainNumber = mTrainNumber;
        this.mOriginCode = mOriginCode;
        this.mOrarioPartenza = mOrarioPartenza;
        this.mDestinazione = mDestinazione;
        this.mTrainCategory = mTrainCategory;
    }

    public String toString(){
        return mTrainCategory + " " + mTrainNumber + "\n" + mOriginCode + " " + mOrarioPartenza + "\nto: " + mDestinazione;
    }
}
