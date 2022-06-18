package com.example.myapplication;
//Import android Libraries
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
//Import ThingSpeakAndroid Unofficial ThingSpeak API library for Android by macroyau.
import com.macroyau.thingspeakandroid.ThingSpeakChannel;
import com.macroyau.thingspeakandroid.model.Feed;


public class MainActivity extends AppCompatActivity {

    // Variables and objects declared here
    // Objects of ThingsSpeakChannel API
    private ThingSpeakChannel dChannel; //Object of ThinkSpeak Door Channel
    private ThingSpeakChannel aChannel; //Object of ThinkSpeak AQI Channel
    private ThingSpeakChannel tChannel; //Object of ThinkSpeak Temperature Channel

    private TextView doorST, pmData, airStatus, temData, humData; //Object of Text View
    String ds, tem, hum, as; //Local String Variables

    Handler handler;      //Handler Variables
    Runnable myRunnable; //Runnable Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doorST = findViewById(R.id.textView);
        pmData = findViewById(R.id.pmData);
        airStatus = findViewById(R.id.airStatus);
        temData = findViewById(R.id.TemData);
        humData = findViewById(R.id.HumData);

        //SharedPreferences is local store for data
        SharedPreferences pref1;
        SharedPreferences.Editor editor1; //SharedPreferences object
        pref1 = MainActivity.this.getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor1 = pref1.edit();

        // Connect to ThinkSpeak AQI Channel
        aChannel = new ThingSpeakChannel(1698133);
        // Set listener for Channel AQI feed update events
        aChannel.setFeedUpdateListener(new ThingSpeakChannel.FeedEntryUpdateListener() {
            @Override
            public void onFeedUpdated(long channelId, long entryId, Feed feed) {
                as = feed.getField1();//Store the data from feed object to as
                Log.e(null, "The AQI is " + as);//Print the updated data in log
                //To check as is Null or not
                if (as != null)// In case not null convert as String into Float and check status
                {
                    float pm = Float.parseFloat(as);//Convert as String into Float
                    if (pm <= 50)//If pm is less than or equal to 50 then show “Good”Status to User Interface.
                    {
                        pmData.setText(as);
                        pmData.setBackgroundResource(R.drawable.circleg);
                        airStatus.setText("Good");

                    }
                    else if (pm <= 100)//If pm is less than or equal to 100 then show “Moderate”Status to User Interface.
                    {
                        pmData.setText(as);
                        pmData.setBackgroundResource(R.drawable.circlem);
                        airStatus.setText("Moderate");
                        editor1.putString("s1", "Moderate, be careful.");
                        editor1.commit();
                        startnotif();
                    }
                    else if (pm <= 150)//If pm is less than or equal to 150 then show “Unhealthy for Sensitive Groups”Status to User Interface.
                    {
                        pmData.setText(as);
                        pmData.setBackgroundResource(R.drawable.circleufs);
                        airStatus.setText("Unhealthy for Sensitive Groups");
                        airStatus.setTextSize(15f);
                        editor1.putString("s1", "Unhealthy for Sensitive Groups, be careful, stay home.");
                        editor1.commit();
                        startnotif();
                    }
                    else if (pm <= 200)//If pm is less than or equal to 200 then show “Unhealthy”Status to User Interface.
                    {
                        pmData.setText(as);
                        pmData.setBackgroundResource(R.drawable.circleu);
                        airStatus.setText("Unhealthy");
                        editor1.putString("s1", "Unhealthy, be careful, stay home.");
                        editor1.commit();
                        startnotif();
                    }
                    else if (pm <= 300)//If pm is less than or equal to 300 then show “Very Unhealthy”Status to User Interface.
                    {
                        pmData.setText(as);
                        pmData.setBackgroundResource(R.drawable.circlevu);
                        airStatus.setText("Very Unhealthy");
                        airStatus.setTextSize(23f);
                        editor1.putString("s1", "Very Unhealthy, be careful, stay home.");
                        editor1.commit();
                        startnotif();
                    }
                    else if (pm <= 500)//If pm is less than or equal to 500 then show “Hazardous”Status to User Interface.
                    {
                        pmData.setText(as);
                        pmData.setBackgroundResource(R.drawable.circleh);
                        airStatus.setText("Hazardous");
                        editor1.putString("s1", "Hazardous, be careful, stay home.");
                        editor1.commit();
                        startnotif();
                    }
                }
                else//Else set null to pmData Object
                {
                    pmData.setText("null");
                }
            }
        });
        // Fetch the specific Channel feed
        aChannel.loadLastEntryInChannelFeed();

        handler = new Handler();//update data in app every 5 sec
        myRunnable = new Runnable(){
            @Override
            public void run() {
                //Connect to ThinkSpeak Temperature Channel
                tChannel = new ThingSpeakChannel(1726321);
                //Set listener for Channel Temperature feed update events
                tChannel.setFeedUpdateListener(new ThingSpeakChannel.FeedEntryUpdateListener() {
                    @Override
                    public void onFeedUpdated(long channelId, long entryId, Feed feed) {
                        tem = feed.getField(1);//Store the data from feed object to tem
                        Log.e(null, "The Tem is " + tem);//Print the updated data in log
                        //To check tem is Null or not
                        if (tem != null)// In case not null set tem value in temData Object
                        {
                            temData.setText(tem+"\u2103");
                        }
                        else//Else set null to temData Object
                        {
                            temData.setText("null");
                        }
                        hum = feed.getField(2);//Store the data from feed object to hum
                        Log.e(null, "The Hum is " + hum);//Print the updated data in log
                        //To check hum is Null or not
                        if (hum != null)// In case not null set hum value in humData Object
                        {
                            humData.setText(hum+"%");
                        }
                        else//Else set null to humData Object
                        {
                            humData.setText("null");
                        }
                    }
                });
                tChannel.loadLastEntryInChannelFeed();

                //Connect to ThinkSpeak Door Channel
                dChannel = new ThingSpeakChannel(1649338);
                //Set listener for Channel Door feed update events
                dChannel.setFeedUpdateListener(new ThingSpeakChannel.FeedEntryUpdateListener() {
                    @Override
                    public void onFeedUpdated(long channelId, long entryId, Feed feed) {
                        ds = feed.getField1();//Store the data from feed object to ds
                        Log.e(null, "The feed is " + ds);//Print the updated data in log
                        //To check door status
                        if (ds.equals("1\r\n\r\n" )) //If ds = 1 then door is open
                        {
                            doorST.setText("Open");
                        }
                        else if (ds.equals("0\r\n\r\n")) //Else if ds = 0 then door is close
                        {
                            doorST.setText("Close");
                        }
                        else //No data
                        {
                            doorST.setText("null");
                        }
                    }
                });
                // Fetch the specific Channel feed
                dChannel.loadLastEntryInChannelFeed();

                handler.postDelayed(this, 5000);
            }

        };
        handler.postDelayed(myRunnable, 5000);//End handler

    }
    public void startnotif() {
        Intent intentt = new Intent(MainActivity.this, Exservice.class);

        intentt.putExtra("inputExtra", "Air Pollution Alert");

        ContextCompat.startForegroundService(this, intentt);

    }
    @Override
    public void onBackPressed()//Stop Runnable while app in background
    {
        super.onBackPressed();
        handler.removeCallbacks(myRunnable);
    }
}
