package com.kamil184.lingly.main.authorization.Registration.sign_up;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_EMAIL;
import static com.kamil184.lingly.main.authorization.Registration.RegistrationActivity.editor;


class SignUpPresenter extends BasePresenter {

    private SignUpFragment view;
    private FirebaseUser user;
    private DocumentReference userRef;
    private CollectionReference languagesRef;

    void attachView(SignUpFragment signUpActivity) {
        view = signUpActivity;
    }

    SignUpPresenter(Context context) {
        super(context);
    }

    void signUpWithEmail(String email, String password) {
        editor.putString(APP_PREFERENCES_EMAIL, email);
        editor.commit();
        view.hideKeyboard();
        if (hasInternetConnection()) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, task -> {
                        if (!task.isSuccessful()) {
                            view.setProgressVisibilityGone();
                            view.showSnackBar(R.string.signup_err);
                        } else {
                            userRef = db.collection("users").document(getCurrentUserId());
                            languagesRef = userRef.collection("languages");
                            createUserInfo();
                        }
                    });
        } else {
            view.setProgressVisibilityGone();
            view.showSnackBar(R.string.no_interent_connection_err);
        }

    /*
    Ну тут уж все очевидно, я считаю
    */

    }

    private void createUserInfo() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", getCurrentUserEmail());
        userRef
                .set(userInfo)
                .addOnSuccessListener(aVoid -> createLanguagesInfo())
                .addOnFailureListener(e -> ifError(R.string.signup_err));
    }

    /*
    Метод createLanguageInfo() создает документы в коллекцию languages, туда же кладет информацию о том, выбран язык или нет,
    в будущем это надо будет для проверки, заполнил ли юзер свои данные.
    Метод пытается создать документ, если success, то пытается для другого документа и так , пока либо не выведет ошибку, либо не создаст все документы
     */

    private void createLanguagesInfo() {
        Map<String, Object> languagesInfo = new HashMap<>();
        languagesInfo.put("is_selected", false);
        languagesRef.document("languages_levels")
                .set(languagesInfo)
                .addOnSuccessListener(aVoid -> {
                    languagesRef.document("languages_native")
                            .set(languagesInfo)
                            .addOnSuccessListener(aVoid1 -> {
                                languagesRef.document("languages_non_native")
                                        .set(languagesInfo)
                                        .addOnSuccessListener(aVoid2 -> switchToNextStep())
                                        .addOnFailureListener(e -> ifError(R.string.signup_err));
                            })
                            .addOnFailureListener(e -> ifError(R.string.signup_err));
                })
                .addOnFailureListener(e -> ifError(R.string.signup_err));
    }


    private void switchToNextStep() {
        view.setProgressVisibilityGone();
        view.callback.toUserInfo();
    }

    void ifError(@StringRes int message) {
        view.progressBar.setVisibility(View.GONE);
        view.showSnackBar(message);
    }

}
