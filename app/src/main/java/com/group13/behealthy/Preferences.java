package com.group13.behealthy;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.R.id.input;

/**
 * Created by stephan on 4/15/17.
 */

public class Preferences extends Fragment {
    private FirebaseAuth.AuthStateListener authListener;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public Preferences() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                }
            }
        };


        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.preferences, container, false);
        final EditText newPassword = new EditText(rootView.getContext());
        newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        TextView diet = (TextView)rootView.findViewById(R.id.dplan);
        TextView changePassword = (TextView)rootView.findViewById(R.id.changePass);
        CharSequence[] plans = new CharSequence[4];
        plans[0] = "Lose Weight";
        plans[1] = "Gain Weight";
        plans[2] = "Maintain Weight";
        plans[3] = "I Don\'t Want a Diet Plan";
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog.Builder newPass = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a Diet Plan")
                .setItems(plans, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch(which){
                            case 0: break;
                            case 1: break;
                            case 2: break;
                            case 3: break;
                            default: break;
                        }
                    }
                });
        newPass.setTitle("Change Password");
        newPass.setView(newPassword);
        newPass.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Password too short, enter minimum 6 characters");
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(rootView.getContext(), "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            signOut();
                                        } else {
                                            Toast.makeText(rootView.getContext(), "Failed to update password!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                }

            }
        });
        newPass.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                builder.show();
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                newPass.show();
            }
        });
        return rootView;
    }

    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }

    }
}
