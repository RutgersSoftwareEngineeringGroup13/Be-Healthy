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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by stephan on 3/24/17.
 */

public class Food_Intake extends Fragment {
    private FirebaseDatabase database;
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
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.food_intake, container, false);
        Button btn = (Button) rootView.findViewById(R.id.confirmDate);
        final DatePicker date = (DatePicker) rootView.findViewById(R.id.datePicker);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i= new Intent(this, Food_Journal.class);
                Fragment fragment = new Food_Journal();
                Food_Journal.setDate(date);
                database = FirebaseDatabase.getInstance();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String uid = user.getUid();
                String year = Integer.toString(date.getYear());
                String month = Integer.toString(date.getMonth() + 1);
                String day = Integer.toString(date.getDayOfMonth());
                final String date1 = month + "-" + day + "-" + year;
                DatabaseReference myRef = database.getReference("series");
                myRef.child(uid).child(date1).child("Total").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null){
                            float value = Float.parseFloat(dataSnapshot.getValue().toString());
                            Food_Journal.setTotal(value);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(rootView.getContext(), "Oops " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
            }
        });
        return rootView;
    }

}
