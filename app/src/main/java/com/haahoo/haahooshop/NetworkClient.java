package com.haahoo.haahooshop;

import android.content.Context;

import com.haahoo.haahooshop.utils.Global;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

 // private static final String BASE_URL = "https://haahoo.in/";
 private static final String BASE_URL = Global.BASE_URL;
  private static Retrofit retrofit;
  public static Retrofit getRetrofitClient(Context context) {
    if (retrofit == null) {
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .build();
      retrofit = new Retrofit.Builder()
              .baseUrl(BASE_URL)
              .client(okHttpClient)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
    }
    return retrofit;
  }

}