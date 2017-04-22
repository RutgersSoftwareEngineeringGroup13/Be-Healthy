package com.group13.behealthy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.group13.behealthy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Notifies the user of sign in successes or failures beyond the lifecycle of an activity.
 */
public class SignInResultNotifier implements OnCompleteListener<AuthResult> {
    private Context mContext;

    public SignInResultNotifier(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Toast.makeText(mContext, "Too Short", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Too Long", Toast.LENGTH_LONG).show();
        }
    }
}