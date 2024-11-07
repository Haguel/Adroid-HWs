package com.example.hw8.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hw8.R;
import com.example.hw8.fragment.NameListFragment;
import com.example.hw8.model.NameModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<NameModel> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameList = new ArrayList<>();
        nameList.add(new NameModel("John", "24 June", "Derived from the Hebrew name Yochanan, meaning 'God is gracious' or 'God is merciful'."));
        nameList.add(new NameModel("Mary", "15 August", "Derived from the Hebrew name Miriam, meaning 'beloved', 'bitter', or 'rebellious'."));
        nameList.add(new NameModel("Michael", "29 September", "Derived from the Hebrew name Mikha'el, meaning 'Who is like God?'."));
        nameList.add(new NameModel("Anna", "26 July", "Derived from the Hebrew name Hannah, meaning 'grace' or 'favor'."));
        nameList.add(new NameModel("David", "29 December", "Derived from the Hebrew name Dawid, meaning 'beloved'."));
        nameList.add(new NameModel("Sophia", "30 September", "Derived from the Greek word sophia, meaning 'wisdom'."));
        nameList.add(new NameModel("James", "3 May", "Derived from the Hebrew name Yaakov, meaning 'supplanter' or 'one who follows'."));
        nameList.add(new NameModel("Emma", "19 April", "Derived from the Germanic word ermen, meaning 'whole' or 'universal'."));
        nameList.add(new NameModel("Daniel", "21 July", "Derived from the Hebrew name Daniyyel, meaning 'God is my judge'."));
        nameList.add(new NameModel("Olivia", "11 June", "Derived from the Latin word oliva, meaning 'olive tree'."));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new NameListFragment())
                    .commit();
        }
    }

    public List<String> getNameList() {
        List<String> names = new ArrayList<>();
        for (NameModel model : nameList) {
            names.add(model.getName());
        }

        return names;
    }

    public NameModel getNameModel(String name) {
        for (NameModel model : nameList) {
            if (model.getName().equals(name)) {
                return model;
            }
        }

        return null;
    }
}