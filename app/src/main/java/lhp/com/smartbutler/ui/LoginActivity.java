package lhp.com.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import lhp.com.smartbutler.MainActivity;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.entity.MyUser;
import lhp.com.smartbutler.utils.ShareUtils;

/**
 * Created by lhp on 2017/7/9.
 * description: 登录
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @InjectView(R.id.btn_register)
    Button btnRegister;
    @InjectView(R.id.et_user)
    EditText etUser;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.cb_keepPassword)
    CheckBox cbKeepPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        //设置选中状态
        boolean isChecked = ShareUtils.getBoolean(this, "keeppass", false);
        cbKeepPassword.setChecked(isChecked);
        if (isChecked) {
            etUser.setText(ShareUtils.getString(this, "name", ""));
            etPassword.setText(ShareUtils.getString(this, "password", ""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btn_login:
                String name = etUser.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                //判断邮箱是否验证
                                if (user.getEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败 " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(this, "keeppass", cbKeepPassword.isChecked());

        //是否记住密码
        if (cbKeepPassword.isChecked()) {
            ShareUtils.putString(this, "name", etUser.getText().toString().trim());
            ShareUtils.putString(this, "password", etPassword.getText().toString().trim());
        } else {
            ShareUtils.deleteShare(this, "name");
            ShareUtils.deleteShare(this, "password");
        }
    }
}
