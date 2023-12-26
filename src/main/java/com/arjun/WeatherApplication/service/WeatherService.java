package com.arjun.WeatherApplication.service;

import com.arjun.WeatherApplication.model.WeatherData;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private final RestTemplate restTemplate;
    private final String API_KEY = "0e939e2549198f7e293ef6a32bc58138";
    private final String API_URL = "https://api.openweathermap.org/data/";

    private WeatherService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    public WeatherData getWeatherData(String city){
        String url = API_URL + "2.5/weather?q=" + city + "&appid=" + API_KEY;
        ResponseEntity<WeatherData> response = null;
        try {
             response = restTemplate.getForEntity(url, WeatherData.class);
        }catch (Exception e){
            e.printStackTrace();
        }


        System.out.println(url);

        assert response != null;
        return response.getBody();
    }



    /*
    https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
     */

}
