package com.example.hw9.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw9.R;
import com.example.hw9.model.Question;
import com.example.hw9.util.AppConstant;
import com.example.hw9.util.FileWork;
import com.example.hw9.util.TaskFactory;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView currNumQuestionTextView;
    private TextView questionTextView;
    private TextView correctNumAnswerTextView;
    private TextView totalAnswersTextView;
    private ImageButton correctBtn;
    private ImageButton incorrectBtn;
    private List<Question> questions;
    private int currNumQuestion;
    private Question currQuestion;
    private int correctNumAnswer;
    private int totalAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileWork.initializeSharedPreferences(this);

        String res = TaskFactory.writeQuestionsToFile(AppConstant.QUESTIONS_FILENAME, this);
        if (res.isEmpty()) {
            Log.d("DATA_RES", "Res: " + res);
        }

        initWidget();
        firstInit();
        restartWidget();
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeUserGameData();
    }

    public void clickAnswer(View view) {
        boolean userAnswer = view.getId() == R.id.correct_btn;

        if (userAnswer == currQuestion.getAnswer()) correctNumAnswer++;

        totalAnswers++;
        correctNumAnswerTextView.setText("Correct Answers: " + correctNumAnswer);
        totalAnswersTextView.setText("Total Answers: " + totalAnswers);

        nextQuestion();
    }

    public void restartInit(View view) {
        restartInit();
    }

    private void nextQuestion() {
        if (currNumQuestion >= questions.size()) restartInit();

        currNumQuestion++;
        currNumQuestionTextView.setText("Question " + currNumQuestion);

        currQuestion = questions.get(currNumQuestion - 1);
        questionTextView.setText(currQuestion.getQuestion());
    }

    private void writeUserGameData() {
        FileWork.writeIntToSharedPreferences(AppConstant.KEY_NUM_QUESTION_SHARED_PREFERENCES, currNumQuestion);
        FileWork.writeIntToSharedPreferences(AppConstant.KEY_NUM_CORRECT_ANSWER_SHARED_PREFERENCES, correctNumAnswer);
        FileWork.writeIntToSharedPreferences(AppConstant.KEY_TOTAL_ANSWERS_SHARED_PREFERENCES, totalAnswers);
    }

    private void readUserGameData() {
        currNumQuestion = FileWork.readIntFromSharedPreferences(AppConstant.KEY_NUM_QUESTION_SHARED_PREFERENCES, 1);
        correctNumAnswer = FileWork.readIntFromSharedPreferences(AppConstant.KEY_NUM_CORRECT_ANSWER_SHARED_PREFERENCES, 0);
        totalAnswers = FileWork.readIntFromSharedPreferences(AppConstant.KEY_TOTAL_ANSWERS_SHARED_PREFERENCES, 0);
    }

    private void initWidget() {
        currNumQuestionTextView = findViewById(R.id.curr_num_question_text_view);
        questionTextView = findViewById(R.id.question_text_view);
        correctNumAnswerTextView = findViewById(R.id.correct_num_answer_text_view);
        totalAnswersTextView = findViewById(R.id.total_answers_text_view);
        correctBtn = findViewById(R.id.correct_btn);
        incorrectBtn = findViewById(R.id.incorrect_btn);
    }

    private void firstInit() {
        List<String> strQuestions = FileWork.readDataFromFile(AppConstant.QUESTIONS_FILENAME, this);
        questions = TaskFactory.convertStringsToQuestions(strQuestions);
        currQuestion = questions.get(0);

        readUserGameData();
    }

    private void restartInit() {
        Collections.shuffle(questions);

        currNumQuestion = 0;
        currQuestion = questions.get(0);
        correctNumAnswer = 0;
        totalAnswers = 0;

        restartWidget();
    }

    private void restartWidget() {
        currNumQuestionTextView.setText("Question " + currNumQuestion);
        questionTextView.setText(currQuestion.getQuestion());
        correctNumAnswerTextView.setText("Correct Answers: " + correctNumAnswer);
        totalAnswersTextView.setText("Total Answers: " + totalAnswers);
    }
}