package com.kamil184.lingly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kamil184.lingly.base.BaseActivity;
import com.kamil184.lingly.main.authorization.Registration.ComplexPreferences;
import com.kamil184.lingly.main.authorization.login.LoginActivity;

import static com.kamil184.lingly.Constants.UserData.APP_PREFERENCES;

public class MainActivity extends BaseActivity {

    BottomNavigationView navView;
    NavController navController;
    public static SharedPreferences mSettings;
    public static SharedPreferences.Editor editor;
    public static ComplexPreferences complexSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        complexSettings = ComplexPreferences.getComplexPreferences(getBaseContext(), APP_PREFERENCES, MODE_PRIVATE);
        editor = mSettings.edit();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        if (!isAuthorized()){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
