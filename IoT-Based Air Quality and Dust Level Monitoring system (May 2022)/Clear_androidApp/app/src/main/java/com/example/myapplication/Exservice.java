package com.example.myapplication;
//Import Libraries
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static  com.example.myapplication.App.CHANNEL_ID;

public class Exservice extends Service {
    @Override
    public void onCreate() { super.onCreate(); }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        SharedPreferences pref1 ;
        SharedPreferences.Editor editor1;
        pref1 =  Exservice.this.getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor1 = pref1.edit();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Air Pollution Alert")// notification Title
                .setSmallIcon(android.R.drawable.stat_sys_warning)// notification icon
                .setContentText(pref1.getString("s1","ok") )// notification Content
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        stopForeground(false);

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}