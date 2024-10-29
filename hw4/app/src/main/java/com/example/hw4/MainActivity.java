package com.example.hw4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etAge;
    private SeekBar sbSalary;
    private TextView tvSalary, tvResult;
    private Button btnSubmit;
    private CheckBox cbExperience, cbTeamSkills, cbTravel;
    private RadioGroup[] questionGroups = new RadioGroup[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        sbSalary = findViewById(R.id.sbSalary);
        tvSalary = findViewById(R.id.tvSalary);
        tvResult = findViewById(R.id.tvResult);
        btnSubmit = findViewById(R.id.btnSubmit);
        cbExperience = findViewById(R.id.cbExperience);
        cbTeamSkills = findViewById(R.id.cbTeamSkills);
        cbTravel = findViewById(R.id.cbTravel);

        for (int i = 0; i < 5; i++) {
            int resId = getResources().getIdentifier("rgQuestion" + (i + 1), "id", getPackageName());
            questionGroups[i] = findViewById(resId);
        }

        sbSalary.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSalary.setText("Зарплатня: " + progress + " USD");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnSubmit.setOnClickListener(v -> validateAndCalculateScore());
    }

    private void validateAndCalculateScore() {
        String name = etName.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        int age = Integer.parseInt(ageStr);
        int salary = sbSalary.getProgress();

        if (name.isEmpty() || ageStr.isEmpty() || age < 21 || age > 40 || salary < 50000 || salary > 100000) {
            tvResult.setText("Дані не відповідають вимогам компанії.");
            tvResult.setVisibility(View.VISIBLE);
            return;
        }

        int score = 0;

        if (questionGroups[0].getCheckedRadioButtonId() == R.id.correctAnswerQ1) score += 2;
        if (questionGroups[1].getCheckedRadioButtonId() == R.id.correctAnswerQ2) score += 2;
        if (questionGroups[2].getCheckedRadioButtonId() == R.id.correctAnswerQ3) score += 2;
        if (questionGroups[3].getCheckedRadioButtonId() == R.id.correctAnswerQ4) score += 2;
        if (questionGroups[4].getCheckedRadioButtonId() == R.id.correctAnswerQ5) score += 2;

        if (cbExperience.isChecked()) score += 2;
        if (cbTeamSkills.isChecked()) score += 1;
        if (cbTravel.isChecked()) score += 1;

        if (score >= 10) {
            tvResult.setText("Вітаємо! Ви пройшли тест");
        } else {
            tvResult.setText("На жаль, ви не пройшли тест.");
        }

        tvResult.setVisibility(View.VISIBLE);
    }
}