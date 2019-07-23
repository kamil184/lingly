package com.kamil184.lingly.main.authorization.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseActivity;
import com.kamil184.lingly.main.authorization.ResetPasswordActivity;
import com.kamil184.lingly.main.authorization.SignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email) TextInputEditText inputEmail;
    @BindView(R.id.email_text_input_layout) TextInputLayout emailInputLayout;
    @BindView(R.id.password) TextInputEditText inputPassword;
    @BindView(R.id.password_text_input_layout) TextInputLayout passwordInputLayout;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.btn_signup) Button btnSignup;
    @BindView(R.id.btn_reset_password) Button btnReset;
    @BindView(R.id.panorama_image_view) PanoramaImageView panoramaImageView;

    private GyroscopeObserver gyroscopeObserver;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        presenter.attachView(this);
        ButterKnife.bind(this);

        gyroscopeObserver = new GyroscopeObserver();
        // Set the maximum radian the device should rotate to show image's bounds.
        // It should be set between 0 and π/2.
        // The default value is π/9.
        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);
        panoramaImageView.setEnablePanoramaMode(true);
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);


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
                inputPassword.setError(getString(R.string.email_err));
                validate = false;
            }
            if (isPasswordNotValidate(password)) {
                inputPassword.setError(getString(R.string.minimum_password));
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
        // Register GyroscopeObserver.
        gyroscopeObserver.register(this);
    }



    @Override
    protected void onPause() {
        super.onPause();
        // Unregister GyroscopeObserver.
        gyroscopeObserver.unregister();
    }
}



