package com.kamil184.lingly.main.authorization.Registration;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseActivity;
import com.kamil184.lingly.main.authorization.Registration.native_language.NativeLanguageFragment;
import com.kamil184.lingly.main.authorization.Registration.non_native_language.NonNativeLanguageFragment;
import com.kamil184.lingly.main.authorization.Registration.sign_up.ExtraUserInfoFragment;
import com.kamil184.lingly.main.authorization.Registration.sign_up.SignUpFragment;

public class RegistrationActivity extends BaseActivity implements SignUpFragment.Callback, ExtraUserInfoFragment.Callback, NativeLanguageFragment.Callback, NonNativeLanguageFragment.Callback {

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

    @Override
    public void toNativeLanguage() {
        navController.navigate(R.id.nativeLanguageFragment);
    }

    @Override
    public void toNonNativeLanguage() {
        navController.navigate(R.id.nonNativeLanguageFragment);
    }
    @Override
    public void toMainFragment(){
        navController.navigate(R.id.communityFragment);
    }
}
