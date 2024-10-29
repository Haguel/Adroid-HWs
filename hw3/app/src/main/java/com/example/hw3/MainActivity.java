package com.example.hw3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private StringBuilder currentInput = new StringBuilder();
    private double operand1 = 0;
    private double operand2 = 0;
    private String operator = "";
    private boolean operatorEntered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String buttonText = button.getText().toString();
                handleInput(buttonText);
            }
        };

        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSubtract,
                R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonDot, R.id.buttonEquals
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void handleInput(String input) {
        switch (input) {
            case "+":
            case "-":
            case "*":
            case "/":
                if (currentInput.length() > 0 && !operatorEntered) {
                    operator = input;
                    operand1 = Double.parseDouble(currentInput.toString());

                    currentInput.append(" ").append(operator).append(" ");
                    display.setText(currentInput.toString());
                    operatorEntered = true;
                }

                break;
            case "=":
                if (currentInput.length() > 0 && operatorEntered) {
                    String[] parts = currentInput.toString().split(" ");

                    if (parts.length == 3) {
                        operand2 = Double.parseDouble(parts[2]);
                        double result = calculateResult();

                        display.setText(String.valueOf(result));
                        currentInput.setLength(0);
                        currentInput.append(result);
                        operatorEntered = false;
                    }
                }

                break;
            default:
                currentInput.append(input);
                display.setText(currentInput.toString());

                break;
        }
    }

    private double calculateResult() {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                return 0;
        }
    }
}