package com.kamil184.lingly.main.authorization.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kamil184.lingly.MainActivity;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BasePresenter;
import com.kamil184.lingly.main.authorization.Registration.ComplexPreferences;
import com.kamil184.lingly.main.authorization.Registration.NonNativeLanguageList;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_ABOUT;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_BIRTHDAY;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_BIRTHMONTH;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_BIRTHYEAR;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_EMAIL;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_FIRSTNAME;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_NATIVE_LANGUAGES;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_NON_NATIVE_LANGUAGES;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_SECONDNAME;
import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES_STATUS;
import static com.kamil184.lingly.main.authorization.Registration.RegistrationActivity.editor;

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
            user = getCurrentUser();
            final DocumentReference userRef = db.collection("users").document(user.getUid());
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        SharedPreferences mSettings = view.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putString(APP_PREFERENCES_EMAIL, document.get(APP_PREFERENCES_EMAIL).toString());
                        editor.putString(APP_PREFERENCES_FIRSTNAME, document.get(APP_PREFERENCES_FIRSTNAME).toString());
                        editor.putString(APP_PREFERENCES_SECONDNAME, document.get(APP_PREFERENCES_SECONDNAME).toString());
                        editor.putInt(APP_PREFERENCES_BIRTHDAY, (int)document.get(APP_PREFERENCES_BIRTHDAY));
                        editor.putInt(APP_PREFERENCES_BIRTHMONTH, (int)document.get(APP_PREFERENCES_BIRTHMONTH));
                        editor.putInt(APP_PREFERENCES_BIRTHYEAR, (int)document.get(APP_PREFERENCES_BIRTHYEAR));
                        editor.putString(APP_PREFERENCES_STATUS, document.get(APP_PREFERENCES_STATUS).toString());
                        editor.putString(APP_PREFERENCES_ABOUT, document.get(APP_PREFERENCES_ABOUT).toString());
                        editor.apply();
                    } else {
                        view.showWarningDialog("Такого пользователя нет!");
                        //TODO проверка на завершение регистрации
                        view.hideProgress();
                    }
                } else {
                    view.showSnackBar(R.string.err);
                    view.hideProgress();
                }
            });
            final CollectionReference reference = userRef.collection("languages");
            reference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ComplexPreferences complexSettings = ComplexPreferences.getComplexPreferences(view.getBaseContext(), APP_PREFERENCES, MODE_PRIVATE);
                    NonNativeLanguageList list = new NonNativeLanguageList();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        switch (document.getId()){
                            case "languages_levels":
                                for (int i = 0; i < list.getLanguageLevelModels().size(); i++) {
                                    list.setLanguageLevel((int)document.get(String.valueOf(i+1)), i);
                                }
                                break;
                            case "languages_non_native":
                                list.setLanguageLevelModels(null, (List<Long>) document.get("non_native_languages"));
                                break;
                            case "language_native":
                                complexSettings.putObject(APP_PREFERENCES_NATIVE_LANGUAGES, document.get("native_languages"));
                                break;
                        }
                    }
                    complexSettings.putObject(APP_PREFERENCES_NON_NATIVE_LANGUAGES, list);
                }
            });
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
