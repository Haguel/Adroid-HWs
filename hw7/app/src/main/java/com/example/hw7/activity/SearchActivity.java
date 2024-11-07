package com.example.hw7.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw7.R;
import com.example.hw7.model.Car;
import com.example.hw7.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView brandTextView;
    private AutoCompleteTextView modelTextView;
    private Spinner yearFromSpinner;
    private Spinner yearToSpinner;
    private EditText costFromEditText;
    private EditText costToEditText;
    private Button matchesButton;
    private TextView matchesCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        brandTextView = findViewById(R.id.brand_auto_complete);
        modelTextView = findViewById(R.id.model_auto_complete);
        yearFromSpinner = findViewById(R.id.year_from_spinner);
        yearToSpinner = findViewById(R.id.year_to_spinner);
        costFromEditText = findViewById(R.id.cost_from_edit_text);
        costToEditText = findViewById(R.id.cost_to_edit_text);
        matchesButton = findViewById(R.id.matches_button);
        matchesCountTextView = findViewById(R.id.matches_count_text_view);

        setupAutoCompleteTextViews();
        setupYearSpinners();
        setupCostEditTexts();

        matchesButton.setOnClickListener(v -> returnSearchResults());

        search();
    }

    private void setupAutoCompleteTextViews() {
        Set<String> brands = CarRepository.getCars().stream().map(Car::getBrand).collect(Collectors.toSet());
        Set<String> models = CarRepository.getCars().stream().map(Car::getModel).collect(Collectors.toSet());
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>(brands));
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>(models));

        brandTextView.setAdapter(brandAdapter);
        modelTextView.setAdapter(modelAdapter);
        brandTextView.addTextChangedListener(new SearchTextWatcher());
        modelTextView.addTextChangedListener(new SearchTextWatcher());
    }

    private void setupYearSpinners() {
        Set<Integer> years = CarRepository.getCars().stream().map(Car::getYear).collect(Collectors.toSet());
        List<Integer> yearList = new ArrayList<>(years);

        yearList.sort(Integer::compareTo);

        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearFromSpinner.setAdapter(yearAdapter);
        yearToSpinner.setAdapter(yearAdapter);
        yearFromSpinner.setOnItemSelectedListener(new SearchItemSelectedListener());
        yearToSpinner.setOnItemSelectedListener(new SearchItemSelectedListener());
    }

    private void setupCostEditTexts() {
        costFromEditText.addTextChangedListener(new SearchTextWatcher());
        costToEditText.addTextChangedListener(new SearchTextWatcher());
    }

    private void search() {
        String brand = brandTextView.getText().toString();
        String model = modelTextView.getText().toString();
        Integer yearFrom = yearFromSpinner.getSelectedItem() != null ? (Integer) yearFromSpinner.getSelectedItem() : null;
        Integer yearTo = yearToSpinner.getSelectedItem() != null ? (Integer) yearToSpinner.getSelectedItem() : null;
        Double costFrom = !costFromEditText.getText().toString().isEmpty() ? Double.parseDouble(costFromEditText.getText().toString()) : null;
        Double costTo = !costToEditText.getText().toString().isEmpty() ? Double.parseDouble(costToEditText.getText().toString()) : null;

        if (brand.isEmpty() && model.isEmpty() && yearFrom == null && yearTo == null && costFrom == null && costTo == null) {
            matchesCountTextView.setText("Matches found: 0");
            matchesButton.setEnabled(false);
        }

        List<Car> results = CarRepository.searchCars(brand, model, yearFrom, yearTo, costFrom, costTo);

        matchesCountTextView.setText("Matches found: " + results.size());
        matchesButton.setEnabled(!results.isEmpty());
    }

    private void returnSearchResults() {
        String brand = brandTextView.getText().toString();
        String model = modelTextView.getText().toString();
        Integer yearFrom = yearFromSpinner.getSelectedItem() != null ? (Integer) yearFromSpinner.getSelectedItem() : null;
        Integer yearTo = yearToSpinner.getSelectedItem() != null ? (Integer) yearToSpinner.getSelectedItem() : null;
        Double costFrom = !costFromEditText.getText().toString().isEmpty() ? Double.parseDouble(costFromEditText.getText().toString()) : null;
        Double costTo = !costToEditText.getText().toString().isEmpty() ? Double.parseDouble(costToEditText.getText().toString()) : null;
        List<Car> results = CarRepository.searchCars(brand, model, yearFrom, yearTo, costFrom, costTo);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("search_results", new ArrayList<>(results));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private class SearchTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            search();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    private class SearchItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
            search();
        }

        @Override
        public void onNothingSelected(android.widget.AdapterView<?> parent) {}
    }
}