package com.caimuhao.architecture.main;

import android.app.Activity;
import android.os.Bundle;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author Smile
 * @time 2018/9/13  16:37
 * @desc ${TODD}
 */
public class SplashActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    ARouter.getInstance().build("/main/login").navigation();
  }
}
