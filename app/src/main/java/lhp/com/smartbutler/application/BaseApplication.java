package lhp.com.smartbutler.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import lhp.com.smartbutler.utils.SecretKey;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), SecretKey.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this, SecretKey.BMOB_APPLICATION_ID);

        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=" + SecretKey.XFYUN_APP_ID);
    }
}
