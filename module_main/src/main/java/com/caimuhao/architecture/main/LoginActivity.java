package com.caimuhao.architecture.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * @author Smile
 * @time 2018/9/13  17:10
 * @desc ${TODD}
 */
@Route(path ="/main/login")
public class LoginActivity extends Activity{

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

  }
}
