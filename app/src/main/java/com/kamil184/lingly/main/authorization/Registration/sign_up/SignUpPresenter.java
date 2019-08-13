package com.kamil184.lingly.main.authorization.Registration.sign_up;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.kamil184.lingly.MainActivity;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class SignUpPresenter extends BasePresenter {


    private SignUpFragment view;
    private FirebaseUser user;

    void attachView(SignUpFragment signUpActivity) {
        view = signUpActivity;
    }

    SignUpPresenter(Context context) {
        super(context);
    }

    void signUpWithEmail(String email,String password){
        view.hideKeyboard();
        if(hasInternetConnection()) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, task -> {
                        if (!task.isSuccessful()) {
                            view.setProgressVisibilityGone();
                            view.showSnackBar(R.string.signup_err);
                        } else {
                            Map<String, Object> userInfo = new HashMap<>();
                            user = getCurrentUser();
                            userInfo.put("email",user.getEmail());
                            db.collection("users").document(user.getUid())
                                    .set(userInfo)
                                    .addOnSuccessListener(aVoid -> switchToNextStep())
                                    .addOnFailureListener(e -> ifError(R.string.signup_err));
                        }
                    });
        }else{
            view.setProgressVisibilityGone();
            view.showSnackBar(R.string.no_interent_connection_err);
        }

    }

    private void switchToNextStep(){
        view.setProgressVisibilityGone();
        view.callback.toUserInfo();
    }

    void ifError(@StringRes int message){
        view.progressBar.setVisibility(View.GONE);
        view.showSnackBar(message);
    }

}
