package com.group13.behealthy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Personal_Info extends AppCompatActivity {
    EditText age, feet, inches, weight;
    RadioGroup gender;
    RadioButton rb;
    View view;
    boolean GENDER;
    float macroArray[];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
        age = (EditText)findViewById(R.id.AgeInput);
        feet = (EditText)findViewById(R.id.HeightFeet);
        inches = (EditText)findViewById(R.id.HeightInches);
        weight = (EditText)findViewById(R.id.WeightInput);
        gender = (RadioGroup) findViewById(R.id.GenderInput);
    };

    public void RadioButtonClicked(View view, String gender) {
        gender = "";
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radiobtn_female:
                if (checked)
                    gender = "female";
                break;
            case R.id.radiobtn_male:
                if (checked)
                    gender = "male";
                break;
        }
    }
    public void PInext(View v) {

        if (age.getText().toString().isEmpty() || feet.getText().toString().isEmpty()
                 || inches.getText().toString().isEmpty()
                || weight.getText().toString().isEmpty()
                || gender.getCheckedRadioButtonId()== -1 ) {
            Toast.makeText(getApplicationContext(), "Please fill out the form", Toast.LENGTH_SHORT).show();
        }
        else {
            String value1 = age.getText().toString();
            int finalAge = Integer.parseInt(value1);
            String value2 = feet.getText().toString();
            int finalFeet = Integer.parseInt(value2);
            String value3 = inches.getText().toString();
            int finalInches = Integer.parseInt(value3);
            String value4 = weight.getText().toString();
            int finalWeight = Integer.parseInt(value4);
            int selectedId = gender.getCheckedRadioButtonId();
            rb = (RadioButton) findViewById(selectedId);
            int idx = gender.indexOfChild(rb);
            RadioButton rb1 = (RadioButton) gender.getChildAt(idx);
            if(rb1.getText().toString().equals("Male")){
                GENDER = true;
            }
            else{
                GENDER = false;
            }
            //RadioButtonClicked(view, Gender);

            float CALORIES = calculateCalories(finalAge, finalFeet, finalInches, finalWeight, GENDER);
            calculateMacros(finalWeight, CALORIES);
            Toast.makeText(getApplicationContext(),"The BMR is " +CALORIES, Toast.LENGTH_LONG).show();
            Intent j = new Intent(Personal_Info.this, Progress_Dashboard.class);
            j.putExtra("KEY", (float)CALORIES);
           // j.putExtra("KEY2", (float)macroArray[]);
            startActivity(j);
            finish();
        }
    }
    public void calculateMacros(int weight, float calorieIntake){
        float protein = 4f*1.2f*weight;
        float fat =(.20f*calorieIntake);
        float carbs = calorieIntake - (protein+fat);
        float[] macroArray = {protein, fat, carbs};
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
}

