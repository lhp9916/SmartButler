package lhp.com.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.adapter.CourierAdapter;
import lhp.com.smartbutler.entity.CourierData;
import lhp.com.smartbutler.utils.SecretKey;

/**
 * Created by lhp on 2017/7/11.
 * description: 物流查询
 */

public class CourierActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.et_number)
    EditText etNumber;
    @InjectView(R.id.lv_courier)
    ListView lvCourier;
    @InjectView(R.id.btn_get_courier)
    Button btnGetCourier;

    private List<CourierData> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        btnGetCourier.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_courier:
                /**
                 * 1.获取输入框内容
                 * 2.判断是否为空
                 * 3.请求数据（json）
                 * 4.解析json
                 * 5.listView适配器
                 * 6.实体类(item)
                 * 7.设置数据并显示
                 */
                String name = etName.getText().toString().trim();
                String number = etNumber.getText().toString().trim();
                //拼接我们的url
                String url = "http://v.juhe.cn/exp/index?key=" + SecretKey.COURIER_KEY
                        + "&com=" + name + "&no=" + number;
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(number)) {
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            parsingJson(t);
                        }
                    });
                } else {
                    Toast.makeText(this, R.string.text_toast_empty, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                CourierData data = new CourierData();
                data.setDatetime(json.getString("datetime"));
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                mList.add(data);
            }
            //倒序
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this, mList);
            lvCourier.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
