package com.example.countdown_timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView timerText;
    private Button startStopButton;
    private EditText timeInput;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;
    private long timeLeftInMs = 60000;
    private long startTimeInMs = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timer_text);
        startStopButton = findViewById(R.id.start_stop_button);
        Button resetButton = findViewById(R.id.reset_button);
        timeInput = findViewById(R.id.time_input);

        startStopButton.setOnClickListener(v -> {
            if (timerRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });
        resetButton.setOnClickListener(v -> resetTimer());
        updateCountDownText();
    }

    private void startTimer() {
        String inputTime = timeInput.getText().toString();
        if (!inputTime.isEmpty()) {
            long inputMinutes = Long.parseLong(inputTime);
            startTimeInMs = inputMinutes * 60000;
            timeLeftInMs = startTimeInMs;
        }

        countDownTimer = new CountDownTimer(timeLeftInMs, 1000) {
            @Override
            public void onTick(long msUntilFinished) {
                timeLeftInMs = msUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startStopButton.setText("Start");
                startStopButton.setEnabled(true);
                timeInput.setEnabled(true);
                Toast.makeText(MainActivity.this, "Timer finished!", Toast.LENGTH_SHORT).show();
            }
        }.start();

        timerRunning = true;
        startStopButton.setText("Stop");
        timeInput.setEnabled(false);
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        startStopButton.setText("Start");
        timeInput.setEnabled(true);
    }

    private void resetTimer() {
        timeLeftInMs = startTimeInMs;
        updateCountDownText();
        if (timerRunning) {
            stopTimer();
        }
        timeInput.setEnabled(true);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMs / 1000) / 60;
        int seconds = (int) (timeLeftInMs / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(timeLeftFormatted);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerRunning) {
            stopTimer();
        }
    }
}