package com.myapp.demo.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.myapp.demo.R;

import static android.R.attr.id;

/**
 * a class which manage views of notifications
 */

public class NotificationManagerUtility {
    private final Context context;

    public NotificationManagerUtility(Context context, String string) {
        this.context = context;

        showNotification(string);
    }



    //Method to show notification;
    private void showNotification(String message) {
        Intent broadcastIntent = new Intent("NotificationReceived");
        context.sendBroadcast(broadcastIntent);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSound(defaultSoundUri);
        notificationBuilder.setColor(ContextCompat.getColor(context,R.color.colorAlertBackground));
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder.setSmallIcon(getNotificationIcon())
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true);
        notificationManager.notify(id, notificationBuilder.build());

    }

    /**
     *Method which shows icon in notification for now i have implemented
     * ic launcher icon for same for both android greater than lollipop and less than lollipop.
     *
     * @return ; returns the drawable according to the android version
     */
    private int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }
}

