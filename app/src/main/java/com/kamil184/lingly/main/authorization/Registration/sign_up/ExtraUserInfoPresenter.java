package com.kamil184.lingly.main.authorization.Registration.sign_up;

import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;

import com.google.firebase.auth.FirebaseUser;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_ABOUT;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_BIRTHDAY;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_BIRTHMONTH;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_BIRTHYEAR;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_EMAIL;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_FIRSTNAME;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_SECONDNAME;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_STATUS;
import static com.kamil184.lingly.main.authorization.Registration.RegistrationActivity.editor;

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
        editor.putString(APP_PREFERENCES_FIRSTNAME, firstName);
        editor.putString(APP_PREFERENCES_SECONDNAME, secondName);
        editor.putString(APP_PREFERENCES_STATUS, "");
        editor.putString(APP_PREFERENCES_ABOUT, "");
        editor.putInt(APP_PREFERENCES_BIRTHDAY, day);
        editor.putInt(APP_PREFERENCES_BIRTHMONTH, month);
        editor.putInt(APP_PREFERENCES_BIRTHYEAR, year);
        editor.commit();
        view.progressBar.setVisibility(View.VISIBLE);
        view.hideKeyboard();
        if(hasInternetConnection()) {
            Map<String, Object> user = new HashMap<>();
            user.put("first_name", firstName);
            user.put("second_name",secondName);
            user.put("birth_day",day);
            user.put("birth_month",month);
            user.put("birth_year",year);
            user.put("status","");
            user.put("about","");
            user.put("is_online",true);
            if(isAuthorized()) {
                db.collection("users").document(getCurrentUserId())
                        .update(user)
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
