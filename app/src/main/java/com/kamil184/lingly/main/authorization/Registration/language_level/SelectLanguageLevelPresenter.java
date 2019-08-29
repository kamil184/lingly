package com.kamil184.lingly.main.authorization.Registration.language_level;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.kamil184.lingly.base.BasePresenter;
import com.kamil184.lingly.main.authorization.Registration.native_language.NativeLanguageFragment;

public class SelectLanguageLevelPresenter extends BasePresenter {

    private SelectLanguageLevelFragment view;

    public SelectLanguageLevelPresenter(Context context) {
        super(context);
    }

    void attachView(SelectLanguageLevelFragment selectLanguageLevelFragment) {
        view = selectLanguageLevelFragment;
    }


}
