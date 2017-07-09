package lhp.com.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.MainActivity;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.utils.ShareUtils;
import lhp.com.smartbutler.utils.StaticClass;
import lhp.com.smartbutler.utils.UtilTools;

/**
 * Created by lhp on 2017/7/9.
 * description: 闪屏页
 */

public class SplashActivity extends AppCompatActivity {

    @InjectView(R.id.tv_splash)
    TextView tvSplash;

    /**
     * 1.延迟2000ms
     * 2.判断程序是否是第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     */

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //是否第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        //延迟2秒
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        //设置字体
        UtilTools.setFont(this, tvSplash);
    }

    /**
     * 判断程序是否第一次运行
     *
     * @return
     */
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }

    //禁止返回键
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
