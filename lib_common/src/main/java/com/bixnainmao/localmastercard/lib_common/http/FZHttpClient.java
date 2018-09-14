package com.bixnainmao.localmastercard.lib_common.http;

import com.bixnainmao.localmastercard.lib_common.common.AppConfig;
import java.net.Proxy;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Maliang
 * @desc
 * @date 2017/12/15 下午2:48.
 */

public class FZHttpClient {

  private static Retrofit.Builder mRetrofitBuilder =
      new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
          //            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
          .client(getHttpClient().newBuilder().build());

  public static OkHttpClient getHttpClient() {
    return HttpClientHolder.httpClient;
  }

  private static class HttpClientHolder {
    private static final long TIME_OUT = 20L;

    private static OkHttpClient httpClient = getHttpClient();

    private static OkHttpClient getHttpClient() {
      OkHttpClient.Builder builder =
          new OkHttpClient.Builder().readTimeout(TIME_OUT, TimeUnit.SECONDS)
              .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
              .writeTimeout(TIME_OUT, TimeUnit.SECONDS);

      builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts());
      builder.hostnameVerifier(new TrustAllHostnameVerifier());
      if (AppConfig.DEBUG) {
        builder.addInterceptor(
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
      } else {
        builder.proxy(Proxy.NO_PROXY); // TODO:Test阶段暂时的状态
      }
      return builder.build();
    }
  }

  public static class TrustAllCerts implements X509TrustManager {
    @Override public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
    }

    @Override public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
    }

    @Override public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[] {};
    }
  }

  public static class TrustAllHostnameVerifier implements HostnameVerifier {
    @Override public boolean verify(String hostname, SSLSession session) {
      return true;
    }
  }

  public static SSLSocketFactory createSSLSocketFactory() {
    SSLSocketFactory ssfFactory = null;

    try {
      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, new TrustManager[] { new TrustAllCerts() }, new SecureRandom());
      ssfFactory = sc.getSocketFactory();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return ssfFactory;
  }

  public static <T> T getService(final String baseUrl, final Class<T> service) {
    return mRetrofitBuilder.baseUrl(baseUrl).build().create(service);
  }
}
