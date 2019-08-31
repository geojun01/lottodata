package com.example.test;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * test
 * Created by Jungle(JUN)
 * 2019-08-19
 **/

public interface ApiService {

    @GET("lotto.json")
    Call<List<WeatherSample >> getPosts();
}
