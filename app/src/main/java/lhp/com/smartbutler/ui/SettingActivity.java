package lhp.com.smartbutler.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.service.SmsService;
import lhp.com.smartbutler.utils.ShareUtils;
import lhp.com.smartbutler.utils.StaticClass;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.switch_tts)
    Switch switchTts;
    @InjectView(R.id.sw_sms)
    Switch swSms;
    @InjectView(R.id.ll_update)
    LinearLayout llUpdate;
    @InjectView(R.id.tv_version)
    TextView tvVersion;

    private String versionName;
    private int versionCode;
    private String url;

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
        llUpdate.setOnClickListener(this);
        //设置选中状态
        switchTts.setChecked(ShareUtils.getBoolean(this, "isSpeak", false));
        swSms.setChecked(ShareUtils.getBoolean(this, "isSms", false));

        try {
            getVersionNameCode();
            tvVersion.setText("检测版本：" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tvVersion.setText("检测版本");
        }
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
            case R.id.ll_update:
                RxVolley.get(StaticClass.CHECK_UPDATE_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        //L.i(t);
                        parsingJson(t);
                    }
                });
                break;
        }
    }

    private void parsingJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("versionCode");
            String content = jsonObject.getString("content");
            url = jsonObject.getString("url");
            if (code > versionCode) {
                showUpdateDialog(content);
            } else {
                Toast.makeText(this, "当前应用是最新版本", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹出升级提示
     *
     * @param content
     */
    private void showUpdateDialog(String content) {
        new AlertDialog.Builder(this)
                .setTitle("有新版本啦")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SettingActivity.this, UpdateActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //我什么都不做，也会执行dismis方法
                    }
                }).show();
    }

    //获取版本信息
    private void getVersionNameCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
        versionName = info.versionName;
        versionCode = info.versionCode;
    }
}
