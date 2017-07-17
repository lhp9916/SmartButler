package lhp.com.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import lhp.com.smartbutler.R;
import lhp.com.smartbutler.utils.GirlData;
import lhp.com.smartbutler.utils.PicassoUtils;

/**
 * Created by lhp on 2017/7/17.
 * description: 福利适配器
 */

public class GirdAdapter extends BaseAdapter {

    private Context context;
    private List<GirlData> mList;
    private LayoutInflater inflater;//布局加载器
    private GirlData data;

    private WindowManager wm;
    private int width;

    public GirdAdapter(Context context, List<GirlData> mList) {
        this.context = context;
        this.mList = mList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.girl_item, null);
            viewHolder.imageView = view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        data = mList.get(i);
        //拿到图片地址
        String url = data.getImgUrl();

        PicassoUtils.loadImageViewSize(context, url, width / 2, 250, viewHolder.imageView);

        return view;
    }

    //缓存
    class ViewHolder {
        private ImageView imageView;
    }
}
