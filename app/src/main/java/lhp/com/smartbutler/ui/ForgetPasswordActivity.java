package lhp.com.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.entity.MyUser;

/**
 * Created by lhp on 2017/7/10.
 * description: 修改/重置密码
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.et_now)
    EditText etNow;
    @InjectView(R.id.et_new)
    EditText etNew;
    @InjectView(R.id.et_new_password)
    EditText etNewPassword;
    @InjectView(R.id.btn_update_password)
    Button btnUpdatePassword;
    @InjectView(R.id.et_email)
    EditText etEmail;
    @InjectView(R.id.btn_forget_password)
    Button btnForgetPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        btnUpdatePassword.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //修改密码
            case R.id.btn_update_password:
                String now = etNow.getText().toString().trim();
                String newPass = etNew.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(now) & !TextUtils.isEmpty(newPass) & !TextUtils.isEmpty(newPassword)) {
                    if (newPass.equals(newPassword)) {
                        MyUser.updateCurrentUserPassword(now, newPass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgetPasswordActivity.this, R.string.reset_successfully, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, R.string.reset_failed, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, R.string.text_two_input_not_consistent, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.text_toast_empty, Toast.LENGTH_SHORT).show();
                }
                break;
            //重置密码
            case R.id.btn_forget_password:
                final String email = etEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this, getString(R.string.text_email_send_ok) + email, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this, R.string.text_email_send_no, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, R.string.text_toast_empty, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
