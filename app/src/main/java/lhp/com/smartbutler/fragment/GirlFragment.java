package lhp.com.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.adapter.GirdAdapter;
import lhp.com.smartbutler.utils.GirlData;
import lhp.com.smartbutler.utils.PicassoUtils;
import lhp.com.smartbutler.view.CustomDialog;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by lhp on 2017/7/8.
 * 福利
 */

public class GirlFragment extends Fragment {

    private static final String url = "http://gank.io/api/search/query/listview/category/%E7%A6%8F%E5%88%A9/count/50/page/1";

    private List<GirlData> mList = new ArrayList<>();
    private GirdAdapter adapter;

    private List<String> urlList = new ArrayList<>();

    private CustomDialog dialog;
    private ImageView imageView;//预览图片
    //PhotoView
    private PhotoViewAttacher mAttacher;

    @InjectView(R.id.mGrid)
    GridView mGrid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl,
                R.style.pop_anim_style, Gravity.CENTER);
        imageView = dialog.findViewById(R.id.iv_img);

        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsing(t);
                //L.i(t);
            }
        });

        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = urlList.get(i);
                PicassoUtils.loadImageView(getActivity(), url, imageView);
                //缩放
                mAttacher = new PhotoViewAttacher(imageView);
                //刷新
                mAttacher.update();
                dialog.show();
            }
        });
    }

    private void parsing(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonResults = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject json = (JSONObject) jsonResults.get(i);
                String url = json.getString("url");
                urlList.add(url);
                GirlData data = new GirlData();
                data.setImgUrl(url);
                mList.add(data);
            }
            adapter = new GirdAdapter(getActivity(), mList);
            mGrid.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
