package com.kamil184.lingly.main.authorization.Registration.language_level;

import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;

import com.kamil184.lingly.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

public class SelectLanguageLevelPresenter extends BasePresenter {

    private SelectLanguageLevelFragment view;

    SelectLanguageLevelPresenter(Context context) {
        super(context);
    }


    void attachView(SelectLanguageLevelFragment selectLanguageLevelFragment) {
        view = selectLanguageLevelFragment;
    }


    void setLanguageLevel(int languageLevel, int languageId){
        if(hasInternetConnection()){
            Map<String, Object> user = new HashMap<>();
            user.put(""+languageId,languageLevel);
            if (isAuthorized()){
                db.collection("users").document(getCurrentUserId()).collection("languages").document("languageLevels")
                        .update(user)
                        .addOnSuccessListener(aVoid -> {
                            //view.progressBar.setVisibility(View.GONE);
                        })
                        .addOnFailureListener(e -> {
                           // ifError(R.string.language_err);
                        });
            }else{
                //ifError(R.string.not_auth_err);
            }
        }else{
            //ifError(R.string.no_interent_connection_err);
        }
    }


    void ifError(@StringRes int message){
        view.progressBar.setVisibility(View.GONE);
        view.showSnackBar(message);
    }


}
