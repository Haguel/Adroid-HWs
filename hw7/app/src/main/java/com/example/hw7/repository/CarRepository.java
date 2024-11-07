package com.example.hw7.repository;

import com.example.hw7.R;
import com.example.hw7.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarRepository {
    private static List<Car> cars = new ArrayList<>();

    static {
        cars.add(new Car("Toyota", "Camry", 2020, "Excellent condition", 25000, R.drawable.toyota_camry));
        cars.add(new Car("Honda", "Civic", 2019, "Low mileage", 20000, R.drawable.honda_civic));
        cars.add(new Car("Ford", "Mustang", 2018, "Sporty and fast", 30000, R.drawable.ford_mustang));
        cars.add(new Car("Chevrolet", "Malibu", 2017, "Comfortable and reliable", 18000, R.drawable.chevrolet_malibu));
        cars.add(new Car("Nissan", "Altima", 2021, "Latest model", 27000, R.drawable.nissan_altima));
        cars.add(new Car("BMW", "3 Series", 2016, "Luxury sedan", 35000, R.drawable.bmw_3_series));
        cars.add(new Car("Audi", "A4", 2015, "Premium features", 32000, R.drawable.audi_a4));
    }

    public static List<Car> getCars() {
        return cars;
    }

    public static List<Car> searchCars(String brand, String model, Integer yearFrom, Integer yearTo, Double costFrom, Double costTo) {
        return getCars().stream()
                .filter(car -> (brand != null && !brand.isEmpty() ? car.getBrand().equalsIgnoreCase(brand) : true))
                .filter(car -> (model != null && !model.isEmpty() ? car.getModel().equalsIgnoreCase(model) : true))
                .filter(car -> (yearFrom != null ? car.getYear() >= yearFrom : true))
                .filter(car -> (yearTo != null ? car.getYear() <= yearTo : true))
                .filter(car -> (costFrom != null ? car.getCost() >= costFrom : true))
                .filter(car -> (costTo != null ? car.getCost() <= costTo : true))
                .collect(Collectors.toList());
    }

}