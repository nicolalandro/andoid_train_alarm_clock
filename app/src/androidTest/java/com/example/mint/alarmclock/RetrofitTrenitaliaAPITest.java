package com.example.mint.alarmclock;

import android.support.test.runner.AndroidJUnit4;

import com.example.mint.alarmclock.TrenitaliaAPI.InfoTrainResponse;
import com.example.mint.alarmclock.TrenitaliaAPI.Train;
import com.example.mint.alarmclock.TrenitaliaAPI.TrenitaliaService;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RetrofitTrenitaliaAPITest {
    @Test
    public void retrofitDelayString() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.viaggiatreno.it/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TrenitaliaService service = retrofit.create(TrenitaliaService.class);

        Call<InfoTrainResponse> call = service.trainInfo("S01708", 23004);
        Response<InfoTrainResponse> response = call.execute();
        InfoTrainResponse i = response.body();
        assertEquals(
                9,
                i.getLenguagesDelayMessage().size()
        );
        assertEquals(
                "ritardo 1 min.",
                i.getLenguagesDelayMessage().get(0)
        );
    }

    @Test
    public void retrofitAutocomplete() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.viaggiatreno.it/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        TrenitaliaService service = retrofit.create(TrenitaliaService.class);

        Call<String> call = service.stationCodeAutosuggest("Busto a");
        Response<String> response = call.execute();
        String i = response.body();

        String[] expected = {
                "BUSTO ARSIZIO|S01031",
                "BUSTO ARSIZIO N.|S01137",
                "BUSTO ARSIZIO RFI|N00107"
        };
        assertEquals(
                expected[0],
                i.split("\n")[0]
        );
    }

    @Test
    public void retrofitTrainSearch() throws IOException {
//        Fri Jun 01 2018 20:21:44 GMT+0200 (GMT+02:00)
//        DateFormat formatter = new SimpleDateFormat("EEE%20MMM%20dd%20yyyy%20HH:mm:ss%20'GMT'Z%20(z)", Locale.US);
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)", Locale.US);
        Date today = Calendar.getInstance().getTime();
        String reportDate = formatter.format(today);
//        assertEquals(
//                "Fri%20Jun%2001%202018%2020:36:57%20GMT+0200%20(GMT+02:00)",
//                reportDate
//        );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.viaggiatreno.it/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TrenitaliaService service = retrofit.create(TrenitaliaService.class);

        Call<List<Train>> call = service.searchTrain("S01031", reportDate);
//        assertEquals(
//                "http://www.viaggiatreno.it/viaggiatrenonew/resteasy/viaggiatreno/autocompletaStazione/S01031/Fri%20Jun%2001%202018%2020:40:51%20GMT+0200%20(GMT+02:00)",
//                call.request().url().toString()
//        );
        Response<List<Train>> response = call.execute();
        List<Train> i = response.body();
//        assertEquals(
//                14,
//                i.size()
//        );
    }
}
