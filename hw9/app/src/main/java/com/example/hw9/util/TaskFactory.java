package com.example.hw9.util;

import android.content.Context;
import android.util.Log;

import com.example.hw9.model.Question;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskFactory {

    public static String writeQuestionsToFile(String filename, Context context) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(context.getFilesDir() + "/" + filename))) {
            writer.write("Is the sky blue?;true\n");
            writer.write("Is fire cold?;false\n");
            writer.write("Is water wet?;true\n");
            writer.write("Is the earth flat?;false\n");
            writer.write("Is snow hot?;false\n");
            writer.write("Is the sun a star?;true\n");
            writer.write("Is ice cream cold?;true\n");
            writer.write("Is the moon made of cheese?;false\n");
            writer.write("Is grass green?;true\n");
            writer.write("Is chocolate sweet?;true\n");

            return "";
        } catch (IOException e) {
            Log.e("TaskFactory", "Error writing questions to file", e);

            return e.getMessage();
        }
    }

    public static List<String> readDataFromFile(String filename, Context context) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(context.getFilesDir() + "/" + filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            Log.e("TaskFactory", "Error reading data from file", e);
        }

        return lines;
    }

    public static List<Question> convertStringsToQuestions(List<String> strQuestions) {
        List<Question> questions = new ArrayList<>();
        for (String str : strQuestions) {
            String[] parts = str.split(";");
            if (parts.length == 2) {
                questions.add(new Question(parts[0], Boolean.parseBoolean(parts[1])));
            }
        }

        return questions;
    }
}