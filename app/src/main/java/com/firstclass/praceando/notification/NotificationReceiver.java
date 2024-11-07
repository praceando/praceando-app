package com.firstclass.praceando.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (webIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(webIntent);
        }
    }
}
