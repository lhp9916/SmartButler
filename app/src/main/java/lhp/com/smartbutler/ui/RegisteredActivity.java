package lhp.com.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.entity.MyUser;
import lhp.com.smartbutler.utils.L;

/**
 * Created by lhp on 2017/7/9.
 * description: 注册
 */

public class RegisteredActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.et_user)
    EditText etUser;
    @InjectView(R.id.et_age)
    EditText etAge;
    @InjectView(R.id.et_desc)
    EditText etDesc;
    @InjectView(R.id.rb_boy)
    RadioButton rbBoy;
    @InjectView(R.id.rb_girl)
    RadioButton rbGirl;
    @InjectView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.et_password1)
    EditText etPassword1;
    @InjectView(R.id.et_email)
    EditText etEmail;
    @InjectView(R.id.btn_register)
    Button btnRegister;
    //性别 男=true 女=false
    private boolean sex = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
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
                String name = etUser.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String password1 = etPassword1.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name)
                        & !TextUtils.isEmpty(age)
                        & !TextUtils.isEmpty(password)
                        & !TextUtils.isEmpty(password1)
                        & !TextUtils.isEmpty(email)) {

                    if (!password.equals(password1)) {
                        Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //判断性别
                    mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                            if (i == R.id.rb_boy) {
                                sex = true;
                            } else if (i == R.id.rb_girl) {
                                sex = false;
                            }
                        }
                    });
                    if (TextUtils.isEmpty(desc)) {
                        desc = "这个人很懒，什么都没有留下";
                    }

                    //注册
                    MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.setAge(Integer.parseInt(age));
                    user.setDesc(desc);
                    user.setSex(sex);
                    user.signUp(new SaveListener<Object>() {
                        @Override
                        public void done(Object o, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisteredActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisteredActivity.this, "注册失败 " + e.toString(), Toast.LENGTH_SHORT).show();
                                L.e(e.toString());
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
