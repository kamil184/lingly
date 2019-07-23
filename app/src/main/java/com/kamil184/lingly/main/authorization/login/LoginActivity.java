package com.kamil184.lingly.main.authorization.login;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseActivity;
import com.kamil184.lingly.main.authorization.ResetPasswordActivity;
import com.kamil184.lingly.main.authorization.Registration.SignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email) TextInputEditText inputEmail;
    @BindView(R.id.email_text_input_layout) TextInputLayout emailInputLayout;
    @BindView(R.id.password) TextInputEditText inputPassword;
    @BindView(R.id.password_text_input_layout) TextInputLayout passwordInputLayout;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.btn_login) MaterialButton btnLogin;
    @BindView(R.id.btn_signup) MaterialButton btnSignup;
    @BindView(R.id.btn_reset_password) MaterialButton btnReset;
    @BindView(R.id.container1) CoordinatorLayout container;
    AnimationDrawable anim;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        presenter.attachView(this);
        ButterKnife.bind(this);

        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(100);
        anim.setExitFadeDuration(1000);

        btnSignup.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        btnReset.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class)));

        btnLogin.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            boolean validate = true;
            if (isEmailNotValidate(email)) {
                emailInputLayout.setError(getString(R.string.email_err));
                setProgressVisibilityGone();
                // TODO: вибрация
                validate = false;
            }
            if (isPasswordNotValidate(password)) {
                passwordInputLayout.setError(getString(R.string.minimum_password));
                setProgressVisibilityGone();
                // TODO: вибрация
                validate = false;
            }
            if(validate) {
                presenter.signIn(email, password);
            }
        });

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

        inputEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String email = inputEmail.getText().toString();
                if(isEmailNotValidate(email)) {
                    emailInputLayout.setError(getString(R.string.email_err));
                } else emailInputLayout.setErrorEnabled(false);
            }
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

    }

    @Override
    public void onBackPressed() {}

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

    private boolean isPasswordNotValidate(String password) {
        if (password.length() < 6) {
            return true;
        } return false;
    }

    private String getPasswordValidateMessage(String password){
        return password.length() + "/6+";
    }

    @Override
    protected void onResume() {
        super.onResume();
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
