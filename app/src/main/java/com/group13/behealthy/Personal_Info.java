package com.group13.behealthy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Personal_Info extends AppCompatActivity {
    EditText age, feet, inches, weight;
    RadioGroup gender;
    private FirebaseDatabase database;

    public class user {
        int userAge;
        int userFeet;
        int userInches;
        int userWeight;
        int userGender;


        public user(int a, int b, int c, int d, int e) {
            this.userAge = a;
            this.userFeet = b;
            this.userInches = c;
            this.userWeight = d;
            this.userGender = e;
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
        age = (EditText)findViewById(R.id.AgeInput);
        feet = (EditText)findViewById(R.id.HeightFeet);
        inches = (EditText)findViewById(R.id.HeightInches);
        weight = (EditText)findViewById(R.id.WeightInput);
        gender = (RadioGroup) findViewById(R.id.GenderInput);

        database = FirebaseDatabase.getInstance();
        FirebaseAuth.AuthStateListener authListener;
        FirebaseAuth auth = FirebaseAuth.getInstance();
    };
    public void PInext(View v) {
        if (!age.getText().toString().isEmpty() && !feet.getText().toString().isEmpty()
                && !inches.getText().toString().isEmpty()
                && !weight.getText().toString().isEmpty()
                && gender.getCheckedRadioButtonId()!= -1 ) {
            String ageString = age.getText().toString();
            String feetString = feet.getText().toString();
            String inchesString = inches.getText().toString();
            String weightString = weight.getText().toString();

            int Age = Integer.parseInt(ageString);
            int Feet = Integer.parseInt(feetString);
            int Inches = Integer.parseInt(inchesString);
            int Weight = Integer.parseInt(weightString);
            int selectedId = gender.getCheckedRadioButtonId();

            addUserPlan(Age, Feet, Inches, Weight, selectedId);
            Intent j = new Intent(Personal_Info.this, Progress_Dashboard.class);
            startActivity(j);
            finish();

        }
        else{
            Toast.makeText(getApplicationContext(), "Please fill out the form", Toast.LENGTH_SHORT).show();
        }
    }

    public void addUserPlan(int age, int feet, int inches, int weight, int gender) {

        DatabaseReference userRef = database.getReference("series");

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user userTemp = new user(age, feet, inches, weight, gender);

        String uid1 = user.getUid();

            userRef.child("UserPersonalInfo").child("Age").child(uid1).setValue(userTemp.userAge);
            userRef.child("UserPersonalInfo").child("Feet").child(uid1).setValue(userTemp.userFeet);
            userRef.child("UserPersonalInfo").child("Inches").child(uid1).setValue(userTemp.userInches);
            userRef.child("UserPersonalInfo").child("Weight").child(uid1).setValue(userTemp.userWeight);
            userRef.child("UserPersonalInfo").child("Gender").child(uid1).setValue(userTemp.userGender);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Oops " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }


    public double calculateCalories(int age, int feet, int inches, int weight, String gender){
        double totalheight = 2.54*(feet*12 + inches);
        double BMR = 10*0.453592*weight + 6.25*totalheight - 5*age;
        if(gender.equals("male")){
            BMR = BMR + 5;
        }
        else if(gender.equals("female")) {
            BMR = BMR - 161;
        }
        return BMR;
    }
}


