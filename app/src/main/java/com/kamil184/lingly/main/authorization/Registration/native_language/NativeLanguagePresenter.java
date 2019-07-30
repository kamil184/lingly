package com.kamil184.lingly.main.authorization.Registration.native_language;

import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;

import com.google.firebase.auth.FirebaseUser;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NativeLanguagePresenter extends BasePresenter {
    private NativeLanguageFragment view;
    private FirebaseUser user;

    public NativeLanguagePresenter(Context context) {
        super(context);
    }

    void attachView(NativeLanguageFragment NativeLanguageFragment) {
        view = NativeLanguageFragment;
    }


    void addNativaeLanguage(ArrayList<String> nativeLanguage){
        view.progressBar.setVisibility(View.VISIBLE);
        if(hasInternetConnection()){
            Map<String, Object> user = new HashMap<>();
            user.put("user_native_language",nativeLanguage);
            if (isAuthorized()){
                db.collection("users").document(getCurrentUserEmail())
                        .update(user)
                        .addOnSuccessListener(aVoid ->{
                            view.progressBar.setVisibility(View.GONE);
                            view.finish();
                            view.callback.toNonNativeLanguage();
                        })
                        .addOnFailureListener(e -> {
                            ifError(R.string.language_err);
                        });
            }else{
                ifError(R.string.not_auth_err);
            }
        }else{
            ifError(R.string.no_interent_connection_err);
        }
    }

    void ifError(@StringRes int message){
        view.next_btn.setClickable(true);
        view.progressBar.setVisibility(View.GONE);
        view.showSnackBar(message);
    }
}
