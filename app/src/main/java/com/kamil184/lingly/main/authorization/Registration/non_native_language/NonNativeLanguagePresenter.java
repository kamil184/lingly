package com.kamil184.lingly.main.authorization.Registration.non_native_language;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NonNativeLanguagePresenter extends BasePresenter {
    private NonNativeLanguageFragment view;
    private FirebaseUser user;

    NonNativeLanguagePresenter(Context context) {
        super(context);
    }

    void attachView(NonNativeLanguageFragment nonNativeLanguageFragment) {
        view = nonNativeLanguageFragment;
    }

    void addNonNativeLanguage(ArrayList<Integer> nonNativeLanguage){
        view.progressBar.setVisibility(View.VISIBLE);
        if(hasInternetConnection()){
            Map<String, Object> user = new HashMap<>();
            user.put("non_native_languages",nonNativeLanguage);
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("nonNativeLanguages",nonNativeLanguage);
            if (isAuthorized()){
                db.collection("users").document(getCurrentUserId()).collection("languages").document("languages_non_native")
                        .set(user)
                        .addOnSuccessListener(aVoid ->{
                            view.progressBar.setVisibility(View.GONE);
                            view.callback.toSelectLanguageLevel(bundle);
                        })
                        .addOnFailureListener(e -> {
                            view.progressBar.setVisibility(View.GONE);
                            view.showSnackBar(R.string.language_err);
                        });
            }else{
                view.progressBar.setVisibility(View.GONE);
                view.showSnackBar(R.string.not_auth_err);
            }
        }else{
            view.progressBar.setVisibility(View.GONE);
            view.showSnackBar(R.string.no_interent_connection_err);
        }
    }

}
