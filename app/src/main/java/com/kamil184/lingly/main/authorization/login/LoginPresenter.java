package com.kamil184.lingly.main.authorization.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kamil184.lingly.MainActivity;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;

class LoginPresenter extends BasePresenter {

    private LoginActivity view;

    void attachView(LoginActivity loginActivity) {
        view = loginActivity;
    }

    LoginPresenter(Context context) {
        super(context);
    }

    void signIn(String email, String password){

        view.hideKeyboard();

        if (hasInternetConnection()) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, task -> {
                        view.setProgressVisibilityGone();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            view.finishActivity();
                        }else {
                            view.showSnackBar(R.string.auth_failed);
                        
                        }
                    });
        }else {
            view.setProgressVisibilityGone();
            view.showSnackBar(R.string.no_interent_connection_err);
        }
    }
}
