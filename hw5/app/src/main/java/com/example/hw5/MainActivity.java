package com.example.hw5;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<UserModel> users = UserGenerator.generateUsers(100);
        UserAdapter adapter = new UserAdapter(this, users);

        ListView listView = findViewById(R.id.user_list);
        listView.setAdapter(adapter);
    }
}