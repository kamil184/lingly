package com.kamil184.lingly.main.authorization.Registration.sign_up;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

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
        view.hideKeyboard();
        if(hasInternetConnection()) {
            Map<String, Object> user = new HashMap<>();
            user.put("firstName", firstName);
            user.put("secondName",secondName);
            user.put("birth_day",day);
            user.put("birth_month",month);
            user.put("birth_year",year);
            if(isAuthorized()) {
                db.collection("users").document(getCurrentUserEmail())
                        .update(user)
                        .addOnSuccessListener(aVoid ->{
                            view.finish();
                            view.callback.toNativeLanguage();
                        })
                        .addOnFailureListener(e -> view.showSnackBar(R.string.signup_err));
            }else{
                view.showSnackBar(R.string.not_auth_err);
            }
        }
            else{
            //view.setProgressVisibilityGone();
            view.showSnackBar(R.string.no_interent_connection_err);
        }
    }


}
