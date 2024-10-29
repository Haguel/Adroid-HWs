package com.example.hw2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        randomNumber = new Random().nextInt(100) + 1;

        EditText editTextNumber = findViewById(R.id.editTextNumber);
        Button guessButton = findViewById(R.id.button_guess);
        TextView resultTextView = findViewById(R.id.textViewResult);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userGuess = Integer.parseInt(editTextNumber.getText().toString());
                if (userGuess < randomNumber) {
                    resultTextView.setText("Too low!");
                } else if (userGuess > randomNumber) {
                    resultTextView.setText("Too high!");
                } else {
                    resultTextView.setText("Correct!");
                }
            }
        });
    }
}