package com.example.hw8.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.hw8.R;
import com.example.hw8.activity.MainActivity;
import com.example.hw8.model.NameModel;

public class NameDetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_detail, container, false);
        TextView nameTextView = view.findViewById(R.id.name_text_view);
        TextView dateTextView = view.findViewById(R.id.date_text_view);
        TextView meaningTextView = view.findViewById(R.id.meaning_text_view);
        Button backButton = view.findViewById(R.id.back_button);
        String name = getArguments().getString("name");
        MainActivity activity = (MainActivity) getActivity();
        NameModel model = activity.getNameModel(name);

        if (model != null) {
            nameTextView.setText(model.getName());
            dateTextView.setText(model.getDate());
            meaningTextView.setText(model.getMeaning());
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}