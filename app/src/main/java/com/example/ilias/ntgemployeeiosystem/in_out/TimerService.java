package com.example.ilias.ntgemployeeiosystem.in_out;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.ilias.ntgemployeeiosystem.R;

import static com.example.ilias.ntgemployeeiosystem.in_out.MainActivity.YOU_CAN_GO_NOW;

/**
 * Created by ilias on 26/02/2018.
 */

public class TimerService extends Service {

    public static final String RESULT_RECEIVER_INTENT_KEY = "resultReceiver";
    private boolean youCanGoNow = false;
    ResultReceiver serviceResult;

    CountDownTimer timer = new CountDownTimer(15 * 1000, 1000) {
        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            SharedPreferences preferences =
                    getSharedPreferences("MainSharedPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(YOU_CAN_GO_NOW, true);
            editor.apply();
            youCanGoNow = true;
            Bundle bundle = new Bundle();
            bundle.putBoolean(YOU_CAN_GO_NOW, true);
            if (serviceResult != null) {
                serviceResult.send(0, bundle);
            } else {
                notifyEmployee();
            }
            stopForeground(true);
            stopSelf();
        }
    };

    private void notifyEmployee() {
        Intent intent = new Intent(TimerService.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(YOU_CAN_GO_NOW, youCanGoNow);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(TimerService.this, 0, intent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(TimerService.this, "0")
                        .setSmallIcon(R.drawable.exit)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.exit))
                        .setContentTitle("امضي انصراف")
                        .setContentText("فات 6 ساعات من ساعة حضورك ممكن تمشي دلوقتي وتعوض الباقي يوم تاني")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(0, builder.build());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            serviceResult = intent.getParcelableExtra(RESULT_RECEIVER_INTENT_KEY);
        }
        timer.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
