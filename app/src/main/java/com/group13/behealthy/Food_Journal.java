package com.group13.behealthy;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;


/**
 * Created by stephan on 3/26/17.
 */




public class Food_Journal extends Fragment {
    static DatePicker date;
    TextView text;

    public static void setDate(final DatePicker d1) {
        date = d1;
    }

    public Food_Journal() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.food_journal, container, false);
        text = (TextView) rootView.findViewById(R.id.foodDate);
        String year = Integer.toString(date.getYear());
        String month = Integer.toString(date.getMonth()+1);
        String day = Integer.toString(date.getDayOfMonth());
        String date1 = month + "/" + day + "/" + year;
        text.setText(date1);
        return rootView;
    }
}
