package com.kamil184.lingly.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class BasePresenter {

    protected Context context;
    protected FirebaseAuth auth;
    protected FirebaseFirestore db;
    protected FirebaseUser user;

    public BasePresenter(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = getCurrentUser();
    }


    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean isAuthorized(){
        return auth.getCurrentUser() != null;
    }

    protected String getCurrentUserId() {
        return auth.getCurrentUser().getUid();
    }
    protected FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }
    protected String getCurrentUserEmail(){return auth.getCurrentUser().getEmail();}
}
