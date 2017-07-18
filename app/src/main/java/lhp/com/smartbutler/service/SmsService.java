package lhp.com.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;

import lhp.com.smartbutler.utils.L;

/**
 * Created by lhp on 2017/7/18.
 * description: 短信监听服务
 */

public class SmsService extends Service {
    //短信Action
    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private SmsReceiver smsReceiver;
    //发件人号码
    private String smsPhone;
    //短信内容
    private String smsContent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        smsReceiver = new SmsReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SMS_ACTION);
        //设置权限
        intentFilter.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(smsReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销
        unregisterReceiver(smsReceiver);
    }

    //短信广播
    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (SMS_ACTION.equals(action)) {
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                for (Object obj : objs) {
                    SmsMessage fromPdu = SmsMessage.createFromPdu((byte[]) obj);
                    smsPhone = fromPdu.getOriginatingAddress();
                    smsContent = fromPdu.getMessageBody();
                    L.i(smsPhone + ":" + smsContent);
                }
            }
        }
    }
}
