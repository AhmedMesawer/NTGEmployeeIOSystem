package com.example.ilias.ntgemployeeiosystem.in_out;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import static com.example.ilias.ntgemployeeiosystem.in_out.MainActivity.YOU_CAN_GO_NOW;

/**
 * Created by ilias on 26/02/2018.
 */

public class TimerService extends IntentService {

    public static final String RESULT_RECEIVER_INTENT_KEY = "resultReceiver";
    boolean youCanGoNow = false;
    ResultReceiver serviceResult;

    public TimerService() {
        super("TimerService");
    }

    CountDownTimer timer = new CountDownTimer(30 * 1000, 1000) {
        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            youCanGoNow = true;
            Bundle bundle = new Bundle();
            bundle.putBoolean(YOU_CAN_GO_NOW, youCanGoNow);
            serviceResult.send(0, bundle);
        }
    };

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            serviceResult = intent.getParcelableExtra(RESULT_RECEIVER_INTENT_KEY);
        }
        timer.start();
    }
}
