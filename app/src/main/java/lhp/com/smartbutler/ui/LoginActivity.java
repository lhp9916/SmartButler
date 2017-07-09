package lhp.com.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;

/**
 * Created by lhp on 2017/7/9.
 * description: 登录
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @InjectView(R.id.btn_register)
    Button btnRegister;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
        }
    }
}
