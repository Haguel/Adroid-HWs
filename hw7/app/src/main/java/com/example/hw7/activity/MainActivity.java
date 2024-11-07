package com.example.hw7.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hw7.R;
import com.example.hw7.model.Car;
import com.example.hw7.repository.CarRepository;
import com.example.hw7.utils.CarAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> searchActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button searchButton = findViewById(R.id.search_button);

        searchActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        List<Car> searchResults = result.getData().getParcelableArrayListExtra("search_results");
                        displayCars(searchResults);
                    }
                }
        );

        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            searchActivityResultLauncher.launch(intent);
        });

        if (getIntent().hasExtra("search_results")) {
            List<Car> cars = getIntent().getParcelableArrayListExtra("search_results");
            displayCars(cars);
        } else {
            displayCars(CarRepository.getCars());
        }
    }

    private void displayCars(List<Car> cars) {
        CarAdapter adapter = new CarAdapter(this, cars);
        ListView listView = findViewById(R.id.car_list_view);
        listView.setAdapter(adapter);
    }
}