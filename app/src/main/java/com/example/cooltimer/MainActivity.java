package com.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView;
    private boolean isTimerOn;
    private Button button;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.timerSeekBar);
        textView = findViewById(R.id.TimerTextView);
        button = findViewById(R.id.startButton);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        isTimerOn = false;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                long progressInMillis = i*1000;
                updateTime(progressInMillis);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void start(View view) {
        if(!isTimerOn){
            button.setText("STOP");
            seekBar.setEnabled(false);
            isTimerOn = true;

            countDownTimer = new CountDownTimer(seekBar.getProgress()*1000,1000) {
                @Override
                public void onTick(long l) {
                    updateTime(l);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.budilnik);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
        }else{
            resetTimer();
        }

    }

    private void updateTime(long l){
        int minutes =(int) l/1000/60;
        int seconds = (int) l/1000-(minutes*60);

        String minutesString = "";
        String secondString = "";

        if(minutes<10){
            minutesString = "0"+minutes;
        }else{
            minutesString = String.valueOf(minutes);
        }

        if(seconds<10){
            secondString = "0"+seconds;
        }else{
            secondString = String.valueOf(seconds);
        }

        textView.setText(minutesString+":"+secondString);
    }

    private void resetTimer(){
        countDownTimer.cancel();
        button.setText("START");
        textView.setText("00:30");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        isTimerOn = false;
    }
}

