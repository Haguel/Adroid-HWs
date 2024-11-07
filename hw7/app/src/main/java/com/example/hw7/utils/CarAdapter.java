package com.example.hw7.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.hw7.R;
import com.example.hw7.model.Car;

import java.util.List;

public class CarAdapter extends ArrayAdapter<Car> {
    public CarAdapter(@NonNull Context context, @NonNull List<Car> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.car_list_item, parent, false);
        }

        Car car = getItem(position);
        TextView brandTextView = convertView.findViewById(R.id.car_brand);
        TextView modelTextView = convertView.findViewById(R.id.car_model);
        TextView yearTextView = convertView.findViewById(R.id.car_year);
        TextView descriptionTextView = convertView.findViewById(R.id.car_description);
        TextView costTextView = convertView.findViewById(R.id.car_cost);
        ImageView carImageView = convertView.findViewById(R.id.car_image);

        brandTextView.setText(car.getBrand());
        modelTextView.setText(car.getModel());
        yearTextView.setText(String.valueOf(car.getYear()));
        descriptionTextView.setText(car.getDescription());
        costTextView.setText(String.format("$%.2f", car.getCost()));
        carImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), car.getImageResourceId()));

        return convertView;
    }
}