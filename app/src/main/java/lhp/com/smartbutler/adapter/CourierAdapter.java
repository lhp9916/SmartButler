package lhp.com.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lhp.com.smartbutler.R;
import lhp.com.smartbutler.entity.CourierData;

/**
 * Created by lhp on 2017/7/11.
 * description: 物流查询适配器
 */

public class CourierAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourierData> mList;
    private CourierData data;
    //布局加载器
    private LayoutInflater inflater;

    public CourierAdapter(Context mContext, List<CourierData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //第一次加载
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_courier_item, null);
            viewHolder.tv_remark = view.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = view.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime = view.findViewById(R.id.tv_datetime);
            //设置缓存
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //设置数据
        data = mList.get(i);
        viewHolder.tv_datetime.setText(data.getDatetime());
        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());
        return view;
    }

    class ViewHolder {
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;
    }
}
