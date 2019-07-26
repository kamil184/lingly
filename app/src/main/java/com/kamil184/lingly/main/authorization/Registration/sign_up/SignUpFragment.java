package com.kamil184.lingly.main.authorization.Registration.sign_up;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseFragment;
import com.kamil184.lingly.main.authorization.ResetPasswordActivity;
import com.kamil184.lingly.main.authorization.login.LoginActivity;


import butterknife.BindView;
import butterknife.ButterKnife;


public class SignUpFragment extends BaseFragment {

    @BindView(R.id.btn_login) MaterialButton btnSignIn;
    @BindView(R.id.btn_signup) MaterialButton btnSignUp;
    @BindView(R.id.btn_reset_password) MaterialButton btnResetPassword;
    @BindView(R.id.email) TextInputEditText inputEmail;
    @BindView(R.id.email_text_input_layout) TextInputLayout emailInputLayout;
    @BindView(R.id.password) TextInputEditText inputPassword;
    @BindView(R.id.password_text_input_layout) TextInputLayout passwordInputLayout;
    @BindView(R.id.us_name) TextInputEditText inputName;
    @BindView(R.id.us_name_text_input_layout) TextInputLayout nameInputLayout;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.signup_container) CoordinatorLayout container1;

    AnimationDrawable anim;
    SignUpPresenter presenter;

    Vibrator vibrator;
    long mills = 300;

    public interface Callback{
        void toUserInfo();
    }

    SignUpFragment.Callback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (SignUpFragment.Callback) context;
        presenter = new SignUpPresenter(context);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.activity_signup, container, false);
        ButterKnife.bind(this, view1);

        presenter.attachView(this);
        anim = (AnimationDrawable) container1.getBackground();
        anim.setEnterFadeDuration(0);
        anim.setExitFadeDuration(1000);

        inputPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = inputPassword.getText().toString();
                if(isPasswordNotValidate(password)) {
                    passwordInputLayout.setError(getPasswordValidateMessage(password));
                } else passwordInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        inputEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        inputEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String email = inputEmail.getText().toString();
                if(isEmailNotValidate(email)) {
                    emailInputLayout.setError(getString(R.string.email_err));
                } else emailInputLayout.setErrorEnabled(false);
            }
        });

        inputName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        inputName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String name = inputName.getText().toString();
                if(isNameNotValidate(name)) {
                    nameInputLayout.setError(getString(R.string.login_empty));
                } else nameInputLayout.setErrorEnabled(false);
            }
        });

        //TODO кнопки

        btnResetPassword.setOnClickListener(v -> startActivity(new Intent(getActivity(), ResetPasswordActivity.class)));

        btnSignIn.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginActivity.class)));

        btnSignUp.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String name = inputName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if(validate(name,email,password)){
                presenter.signUpWithEmail(name,email,password);
                callback.toUserInfo();
            }
        });

        return view1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    void setProgressVisibilityGone(){
        progressBar.setVisibility(View.GONE);
    }

    private boolean isEmailNotValidate(String email) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } return false;
    }

    private boolean isNameNotValidate(String name){
        if (name.isEmpty()){
            return true;
        } return false;
    }

    private boolean isPasswordNotValidate(String password) {
        if (password.length() < 6) {
            return true;
        } return false;
    }

    private String getPasswordValidateMessage(String password){
        return password.length() + "/6+";
    }

    private boolean validate(String name,String email,String password) {
        boolean validate = true;
        if (isEmailNotValidate(email)) {
            emailInputLayout.setError(getString(R.string.email_err));
            setProgressVisibilityGone();
            YoYo.with(Techniques.Shake)
                    .duration(200)
                    .repeat(1)
                    .playOn(inputEmail);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills);
            }
            validate = false;
        }
        if (isPasswordNotValidate(password)) {
            passwordInputLayout.setError(getString(R.string.minimum_password));
            setProgressVisibilityGone();
            YoYo.with(Techniques.Shake)
                    .duration(200)
                    .repeat(1)
                    .playOn(inputPassword);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills);
            }
            validate = false;
        }
        if(isNameNotValidate(name)){
            nameInputLayout.setError(getString(R.string.login_empty));
            setProgressVisibilityGone();
            YoYo.with(Techniques.Shake)
                    .duration(200)
                    .repeat(1)
                    .playOn(inputName);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills);
            }
            validate = false;
        }
    return validate;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

}