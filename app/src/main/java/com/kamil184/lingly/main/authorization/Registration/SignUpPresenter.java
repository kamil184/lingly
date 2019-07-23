package com.kamil184.lingly.main.authorization.Registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.kamil184.lingly.MainActivity;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;


public class SignUpPresenter extends BasePresenter {

    private SignUpActivity view;
    private FirebaseUser user;

    void attachView(SignUpActivity signUpActivity) {
        view = signUpActivity;
    }

    SignUpPresenter(Context context) {
        super(context);
    }

    void signUpWithEmail(String name, String email,String password){
        view.hideKeyboard();
        if(hasInternetConnection()) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, task -> {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            view.showSnackBar(R.string.signup_err);
                        } else {
                            user = getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                view.setProgressVisibilityGone();
                                                view.showSnackBar(R.string.signup_sucss);
                                                view.startActivity(new Intent(context, MainActivity.class));
                                            }
                                        }
                                    });
                        }
                    });
        }else{
            view.setProgressVisibilityGone();
            view.showSnackBar(R.string.no_interent_connection_err);
        }

    }

}
