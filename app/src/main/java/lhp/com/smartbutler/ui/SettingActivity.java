package lhp.com.smartbutler.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.utils.ShareUtils;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.switch_tts)
    Switch switchTts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        switchTts.setOnClickListener(this);

        switchTts.setChecked(ShareUtils.getBoolean(this, "isSpeak", false));
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
        }
    }
}
