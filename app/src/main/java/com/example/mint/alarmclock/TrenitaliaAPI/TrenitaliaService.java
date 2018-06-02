package com.example.mint.alarmclock.TrenitaliaAPI;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TrenitaliaService {
    @GET("/viaggiatrenonew/resteasy/viaggiatreno/andamentoTreno/{codpartenza}/{codtreno}")
    Call<InfoTrainResponse> trainInfo(@Path("codpartenza") String codPartenza, @Path("codtreno") int codTreno);

    @GET("/viaggiatrenonew/resteasy/viaggiatreno/autocompletaStazione/{strpartenza}")
    Call<String> stationCodeAutosuggest(@Path("strpartenza") String str);

    @GET("/viaggiatrenonew/resteasy/viaggiatreno/partenze/{codpartenza}/{timestamp}")
    Observable<List<Train>> searchTrain(@Path("codpartenza") String codPartenza, @Path("timestamp") String timestamp);
}
