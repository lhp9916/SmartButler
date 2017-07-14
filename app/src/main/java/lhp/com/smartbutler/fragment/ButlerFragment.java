package lhp.com.smartbutler.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.adapter.ChatListAdapter;
import lhp.com.smartbutler.entity.ChatListData;

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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String text = etText.getText().toString().trim();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
