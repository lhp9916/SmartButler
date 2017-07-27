package lhp.com.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.utils.UtilTools;

/**
 * Created by lhp on 2017/7/27.
 * description: 关于软件
 */

public class AboutActivity extends BaseActivity {
    @InjectView(R.id.profile_image)
    CircleImageView profileImage;
    @InjectView(R.id.mListView)
    ListView mListView;

    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);

        //去除阴影
        getSupportActionBar().setElevation(0);

        initView();
    }

    private void initView() {
        mList.add("应用名：" + getString(R.string.app_name));
        mList.add("版本：" + UtilTools.getVersion(this));

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
    }
}
