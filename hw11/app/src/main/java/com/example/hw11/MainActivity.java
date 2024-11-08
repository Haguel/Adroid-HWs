package com.example.hw11;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MyTask myTask;
    private Button startButton;
    private Button cancelButton;
    private TextView statusTextView;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private int savedProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = findViewById(R.id.statusTextView);
        progressBar = findViewById(R.id.progressBar);
        progressTextView = findViewById(R.id.progressTextView);
        startButton = findViewById(R.id.startButton);
        cancelButton = findViewById(R.id.cancelButton);

        startButton.setOnClickListener(v -> {
            if (myTask == null || myTask.isCancelled()) {
                myTask = new MyTask(statusTextView, progressBar, progressTextView, savedProgress);
                myTask.execute();
                startButton.setText("Stop");
                cancelButton.setVisibility(View.VISIBLE);
            } else {
                savedProgress = myTask.getCurrentProgress();
                myTask.cancel();
                startButton.setText("Continue");
                cancelButton.setVisibility(View.GONE);
            }
        });

        cancelButton.setOnClickListener(v -> {
            if (myTask != null) {
                myTask.cancel();
                savedProgress = 0;
                progressBar.setProgress(0);
                progressTextView.setText("0%");
                statusTextView.setText("Status: Pending");
                startButton.setText("Start");
                cancelButton.setVisibility(View.GONE);
            }
        });
    }
}