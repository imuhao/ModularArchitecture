package com.bixnainmao.localmastercard.lib_common.http.api;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Smile
 * @time 2018/9/14  11:40
 * @desc ${TODD}
 */
public class ApiManager {

  private ApiManager() {
  }

  private static class CommonApiHolder {
    final private static int TIME_OUT = 20;
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(
        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build();

    private static Retrofit retrofit =
        new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("http://ip.chinaz.com/")
            .client(okHttpClient)
            .build();
    private static Api api = retrofit.create(Api.class);
  }

  public static Api getApi() {
    return CommonApiHolder.api;
  }
}
