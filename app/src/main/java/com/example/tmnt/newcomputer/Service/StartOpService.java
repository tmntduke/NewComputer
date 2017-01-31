package com.example.tmnt.newcomputer.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.example.tmnt.newcomputer.Utils.Utils;

/**
 * Created by tmnt on 2016/7/22.
 */
public class StartOpService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, UpdateDataService.class);

        long time = SystemClock.elapsedRealtime() + 3 * 60 * 60 * 1000;

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);

        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5 * 60 * 1000, time, pendingIntent);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, StartOpService.class);
        startService(intent);
    }
}
