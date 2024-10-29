package com.example.hw1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private long onCreateTime, onStartTime, onResumeTime, onPauseTime, onStopTime, onDestroyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateTime = System.currentTimeMillis();

        Log.d(TAG, "onCreate called at: " + onCreateTime);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> Log.d(TAG, "Button was clicked"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        onStartTime = System.currentTimeMillis();

        Log.d(TAG, "onStart called at: " + onStartTime + ", difference: " + (onStartTime - onCreateTime) + " ms");
    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeTime = System.currentTimeMillis();

        Log.d(TAG, "onResume called at: " + onResumeTime + ", difference: " + (onResumeTime - onStartTime) + " ms");
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseTime = System.currentTimeMillis();

        Log.d(TAG, "onPause called at: " + onPauseTime + ", difference: " + (onPauseTime - onResumeTime) + " ms");
    }

    @Override
    protected void onStop() {
        super.onStop();
        onStopTime = System.currentTimeMillis();

        Log.d(TAG, "onStop called at: " + onStopTime + ", difference: " + (onStopTime - onPauseTime) + " ms");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyTime = System.currentTimeMillis();

        Log.d(TAG, "onDestroy called at: " + onDestroyTime + ", difference: " + (onDestroyTime - onStopTime) + " ms");
    }
}