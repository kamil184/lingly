package com.kamil184.lingly.main.authorization.Registration.sign_up;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.kamil184.lingly.MainActivity;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

public class ExtraUserInfoPresenter extends BasePresenter {

    private ExtraUserInfoFragment view;
    private FirebaseUser user;

    ExtraUserInfoPresenter(Context context) {
        super(context);
    }


    void attachView(ExtraUserInfoFragment extraUserInfoFragment) {
        view = extraUserInfoFragment;
    }

    void addExtraInfo(String firstName,String secondName,int day,int month,int year){
        view.progressBar.setVisibility(View.VISIBLE);
        view.hideKeyboard();
        if(hasInternetConnection()) {
            Map<String, Object> user = new HashMap<>();
            user.put("first_name", firstName);
            user.put("second_name",secondName);
            user.put("birth_day",day);
            user.put("birth_month",month);
            user.put("birth_year",year);
            user.put("is_online",true);
            if(isAuthorized()) {
                db.collection("users").document(getCurrentUserEmail())
                        .set(user)
                        .addOnSuccessListener(aVoid ->{
                            view.progressBar.setVisibility(View.GONE);
                            view.callback.toNativeLanguage();
                        })
                        .addOnFailureListener(e -> {
                            ifError(R.string.signup_err);
                        });
            }else{
                ifError(R.string.not_auth_err);
            }
        }
            else{
            ifError(R.string.no_interent_connection_err);
        }
    }

    void ifError(@StringRes int message){
        view.btn_next_step.setClickable(true);
        view.progressBar.setVisibility(View.GONE);
        view.showSnackBar(message);
    }
}
