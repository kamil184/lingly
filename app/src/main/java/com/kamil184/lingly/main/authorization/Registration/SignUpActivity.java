package com.kamil184.lingly.main.authorization.Registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseActivity;
import com.kamil184.lingly.main.authorization.ResetPasswordActivity;
import com.kamil184.lingly.main.authorization.login.LoginActivity;
import com.rengwuxian.materialedittext.MaterialEditText;


import butterknife.BindView;
import butterknife.ButterKnife;


public class SignUpActivity extends BaseActivity {

    @BindView(R.id.sign_in_button) Button btnSignIn;
    @BindView(R.id.sign_up_button) Button btnSignUp;
    @BindView(R.id.btn_reset_password) Button btnResetPassword;
    @BindView(R.id.email) TextInputEditText inputEmail;
    @BindView(R.id.email_text_input_layout) TextInputLayout emailInputLayout;
    @BindView(R.id.password) TextInputEditText inputPassword;
    @BindView(R.id.password_text_input_layout) TextInputLayout passwordInputLayout;
    @BindView(R.id.us_name) TextInputEditText inputName;
    @BindView(R.id.us_name_text_input_layout) TextInputLayout nameInputLayout;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.signup_container) CoordinatorLayout container;

    AnimationDrawable anim;
    SignUpPresenter presenter;

    long mills = 300;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        presenter = new SignUpPresenter(this);
        presenter.attachView(this);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        anim = (AnimationDrawable) container.getBackground();
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

        btnResetPassword.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class)));



        btnSignIn.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));



        btnSignUp.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String name = inputName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if(validate(name,email,password)){
                presenter.signUpWithEmail(name,email,password);
            }
        });
    }

    void setProgressVisibilityGone(){
        progressBar.setVisibility(View.GONE);
    }

    void finishActivity(){
        finish();
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
                    .duration(400)
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
                    .duration(400)
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
                    .duration(400)
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
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

}