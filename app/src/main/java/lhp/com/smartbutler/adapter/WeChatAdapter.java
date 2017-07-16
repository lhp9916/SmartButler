package lhp.com.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lhp.com.smartbutler.R;
import lhp.com.smartbutler.entity.WeChatData;

/**
 * Created by lhp on 2017/7/16.
 * description: 微信精选
 */

public class WeChatAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<WeChatData> mList;
    private WeChatData data;

    public WeChatAdapter(Context context, List<WeChatData> mList) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mList = mList;
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
            view = inflater.inflate(R.layout.wechat_item, null);
            viewHolder.iv_img = view.findViewById(R.id.iv_img);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.tv_source = view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        data = mList.get(i);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());

        return view;
    }

    class ViewHolder {
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
