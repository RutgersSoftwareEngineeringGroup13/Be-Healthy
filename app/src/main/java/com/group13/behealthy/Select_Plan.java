package com.group13.behealthy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by stephan on 2/6/17.
 */
public class Select_Plan extends AppCompatActivity {
    RadioGroup r1;
    RadioButton rb;
    Button b2;

    protected void onCreate(Bundle savedInsuranceState){
        super.onCreate(savedInsuranceState);
        setContentView(R.layout.select_plan);
        r1 = (RadioGroup) findViewById(R.id.radioG1);
        b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = r1.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(selectedId);
                int idx = r1.indexOfChild(rb);
                RadioButton rb1 = (RadioButton) r1.getChildAt(idx);
                if(r1.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Please Select an Option", Toast.LENGTH_SHORT).show();
                }
                else if (rb1.getText().toString().equals("Diet Plan #1")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                }
                else if(rb1.getText().toString().equals("Diet Plan #2")){
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                }
                else if(rb1.getText().toString().equals("Diet Plan #3")){
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                }
                else if(rb1.getText().toString().equals("I Don\'t Want a Diet Plan")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
