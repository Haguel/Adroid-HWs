package com.example.hw11;

import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTask {
    private TextView statusTextView;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private ExecutorService executorService;
    private Handler handler;
    private boolean isCancelled = false;
    private int currentProgress;

    public MyTask(TextView statusTextView, ProgressBar progressBar, TextView progressTextView, int startProgress) {
        this.statusTextView = statusTextView;
        this.progressBar = progressBar;
        this.progressTextView = progressTextView;
        this.executorService = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
        this.currentProgress = startProgress;
    }

    public void execute() {
        statusTextView.setText("Status: Running");
        executorService.execute(() -> {
            for (int i = currentProgress + 1; i <= 100; i++) {
                if (isCancelled) {
                    handler.post(() -> statusTextView.setText("Status: Cancelled"));
                    return;
                }
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentProgress = i;
                int progress = i;
                handler.post(() -> {
                    progressBar.setProgress(progress);
                    progressTextView.setText(progress + "%");
                    statusTextView.setText("Status: Running");
                });
            }
            handler.post(() -> statusTextView.setText("Status: Finished"));
        });
    }

    public void cancel() {
        isCancelled = true;
        executorService.shutdownNow();
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }
}