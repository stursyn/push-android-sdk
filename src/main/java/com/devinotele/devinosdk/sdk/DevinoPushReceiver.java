package com.devinotele.devinosdk.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DevinoPushReceiver extends BroadcastReceiver {

    static final String KEY_DEEPLINK = "deepLink";
    static final String KEY_PICTURE = "picture";
    static String KEY_DEFAULT_ACTION = "devino://default-push-action";
    static final String KEY_PUSH_ID = "pushId";
    static final String KEY_OPTIONS = "options";


    @Override
    public void onReceive(Context context, Intent intent) {

        DevinoSdk.getInstance().hideNotification(context);

        String deepLink = intent.getStringExtra(KEY_DEEPLINK);
        String picture = intent.getStringExtra(KEY_PICTURE);
        String pushId = intent.getStringExtra(KEY_PUSH_ID);
        String options = intent.getStringExtra(KEY_OPTIONS);

        Intent startMain = new Intent(Intent.ACTION_VIEW);

        try {
            startMain.setData(Uri.parse(deepLink));
            startMain.putExtra(KEY_PICTURE, picture);
            startMain.putExtra(KEY_OPTIONS, options);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(startMain);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        DevinoSdk.getInstance().pushEvent(pushId, DevinoSdk.PushStatus.OPENED, deepLink);
    }
}
