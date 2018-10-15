package com.caimuhao.architecture.lib_common.base;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;
import com.caimuhao.architecture.lib_common.BuildConfig;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

/**
 * @author Smile
 * @time 2018/9/13  16:08
 * @desc ${TODD}
 */
public class BaseApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    initUtil();
    initRoute();
  }

  private void initUtil() {
    Utils.init(this);
    LogUtils.getConfig().setGlobalTag("bxm");
  }

  private void initRoute() {
    if (debug()) {
      ARouter.openLog();
      ARouter.openDebug();
    }
    ARouter.init(this);
    LogUtils.d("initRouter");
  }

  public boolean debug() {
    return BuildConfig.DEBUG;
  }
}
