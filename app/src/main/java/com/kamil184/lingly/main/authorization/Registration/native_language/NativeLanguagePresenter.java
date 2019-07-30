package com.kamil184.lingly.main.authorization.Registration.native_language;

import android.content.Context;

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
        if(hasInternetConnection()){
            Map<String, Object> user = new HashMap<>();
            user.put("user_native_language",nativeLanguage);
            if (isAuthorized()){
                db.collection("users").document(getCurrentUserEmail())
                        .update(user)
                        .addOnSuccessListener(aVoid ->{
                            view.finish();
                            view.callback.toNonNativeLanguage();
                        })
                        .addOnFailureListener(e -> view.showSnackBar(R.string.language_err));
            }else{
                view.showSnackBar(R.string.not_auth_err);
            }
        }else{
            view.showSnackBar(R.string.no_interent_connection_err);
        }
    }


}
