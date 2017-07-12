package lhp.com.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.entity.MyUser;
import lhp.com.smartbutler.ui.CourierActivity;
import lhp.com.smartbutler.ui.LoginActivity;
import lhp.com.smartbutler.view.CustomDialog;

/**
 * Created by lhp on 2017/7/9.
 * description：个人中心
 */

public class UserFragment extends Fragment implements View.OnClickListener {
    @InjectView(R.id.tv_edit_user)
    TextView tvEditUser;
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_sex)
    EditText etSex;
    @InjectView(R.id.et_age)
    EditText etAge;
    @InjectView(R.id.et_desc)
    EditText etDesc;
    @InjectView(R.id.btn_update_ok)
    Button btnUpdateOk;
    @InjectView(R.id.tv_courier)
    TextView tvCourier;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.btn_exit_user)
    Button btnExitUser;
    @InjectView(R.id.profile_image)
    CircleImageView profileImage;
    //圆形图像
    private CircleImageView profile_image;
    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        btnExitUser.setOnClickListener(this);
        tvEditUser.setOnClickListener(this);
        btnUpdateOk.setOnClickListener(this);
        tvCourier.setOnClickListener(this);
        profileImage.setOnClickListener(this);

        dialog = new CustomDialog(getActivity(), 0, 0,
                R.layout.dialog_photo, R.style.pop_anim_style, Gravity.BOTTOM, 0);
        //屏幕外点击无效
        dialog.setCancelable(false);
        btn_camera = dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        //默认不可点击
        setEnable(false);
        //设置具体值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        etUsername.setText(userInfo.getUsername());
        etSex.setText(userInfo.isSex() ? "男" : "女");
        etAge.setText(userInfo.getAge() + "");
        etDesc.setText(userInfo.getDesc());
    }

    private void setEnable(boolean is) {
        etUsername.setEnabled(is);
        etSex.setEnabled(is);
        etAge.setEnabled(is);
        etDesc.setEnabled(is);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //退出登录
            case R.id.btn_exit_user:
                //清除缓存用户对象
                MyUser.logOut();
                //现在的getCurrentUser就为null
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            //编辑资料
            case R.id.tv_edit_user:
                setEnable(true);
                btnUpdateOk.setVisibility(View.VISIBLE);
                break;
            //修改用户信息
            case R.id.btn_update_ok:
                String username = etUsername.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String sex = etSex.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();

                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)) {
                    //更新用户
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }

                    if (!TextUtils.isEmpty(desc)) {
                        user.setDesc(desc);
                    } else {
                        user.setDesc("这个人很懒，什么都没有留下");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setEnable(false);
                                btnUpdateOk.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), R.string.text_toast_empty, Toast.LENGTH_SHORT).show();
                }

                break;
            //物流查询
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            //上传头像
            case R.id.profile_image:
                dialog.show();
                break;
            //取消按钮
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            //拍照
            case R.id.btn_camera:
                break;
            //相册
            case R.id.btn_picture:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
