package com.kamil184.lingly.main.authorization;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.kamil184.lingly.MainActivity;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseActivity;
import com.kamil184.lingly.util.AnimationsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResetPasswordActivity extends BaseActivity {

    private FirebaseAuth auth;
    AnimationDrawable anim;

    @BindView(R.id.email) TextInputEditText inputEmail;
    @BindView(R.id.btn_reset_password) Button btnReset;
    @BindView(R.id.btn_back) Button btnBack;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.linear) CoordinatorLayout layout;
    @BindView(R.id.email_text_input_layout) TextInputLayout emailInputLayout;

    Vibrator vibrator;
    long mills = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(this);
        anim = (AnimationDrawable) layout.getBackground();
        anim.setEnterFadeDuration(0);
        anim.setExitFadeDuration(1000);
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        auth = FirebaseAuth.getInstance();
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

        btnBack.setOnClickListener(v -> finish());
        btnReset.setOnClickListener(v -> {
            this.hideKeyboard();
            this.showProgress();
            String email = inputEmail.getText().toString();
            if(!isEmailNotValidate(email)){
                emailInputLayout.setErrorEnabled(false);
                sendResetMail(email);
            }else {
                AnimationsUtil.shakeAnimation(emailInputLayout);
                if (vibrator.hasVibrator()) {
                    vibrator.vibrate(mills);
                }
                emailInputLayout.setError(getString(R.string.email_err));
               this.hideProgress();
            }
        });
    }

    private boolean isEmailNotValidate(String email) {
        return email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void sendResetMail(String email){
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showSnackBar(R.string.pass_reset_sucss);
                        Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        showSnackBar(R.string.pass_reset_fail);
                    }
                   this.hideProgress();
                });
    }

}
