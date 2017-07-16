package lhp.com.smartbutler.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.adapter.ChatListAdapter;
import lhp.com.smartbutler.entity.ChatListData;
import lhp.com.smartbutler.utils.L;
import lhp.com.smartbutler.utils.SecretKey;
import lhp.com.smartbutler.utils.ShareUtils;

public class ButlerFragment extends Fragment implements View.OnClickListener {
    @InjectView(R.id.mChatListView)
    ListView mChatListView;
    @InjectView(R.id.et_text)
    EditText etText;
    @InjectView(R.id.btn_send)
    Button btnSend;


    private List<ChatListData> mList = new ArrayList<>();
    private ChatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        btnSend.setOnClickListener(this);

        adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String text = etText.getText().toString().trim();
                if (!TextUtils.isEmpty(text)) {
                    if (text.length() > 30) {
                        Toast.makeText(getActivity(), "输入长度超出限制", Toast.LENGTH_SHORT).show();
                    } else {
                        etText.setText("");
                        //添加输入内容到right item
                        addRightItem(text);
                        //发送机器人请求返回内容
                        String url = "http://op.juhe.cn/robot/index?info=" + text
                                + "&key=" + SecretKey.CHAT_LIST_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                L.i(t);
                                parsing(t);
                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.text_toast_empty, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void parsing(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            //拿到返回值
            String text = jsonresult.getString("text");
            //拿到机器人的返回值之后添加在left item
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //添加左边文本
    private void addLeftItem(String text) {

        boolean isSpeak = ShareUtils.getBoolean(getActivity(), "isSpeak", false);
        if (isSpeak) {
//            startSpeak(text);
        }

        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加右边文本
    private void addRightItem(String text) {

        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
