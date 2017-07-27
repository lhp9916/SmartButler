package lhp.com.smartbutler.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.R;

/**
 * Created by lhp on 2017/7/27.
 * description: 二维码生成
 */

public class QrCodeActivity extends BaseActivity {
    @InjectView(R.id.iv_qr_code)
    ImageView ivQrCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        //屏幕宽
        int width = getResources().getDisplayMetrics().widthPixels;

        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是智能管家", width / 2, width / 2,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        ivQrCode.setImageBitmap(qrCodeBitmap);
    }
}
