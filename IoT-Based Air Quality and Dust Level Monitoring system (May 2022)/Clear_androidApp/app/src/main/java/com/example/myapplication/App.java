package com.example.myapplication;
//Import Libraries
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
public class App extends Application {
    public static final String CHANNEL_ID = "notifyID"; //CHANNEL_ID public String Variable
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();//Call method to create notification channel
    }
    //Method to create notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)//check SDK version
        {   //Creating notification channel named AirPollution
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "AirPollution",
                    NotificationManager.IMPORTANCE_DEFAULT //IMPORTANCE_DEFAULT to shows everywhere, and makes noise.
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}