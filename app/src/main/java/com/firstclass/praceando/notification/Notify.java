package com.firstclass.praceando.notification;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.firstclass.praceando.R;

public class Notify {
    public void execute(Context context) {
        // Create an Intent to open the web page directly
        String url = "https://www.figma.com/board/XEv6PPwWvwZfX3x3hkgjna/Banco-Blomera?node-id=0-1&node-type=canvas&t=0QlF3HEioJFFP30j-0";
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Create a PendingIntent that launches the browser activity
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, webIntent, PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_praceando_id")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Nos ajude!")
                .setContentText("Para melhor experiência, clique aqui para responder a uma pesquisa!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent); // Directly launch the browser when the notification is clicked

        // Create the notification channel
        NotificationChannel channel = new NotificationChannel("channel_praceando_id", "Notificar", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);


        // Check and request POST_NOTIFICATIONS permission if required (Android 13+)
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                // For Android 13+, request the notification permission
                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
            return;
        }

        // Show the notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build());
    }
}
