package lhp.com.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.utils.SecretKey;

import static lhp.com.smartbutler.R.id.et_number;
import static lhp.com.smartbutler.R.id.iv_company;
import static lhp.com.smartbutler.R.id.tv_result;

/**
 * Created by lhp on 2017/7/13.
 * description: 归属地查询
 */

public class PhoneActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(et_number)
    EditText etNumber;
    @InjectView(iv_company)
    ImageView ivCompany;
    @InjectView(tv_result)
    TextView tvResult;
    @InjectView(R.id.btn_1)
    Button btn1;
    @InjectView(R.id.btn_2)
    Button btn2;
    @InjectView(R.id.btn_3)
    Button btn3;
    @InjectView(R.id.btn_delete)
    Button btnDelete;
    @InjectView(R.id.btn_4)
    Button btn4;
    @InjectView(R.id.btn_5)
    Button btn5;
    @InjectView(R.id.btn_6)
    Button btn6;
    @InjectView(R.id.btn_0)
    Button btn0;
    @InjectView(R.id.btn_7)
    Button btn7;
    @InjectView(R.id.btn_8)
    Button btn8;
    @InjectView(R.id.btn_9)
    Button btn9;
    @InjectView(R.id.btn_search)
    Button btnSearch;

    //标记位
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        //长按清空
        btnDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                etNumber.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        String str = etNumber.getText().toString().trim();
        //键盘逻辑
        switch (view.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:

                if (flag) {
                    flag = false;
                    str = "";
                    etNumber.setText("");
                }

                etNumber.setText(str + ((Button) view).getText());
                //移动光标
                etNumber.setSelection(str.length() + 1);
                break;
            case R.id.btn_delete:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    etNumber.setText(str.substring(0, str.length() - 1));
                    etNumber.setSelection(str.length() - 1);
                }
                break;
            case R.id.btn_search:
                getPhone(str);
                break;
        }

    }

    private void getPhone(String str) {
        String url = "http://apis.juhe.cn/mobile/get?phone=" + str + "&key=" + SecretKey.PHONE_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");

            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            String card = jsonResult.getString("card");

            tvResult.setText("归属地:" + province + city + "\n"
                    + "区号:" + areacode + "\n"
                    + "邮编:" + zip + "\n"
                    + "运营商:" + company + "\n"
                    + "类型:" + card);


            //图片显示
            switch (company) {
                case "移动":
                    ivCompany.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    ivCompany.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    ivCompany.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }
            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
