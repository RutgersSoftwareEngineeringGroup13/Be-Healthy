package com.group13.behealthy;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by stephan on 3/26/17.
 */




public class Food_Journal extends Fragment {
    static DatePicker date;
    TextView text;
    static float total =(float) 0.0;
    float CalVal =(float) 0.0;
    private FirebaseDatabase database;
    public static void setDate(final DatePicker d1) {
        date = d1;
    }
    public static void setTotal(float value) {
        total = value;
    }

    public Food_Journal() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.food_journal, container, false);
        database = FirebaseDatabase.getInstance();
        FirebaseAuth.AuthStateListener authListener;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        text = (TextView) rootView.findViewById(R.id.foodDate);
        String year = Integer.toString(date.getYear());
        String month = Integer.toString(date.getMonth() + 1);
        String day = Integer.toString(date.getDayOfMonth());
        final String date1 = month + "/" + day + "/" + year;
        final String date2 = month + "-" + day + "-" + year;
        text.setText(date1);
        final DatabaseReference myRef;
        myRef = database.getReference("series");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String uid = user.getUid();

        Button b1 = (Button)rootView.findViewById(R.id.addData);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                float c, cc, f, p;
                EditText cal = (EditText)rootView.findViewById(R.id.calories);
                EditText carb = (EditText)rootView.findViewById(R.id.carbs);
                EditText fat = (EditText)rootView.findViewById(R.id.fat);
                EditText prot = (EditText)rootView.findViewById(R.id.protein);
                final EditText name = (EditText)rootView.findViewById(R.id.foodName);
                c = Float.parseFloat(cal.getText().toString());
                cc = Float.parseFloat(carb.getText().toString());
                f = Float.parseFloat(fat.getText().toString());
                p = Float.parseFloat(prot.getText().toString());
                Entry entry = new Entry(c, cc, f, p);
                if (user != null) {
                    myRef.child(uid).child(date2).child(name.getText().toString()).child("Calories").setValue(entry.calories);
                    myRef.child(uid).child(date2).child(name.getText().toString()).child("Carbohydrates").setValue(entry.carbohydrates);
                    myRef.child(uid).child(date2).child(name.getText().toString()).child("Fat").setValue(entry.fat);
                    myRef.child(uid).child(date2).child(name.getText().toString()).child("Protein").setValue(entry.protein);
                    myRef.child(uid).child(date2).child(name.getText().toString()).child("Calories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            float value = Float.parseFloat(dataSnapshot.getValue().toString());
                            setValue(value);
                            myRef.child(uid).child(date2).child("Total").setValue(total);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(rootView.getContext(), "Oops " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    //myRef.child(uid).child(date2).child("Total").setValue(total);
                }
                /*DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();

                String key = mDatabase.child(date2).child(name.getText().toString()).child("Total").child(uid).push().get;
                if(key == null){
                    total = (float) 0.0;
                }
                else
                    total += Float.parseFloat(key);
                myRef.child(date2).child(name.getText().toString()).child("Total").child(uid).setValue(total);*/
            }
        });

        return rootView;

    }

    @IgnoreExtraProperties
    public static class Entry {

        public Float calories;
        public Float fat;
        public Float carbohydrates;
        public Float protein;

        public Entry() {
            // Default constructor required for calls to DataSnapshot.getValue(Entry.class)
        }

        public Entry(Float calories, Float fat, Float carbohydrates, Float protein) {
            this.calories = calories;
            this.fat = fat;
            this.carbohydrates = carbohydrates;
            this.protein = protein;
        }
    }

    public void setValue(float value){
        CalVal = value;
        total += CalVal;
    }


    //public void getCalories(final ViewGroup rootView) {


   // }


}