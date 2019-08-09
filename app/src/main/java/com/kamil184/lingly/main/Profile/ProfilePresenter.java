package com.kamil184.lingly.main.Profile;

import android.content.Context;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kamil184.lingly.R;
import com.kamil184.lingly.adapters.LanguageLevelAdapter;
import com.kamil184.lingly.base.BasePresenter;
import com.kamil184.lingly.models.LanguageLevelModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


class ProfilePresenter extends BasePresenter {

    private ProfileFragment view;
    private FirebaseUser user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private HashMap<String, Object> map;
    LanguageLevelAdapter languageLevelAdapter;
    ArrayList<LanguageLevelModel> arrayList = new ArrayList<>();


    ProfilePresenter(Context context) {
        super(context);
    }


    void attachView(ProfileFragment profileFragment) {
        view = profileFragment;
    }

    void profileFill() throws java.lang.NullPointerException {
        if (hasInternetConnection()) {
            view.showProgress();
            user = getCurrentUser();
            final DocumentReference userRef = db.collection("users").document(user.getEmail());
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        view.collapsingToolbarLayout.setTitle(document.get("first_name").toString() + " " + document.get("second_name").toString());
                        view.collapsingToolbarLayout.setSubtitle(user.getEmail());
                        view.birthDate.setText(document.get("birth_day").toString() + "." + document.get("birth_month") + "." + document.get("birth_year"));

                        ArrayList<Long> languagesLevels = (ArrayList) document.get("language_levels");
                        ArrayList<Long> nativeLanguages = (ArrayList) document.get("native_languages");
                        ArrayList<Long> nonNativeLanguages = (ArrayList) document.get("non_native_languages");

                        for (int i = 0; i < nonNativeLanguages.size(); i++) {
                            arrayList.add(new LanguageLevelModel(languagesLevels.get(i), nonNativeLanguages.get(i)));
                        }

                        /*
                        Для родных языков(nativeLanguages) не надо будет использовать LanguageLevelModel
                        т.к. они не содержат уровни знания языка
                         */

                        languageLevelAdapter = new LanguageLevelAdapter(view.getContext(), arrayList);
                        view.nativeLanguages.setAdapter(languageLevelAdapter);
                        view.hideProgress();
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
                .addOnSuccessListener(uri -> setAvatar(uri))
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
        Uri file = uri;
        final StorageReference avatarRef = storageRef.child("users/" + user.getEmail() + "/avatar/");
        UploadTask uploadTask = avatarRef.putFile(file);
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


}
