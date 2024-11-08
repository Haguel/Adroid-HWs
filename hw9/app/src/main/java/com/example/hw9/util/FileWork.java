package com.example.hw9.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileWork {
    private static SharedPreferences sharedPreferences;

    public static void initializeSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("game_data", Context.MODE_PRIVATE);
    }

    public static void writeIntToSharedPreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int readIntFromSharedPreferences(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static List<String> readDataFromFile(String filename, Context context) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(context.getFilesDir() + "/" + filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            Log.e("FileWork", "Error reading data from file", e);
        }

        return lines;
    }
}