package lhp.com.smartbutler.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 统一工具类
 */

public class UtilTools {
    //设置字体
    public static void setFont(Context mContext, TextView textView) {
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    /**
     * 保存图片到SharePreference
     *
     * @param mContext
     * @param imageView
     */
    public static void putImageToShare(Context mContext, ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //将Bitmap压缩成字节流数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //利用Base64将我们的字节数组输出流转换成String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imgString = new String(Base64.encode(byteArray, Base64.DEFAULT));
        //将String保存到SharePreference
        ShareUtils.putString(mContext, "image_title", imgString);
    }

    /**
     * 读取图片
     *
     * @param context
     * @param imageView
     */
    public static void getImageFromShare(Context context, ImageView imageView) {
        String imgString = ShareUtils.getString(context, "image_title", "");
        if (!imgString.equals("")) {
            //Base64解码
            byte[] bytes = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(bytes);
            //生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }

    //获取版本号
    public static String getVersion(Context mContext){
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "未知";
        }
    }
}
