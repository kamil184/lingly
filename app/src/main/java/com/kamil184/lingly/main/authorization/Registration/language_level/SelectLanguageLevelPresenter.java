package com.kamil184.lingly.main.authorization.Registration.language_level;

import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;

import com.kamil184.lingly.base.BasePresenter;

public class SelectLanguageLevelPresenter extends BasePresenter {

    private SelectLanguageLevelFragment view;

    public SelectLanguageLevelPresenter(Context context) {
        super(context);
    }

    void attachView(SelectLanguageLevelFragment selectLanguageLevelFragment) {
        view = selectLanguageLevelFragment;
    }




    void ifError(@StringRes int message){
        view.progressBar.setVisibility(View.GONE);
        view.showSnackBar(message);
    }


}
