package lhp.com.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lhp.com.smartbutler.R;
import lhp.com.smartbutler.adapter.WeChatAdapter;
import lhp.com.smartbutler.entity.WeChatData;
import lhp.com.smartbutler.ui.WebViewActivity;
import lhp.com.smartbutler.utils.SecretKey;

/**
 * Created by lhp on 2017/7/8.
 */

public class WechatFragment extends Fragment {

    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();

    private List<String> mListTitle = new ArrayList<>();
    private List<String> mListUrl = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mListView = view.findViewById(R.id.mListView);
        //请求接口
        String url = "http://v.juhe.cn/weixin/query?key=" + SecretKey.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //L.i(t);
                parsing(t);
            }
        });

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("url", mListUrl.get(position));
                startActivity(intent);
            }
        });
    }

    private void parsing(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                WeChatData data = new WeChatData();
                data.setTitle(json.getString("title"));
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));
                mList.add(data);
                mListTitle.add(json.getString("title"));
                mListUrl.add(json.getString("url"));
                WeChatAdapter adapter = new WeChatAdapter(getActivity(), mList);
                mListView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}