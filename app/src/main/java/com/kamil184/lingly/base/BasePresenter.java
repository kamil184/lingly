package com.kamil184.lingly.base;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BasePresenter {

    protected Context context;
    protected FirebaseAuth auth;

    public BasePresenter(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
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
        return FirebaseAuth.getInstance().getUid();
    }
    protected FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }
}
