package com.example.kankan.findpg;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Notification extends FirebaseMessagingService{

    public Notification() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        setNotification(remoteMessage.getNotification().getBody());
    }

    public void setNotification(String message)
    {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setContentText(message);
        builder.setContentTitle("PG Finder");
        builder.setSmallIcon(R.drawable.home);
        builder.setAutoCancel(true);
        Intent intent=new Intent(this,ShowData.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,2134,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify("My ofi",12,builder.build());
    }
}
