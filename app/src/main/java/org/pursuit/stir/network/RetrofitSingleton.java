package org.pursuit.stir.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static Retrofit instance;
    private static final String FOUR_SQUARE_BASE_URL = "https://api.foursquare.com/v2/";

    private RetrofitSingleton() {}

    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(FOUR_SQUARE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
