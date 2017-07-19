package lhp.com.smartbutler.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;
import lhp.com.smartbutler.utils.L;

import static lhp.com.smartbutler.R.id.number_progress_bar;

/**
 * Created by lhp on 2017/7/19.
 * description: 下载更新
 */

public class UpdateActivity extends BaseActivity {
    //下载中
    private static final int HANDLER_LOADING = 10001;
    //下载完成
    private static final int HANDLER_OK = 10002;
    //下载失败
    private static final int HANDLER_NO = 10003;

    @InjectView(R.id.tv_size)
    TextView tvSize;
    @InjectView(number_progress_bar)
    com.daimajia.numberprogressbar.NumberProgressBar numberProgressBar;

    private String url;
    private String path;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_LOADING:
                    Bundle bundle = msg.getData();
                    long transferredBytes = bundle.getLong("transferredBytes");
                    long totalSize = bundle.getLong("totalSize");
                    tvSize.setText(transferredBytes + "/" + totalSize);

                    //设置进度
                    numberProgressBar.setProgress((int) (((float) transferredBytes / (float) totalSize) * 100));

                    break;
                case HANDLER_OK:
                    startInstallApk();
                    tvSize.setText("下载成功");
                    break;
                case HANDLER_NO:
                    tvSize.setText("下载失败");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        numberProgressBar.setMax(100);

        url = getIntent().getStringExtra("url");

        path = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".apk";

        if (!TextUtils.isEmpty(url)) {
            //需地址支持断点续传
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    Message msg = new Message();
                    msg.what = HANDLER_LOADING;
                    Bundle bundle = new Bundle();
                    bundle.putLong("transferredBytes", transferredBytes);
                    bundle.putLong("totalSize", totalSize);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    handler.sendEmptyMessage(HANDLER_OK);
                }

                @Override
                public void onFailure(VolleyError error) {
                    L.e(error.toString());
                    handler.sendEmptyMessage(HANDLER_NO);
                }
            });
        }
    }

    //启动安装
    private void startInstallApk() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        startActivity(i);
        finish();
    }
}
