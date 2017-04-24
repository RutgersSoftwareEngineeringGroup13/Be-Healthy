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
    RadioButton rb;
    boolean GENDER;
    float protein;
    float fat;
    float carbs;
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
        age = (EditText) findViewById(R.id.AgeInput);
        feet = (EditText) findViewById(R.id.HeightFeet);
        inches = (EditText) findViewById(R.id.HeightInches);
        weight = (EditText) findViewById(R.id.WeightInput);
        gender = (RadioGroup) findViewById(R.id.GenderInput);
        database = FirebaseDatabase.getInstance();
        FirebaseAuth.AuthStateListener authListener;
        FirebaseAuth auth = FirebaseAuth.getInstance();
    }

    ;

    public void PInext(View v) {
        if (!age.getText().toString().isEmpty() && !feet.getText().toString().isEmpty()
                && !inches.getText().toString().isEmpty()
                && !weight.getText().toString().isEmpty()
                && gender.getCheckedRadioButtonId() != -1) {
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
            rb = (RadioButton) findViewById(selectedId);
            int idx = gender.indexOfChild(rb);
            RadioButton rb1 = (RadioButton) gender.getChildAt(idx);
            if(rb1.getText().toString().equals("Male")){
                GENDER = true;
            }
            else{
                GENDER = false;
            }
            float CALORIES = calculateCalories(Age,Feet,Inches,Weight,GENDER);
            calculateMacros(Weight, CALORIES);
            Intent j = new Intent(Personal_Info.this, Progress_Dashboard.class);
            j.putExtra("Key1", CALORIES);
            j.putExtra("Key2", protein);
            j.putExtra("Key3", fat);
            j.putExtra("Key4", carbs);
            Toast.makeText(getApplicationContext(),"CALORIES: " +CALORIES, Toast.LENGTH_LONG).show();
            startActivity(j);
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Please fill out the form", Toast.LENGTH_SHORT).show();
        }
    }
    public void calculateMacros(int weight, float calorieIntake){
        protein = 4f*1.2f*weight;
        fat =(.20f*calorieIntake);
        carbs = calorieIntake - (protein+fat);
    }
    public float calculateCalories(int age, int feet, int inches, int weight, boolean gender){
        float totalheight = (float) 2.54*(feet*12 + inches);
        float BMR = (float) 10f*0.453592f*weight + 6.25f*totalheight - 5f*age;
        if(gender == true){
            BMR = BMR + 5;
        }
        else if(gender == false){
            BMR = BMR - 161;
        }
        float failsafe = 8*weight;
        if(BMR < failsafe){
            BMR  = failsafe;
        }
        BMR = 1.3f*BMR; //Activity Level
        return BMR;
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
}

