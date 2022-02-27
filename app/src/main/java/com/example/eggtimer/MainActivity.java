package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    private SeekBar sb_timer;
    private TextView tv_timer;
    private Button btn_goStop;
    private boolean counterActive = false;

    public void resetTimer() {
        tv_timer.setText("0:00");
        sb_timer.setProgress(0);
        sb_timer.setEnabled(true);
        counterActive = false;
        countDownTimer.cancel();
        btn_goStop.setText("Start");
    }

    public void btn_goStopClicked(View view) {
        if (counterActive) {
            resetTimer();
        } else {
            counterActive = true;
            sb_timer.setEnabled(false);
            btn_goStop.setText("Stopp");
            countDownTimer = new CountDownTimer(sb_timer.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    Log.i("Timer abgelaufen", "Hole die Eier aus dem Wasser.");
                    tv_timer.setText("0:00");
                    sb_timer.setProgress(0);
                    sb_timer.setEnabled(true);
                    counterActive = false;
                    btn_goStop.setText("Start");

                    MediaPlayer myMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.rooster);
                    myMediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        tv_timer.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sb_timer = findViewById(R.id.sb_time);
        tv_timer = findViewById(R.id.tv_timer);
        btn_goStop = findViewById(R.id.btn_goStop);

        sb_timer.setMax(600);
        sb_timer.setProgress(0);

        sb_timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}