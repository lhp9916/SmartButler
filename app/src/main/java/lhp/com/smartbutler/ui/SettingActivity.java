package lhp.com.smartbutler.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.service.SmsService;
import lhp.com.smartbutler.utils.ShareUtils;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.switch_tts)
    Switch switchTts;
    @InjectView(R.id.sw_sms)
    Switch swSms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        //设置监听
        switchTts.setOnClickListener(this);
        swSms.setOnClickListener(this);
        //设置选中状态
        switchTts.setChecked(ShareUtils.getBoolean(this, "isSpeak", false));
        swSms.setChecked(ShareUtils.getBoolean(this, "isSms", false));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_tts:
                //切换相反
                switchTts.setSelected(!switchTts.isSelected());
                //保存状态
                ShareUtils.putBoolean(this, "isSpeak", switchTts.isSelected());
                break;
            case R.id.sw_sms:
                //切换相反
                swSms.setSelected(!swSms.isSelected());
                ShareUtils.putBoolean(this, "isSms", swSms.isChecked());
                if (swSms.isChecked()) {
                    startService(new Intent(this, SmsService.class));
                } else {
                    stopService(new Intent(this, SmsService.class));
                }
                break;
        }
    }
}
