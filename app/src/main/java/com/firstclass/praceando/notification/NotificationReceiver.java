package com.firstclass.praceando.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Create an Intent to open the web URL
        Intent webIntent = new Intent(Intent.ACTION_VIEW);

        // Add FLAG_ACTIVITY_NEW_TASK to allow starting an activity from a BroadcastReceiver
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Check if there's an app that can handle this Intent (i.e., a browser)
        if (webIntent.resolveActivity(context.getPackageManager()) != null) {
            // Launch the Intent
            context.startActivity(webIntent);
        }
    }
}
