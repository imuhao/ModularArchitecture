package com.caimuhao.architecture.lib_common.http.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Smile
 * @time 2018/9/14  11:40
 * @desc ${TODD}
 */
public interface Api {

  /**
   * 根据IP反查省市
   */
  @GET("getip.aspx") Call<String> getIpAddress();
}
