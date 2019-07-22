package com.kamil184.lingly.main.authorization;

import android.os.Bundle;



import android.text.TextUtils;

import android.view.View;

import android.widget.Button;

import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseActivity;
import com.rengwuxian.materialedittext.MaterialEditText;


public class ResetPasswordActivity extends BaseActivity {


    private MaterialEditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        inputEmail =  findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        auth = FirebaseAuth.getInstance();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    showSnackBar(v,R.string.email_err);
                    return;

                }
//TODO сообщения в ресурсы

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showSnackBar(R.string.pass_reset_sucss);
                                } else {
                                    showSnackBar(R.string.pass_reset_fail);
                                }



                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }


}
