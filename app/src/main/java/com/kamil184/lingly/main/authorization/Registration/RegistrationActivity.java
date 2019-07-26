package com.kamil184.lingly.main.authorization.Registration;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseActivity;
import com.kamil184.lingly.main.authorization.Registration.sign_up.SignUpFragment;

public class RegistrationActivity extends BaseActivity implements SignUpFragment.Callback {

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    }

    @Override
    public void toUserInfo() {
        navController.navigate(R.id.extraInfo);
    }
}
