package com.kamil184.lingly.main.authorization.Registration.native_language;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.kamil184.lingly.R;
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

    protected String[] languageArray = {"Русский","Английский","Немецкий","Французский","Испанский","Португальский","Турецкий","Итальянский","Хинди","Японский","Китайский"};
    protected int[] flagArray = {
    R.mipmap.ic_list_ru,
    R.mipmap.ic_list_uk,
    R.mipmap.ic_list_de,
    R.mipmap.ic_list_fr,
    R.mipmap.ic_list_es,
    R.mipmap.ic_list_pt,
    R.mipmap.ic_list_tr,
    R.mipmap.ic_list_it,
    R.mipmap.ic_list_in,
    R.mipmap.ic_list_jp,
    R.mipmap.ic_list_cn
    };

    //TODO добавление инфы на сервак


}
