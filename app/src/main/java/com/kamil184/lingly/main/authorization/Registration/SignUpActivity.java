package com.kamil184.lingly.main.authorization.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

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
    @BindView(R.id.email) MaterialEditText inputEmail;
    @BindView(R.id.password) MaterialEditText inputPassword;
    @BindView(R.id.us_name) MaterialEditText inputName;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        presenter = new SignUpPresenter(this);
        presenter.attachView(this);


        CheckBox onpass = (CheckBox) findViewById(R.id.onpass);

        btnResetPassword.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class)));



        btnSignIn.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));


        onpass.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                inputPassword.setTransformationMethod(null);
            }
            else {
                inputPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
            inputPassword.setSelection(inputPassword.length());
        });


        btnSignUp.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String name = inputName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                showWarningDialog("Введите ваше имя!");
                return;
            }
//TODO превести все сообщения в ресуры!!
            if (TextUtils.isEmpty(email)) {
                showSnackBar( R.string.email_signup_err);
                return;
            }
            if (TextUtils.isEmpty(password)) {
                showSnackBar(R.string.pass_signup_err);
                return;
            }
            if(validate(email,password)){
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

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
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
    private boolean validate(String email,String password) {
        boolean validate = true;
        if (isEmailNotValidate(email)) {
            inputEmail.setError(getString(R.string.email_err));
            setProgressVisibilityGone();
            validate = false;
        }
        if (isPasswordNotValidate(password)) {
            inputPassword.setError(getString(R.string.minimum_password));
            setProgressVisibilityGone();
            validate = false;
        }
    return validate;
    }

}