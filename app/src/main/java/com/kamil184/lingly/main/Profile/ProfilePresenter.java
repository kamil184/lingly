package com.kamil184.lingly.main.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.StringRes;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kamil184.lingly.Constants;
import com.kamil184.lingly.R;
import com.kamil184.lingly.adapters.LanguageLevelAdapter;
import com.kamil184.lingly.base.BasePresenter;
import com.kamil184.lingly.main.authorization.Registration.NativeLanguageList;
import com.kamil184.lingly.main.authorization.Registration.NonNativeLanguageList;
import com.kamil184.lingly.models.LanguageLevelModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kamil184.lingly.MainActivity.complexSettings;
import static com.kamil184.lingly.MainActivity.mSettings;

class ProfilePresenter extends BasePresenter {

    private ProfileFragment view;
    private FirebaseUser user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    //private HashMap<String, Object> map;
    LanguageLevelAdapter languageLevelAdapter;
    ArrayList<LanguageLevelModel> arrayList = new ArrayList<>();

    private ArrayList<Long> nativeLanguages = new ArrayList<>();
    private ArrayList<Long> nonNativeLanguages = new ArrayList<>();
    @SuppressLint("UseSparseArrays")
    private HashMap<Long, Long> languagesLevels = new HashMap<>();

    ProfilePresenter(Context context) {
        super(context);
    }


    void attachView(ProfileFragment profileFragment) {
        view = profileFragment;
    }

    void profileFill() throws java.lang.NullPointerException {
        view.collapsingToolbarLayout.setTitle(mSettings.getString(Constants.UserData.APP_PREFERENCES_FIRSTNAME, "")
                + " " + mSettings.getString(Constants.UserData.APP_PREFERENCES_SECONDNAME, ""));
        view.birthDate.setText(mSettings.getInt(Constants.UserData.APP_PREFERENCES_BIRTHDAY, 0)
                + "." + mSettings.getInt(Constants.UserData.APP_PREFERENCES_BIRTHMONTH, 0)
                + "." + mSettings.getInt(Constants.UserData.APP_PREFERENCES_BIRTHYEAR, 0));
        view.collapsingToolbarLayout.setSubtitle(mSettings.getString(Constants.UserData.APP_PREFERENCES_EMAIL, ""));
        if (hasInternetConnection()) {
            view.showProgress();
            user = getCurrentUser();
            final DocumentReference userRef = db.collection("users").document(user.getUid());
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        view.collapsingToolbarLayout.setSubtitle(user.getEmail());
                        view.status.setText(document.get("status").toString());
                        view.about.setText(document.get("about").toString());
                        getLanguagesInfo();
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
        } else {
            view.showSnackBar(R.string.no_interent_connection_err);
            view.finish();
        }
    }


    void getAvatarUri() {
        user = getCurrentUser();
        final StorageReference avatarRef = storageRef.child("users/" + user.getEmail() + "/avatar/");
        avatarRef.getDownloadUrl()
                .addOnSuccessListener(this::setAvatar)
                .addOnFailureListener(e -> setAvatar(null));

    }

    private void setAvatar(Uri uri) throws java.lang.NullPointerException {
        try {
            if (uri != null) {
                Picasso.get()
                        .load(uri)
                        .into(view.avatar);
            }
        } catch (NullPointerException ignored) {
        }
    }


    void uploadAvatar(Uri uri) {
        final StorageReference avatarRef = storageRef.child("users/" + user.getEmail() + "/avatar/");
        UploadTask uploadTask = avatarRef.putFile(uri);
        view.showProgress(R.string.avatar_load);
        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                view.showWarningDialog("Аватарка загружена");
                view.hideProgress();
            } else {
                view.hideProgress();
                view.showWarningDialog("Ошибка при загрузке аватарки");
            }
        });

    }

    private void getLanguagesInfo() {
        //загрузить из памяти
        nativeLanguages = (ArrayList)complexSettings.getObject(Constants.UserData.APP_PREFERENCES_NATIVE_LANGUAGES, NativeLanguageList.class)
                .getLanguages();
        getNonNativeLanguagesInfo();

        //загрузить с сервера
        /*user = getCurrentUser();
        final DocumentReference userRef = db.collection("users").document(user.getUid());
        final CollectionReference languageRef = userRef.collection("languages");
        languageRef.document("languages_native").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot nativeDocument = task.getResult();
                if (nativeDocument.exists()) {
                    nativeLanguages = (ArrayList) nativeDocument.get("native_languages");
                    getNonNativeLanguagesInfo();
                } else {
                    view.showWarningDialog("Такого пользователя нет!");
                    view.hideProgress();
                }
            } else {
                view.showSnackBar(R.string.err);
                view.hideProgress();
            }
        });*/
    }

    private void getNonNativeLanguagesInfo() {
        //загрузить из памяти
        List<LanguageLevelModel> languageList = complexSettings.getObject(Constants.UserData.APP_PREFERENCES_NON_NATIVE_LANGUAGES, NonNativeLanguageList.class)
                .getLanguageLevelModels();
        for (int i = 0; i < languageList.size(); i++) {
            nonNativeLanguages.add(languageList.get(i).getLanguage());
        }
        getLanguageLevels();

        //загрузить с сервера
        /*user = getCurrentUser();
        final DocumentReference userRef = db.collection("users").document(user.getUid());
        final CollectionReference languageRef = userRef.collection("languages");
        languageRef.document("languages_non_native").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot nonNativeDocument = task.getResult();
                if (nonNativeDocument.exists()) {
                    nonNativeLanguages = (ArrayList) nonNativeDocument.get("non_native_languages");
                    getLanguageLevels();
                } else {
                    view.showWarningDialog("Такого пользователя нет!");
                    view.hideProgress();
                }
            } else {
                view.showSnackBar(R.string.err);
                view.hideProgress();
            }
        });*/
    }

    private void getLanguageLevels() {
        //загрузить из памяти
        List<LanguageLevelModel> languageList = complexSettings.getObject(Constants.UserData.APP_PREFERENCES_NON_NATIVE_LANGUAGES, NonNativeLanguageList.class)
                .getLanguageLevelModels();
        for (int i = 0; i < nonNativeLanguages.size(); i++) {
            languagesLevels.put(nonNativeLanguages.get(i), languageList.get(i).getLanguageLevel());
        }
        for (int i = 0; i < nonNativeLanguages.size(); i++) {
            arrayList.add(new LanguageLevelModel(languagesLevels.get(nonNativeLanguages.get(i)), nonNativeLanguages.get(i)));
        }
        languageLevelAdapter = new LanguageLevelAdapter(view.getContext(), arrayList);
        view.nativeLanguages.setAdapter(languageLevelAdapter);
        view.hideProgress();

        //загрузить с сервера
        /*user = getCurrentUser();
        final DocumentReference userRef = db.collection("users").document(user.getUid());
        final CollectionReference languageRef = userRef.collection("languages");
        languageRef.document("languages_levels").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot levelsDocument = task.getResult();
                if (levelsDocument.exists()) {
                    for (int i = 0; i < nonNativeLanguages.size(); i++) {
                        languagesLevels.put(nonNativeLanguages.get(i), (long) levelsDocument.get("" + nonNativeLanguages.get(i)));
                    }
                    for (int i = 0; i < nonNativeLanguages.size(); i++) {
                        arrayList.add(new LanguageLevelModel(languagesLevels.get(nonNativeLanguages.get(i)), nonNativeLanguages.get(i)));
                    }


                    //Для родных языков(nativeLanguages) не надо будет использовать LanguageLevelModel
                    //т.к. они не содержат уровни знания языка


                    languageLevelAdapter = new LanguageLevelAdapter(view.getContext(), arrayList);
                    view.nativeLanguages.setAdapter(languageLevelAdapter);
                    view.hideProgress();
                } else {
                    view.showWarningDialog("Такого пользователя нет!");
                    view.hideProgress();
                }
            } else {
                view.showSnackBar(R.string.err);
                view.hideProgress();
            }
        });*/
    }


    void editField(String fieldText, String fieldType) {
        view.hideKeyboard();
        if (hasInternetConnection()) {
            Map<String, Object> user = new HashMap<>();
            user.put(fieldType, fieldText);
            if (isAuthorized()) {
                db.collection("users").document(getCurrentUserId())
                        .update(user)
                        .addOnSuccessListener(aVoid -> {
                        })
                        .addOnFailureListener(e -> {
                            ifError(R.string.signup_err);
                        });
            } else {
                ifError(R.string.not_auth_err);
            }
        } else {
            ifError(R.string.no_interent_connection_err);
        }
    }


    void ifError(@StringRes int message) {
        view.showSnackBar(message);
    }
}
