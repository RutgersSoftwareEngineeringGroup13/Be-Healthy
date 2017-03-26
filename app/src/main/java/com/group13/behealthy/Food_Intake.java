package com.group13.behealthy;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

/**
 * Created by stephan on 3/24/17.
 */

public class Food_Intake extends Fragment {
    public Food_Intake() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.food_intake, container, false);
        Button btn = (Button) rootView.findViewById(R.id.confirmDate);
        final DatePicker date = (DatePicker) rootView.findViewById(R.id.datePicker);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i= new Intent(this, Food_Journal.class);
                Fragment fragment = new Food_Journal();
                Food_Journal.setDate(date);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
            }
        });
        return rootView;
    }

}
