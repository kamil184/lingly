package com.kamil184.lingly.main.authorization.Registration.native_language;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.StringRes;

import com.google.firebase.auth.FirebaseUser;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;
import com.kamil184.lingly.main.authorization.Registration.NativeLanguageList;
import com.kamil184.lingly.main.authorization.Registration.NonNativeLanguageList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_NATIVE_LANGUAGES;
import static com.kamil184.lingly.main.authorization.Registration.RegistrationActivity.complexSettings;


public class NativeLanguagePresenter extends BasePresenter {
    private NativeLanguageFragment view;
    private FirebaseUser user;

    NativeLanguagePresenter(Context context) {
        super(context);
    }

    void attachView(NativeLanguageFragment NativeLanguageFragment) {
        view = NativeLanguageFragment;
    }

    void setNativeLanguage(ArrayList<Integer> nativeLanguage) {
        NativeLanguageList list = new NativeLanguageList();
        list.setLanguages(NativeLanguageList.integerListToLong(nativeLanguage));
        complexSettings.putObject(APP_PREFERENCES_NATIVE_LANGUAGES, list);
        complexSettings.commit();
        view.progressBar.setVisibility(View.VISIBLE);
        if (hasInternetConnection()) {
            Map<String, Object> user = new HashMap<>();
            user.put("native_languages", nativeLanguage);
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("nativeLanguages", nativeLanguage);
            if (isAuthorized()) {
                db.collection("users").document(getCurrentUserId()).collection("languages").document("languages_native")
                        .set(user)
                        .addOnSuccessListener(aVoid -> {
                            view.progressBar.setVisibility(View.GONE);
                            view.callback.toNonNativeLanguage(bundle);
                        })
                        .addOnFailureListener(e -> ifError(R.string.language_err));
            } else {
                ifError(R.string.not_auth_err);
            }
        } else {
            ifError(R.string.no_interent_connection_err);
        }
    }

    void ifError(@StringRes int message) {
        view.next_btn.setClickable(true);
        view.progressBar.setVisibility(View.GONE);
        view.showSnackBar(message);
    }
}
