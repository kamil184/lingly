package com.kamil184.lingly.main.authorization.Registration.native_language;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.kamil184.lingly.base.BasePresenter;



public class NativeLanguagePresenter extends BasePresenter {
    private NativeLanguageFragment view;
    private FirebaseUser user;

    public NativeLanguagePresenter(Context context) {
        super(context);
    }

    void attachView(NativeLanguageFragment NativeLanguageFragment) {
        view = NativeLanguageFragment;
    }

    protected String[] languageArray = {"Русский","Английский","Немецкий","Французский","Испанский","Португальский","Арабский","Турецкий","Итальянский","Хинди"};


    //TODO добавление инфы на сервак


}
