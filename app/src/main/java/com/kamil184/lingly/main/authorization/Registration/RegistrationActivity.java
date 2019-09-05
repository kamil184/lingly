package com.kamil184.lingly.main.authorization.Registration;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseActivity;
import com.kamil184.lingly.main.authorization.Registration.language_level.SelectLanguageLevelFragment;
import com.kamil184.lingly.main.authorization.Registration.native_language.NativeLanguageFragment;
import com.kamil184.lingly.main.authorization.Registration.non_native_language.NonNativeLanguageFragment;
import com.kamil184.lingly.main.authorization.Registration.sign_up.ExtraUserInfoFragment;
import com.kamil184.lingly.main.authorization.Registration.sign_up.SignUpFragment;

public class RegistrationActivity extends BaseActivity implements SignUpFragment.Callback, ExtraUserInfoFragment.Callback, NativeLanguageFragment.Callback, NonNativeLanguageFragment.Callback, SelectLanguageLevelFragment.Callback {

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
        navController.navigate(R.id.nativeLanguage);
    }

    @Override
    public void toNonNativeLanguage(Bundle bundle) {
        navController.navigate(R.id.nonNativeLanguage, bundle);
    }

    @Override
    public void toSelectLanguageLevel(Bundle bundle) {
        navController.navigate(R.id.selectLanguageLevel, bundle);
    }
    @Override
    public void toMainActivity(){
        navController.navigate(R.id.mainActivity2);
    }
}
