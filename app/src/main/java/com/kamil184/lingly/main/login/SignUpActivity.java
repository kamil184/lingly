package com.kamil184.lingly.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kamil184.lingly.MainActivity;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseActivity;
import com.rengwuxian.materialedittext.MaterialEditText;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class SignUpActivity extends BaseActivity {


    private DatabaseReference mDatabase;
    private MaterialEditText inputEmail, inputPassword, inputName;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        btnSignIn =findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputName = findViewById(R.id.us_name);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        CheckBox onpass = (CheckBox) findViewById(R.id.onpass);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
            }
        });



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        onpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    inputPassword.setTransformationMethod(null);
                }
                else {
                    inputPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
                inputPassword.setSelection(inputPassword.length());
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = inputName.getText().toString().trim();
                final String email = inputEmail.getText().toString().trim();
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



                if (password.length() < 6) {
                   inputPassword.setError("Слишком короткий пароль, введите минимум 6 символов!");
                    return;
                }



                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    showSnackBar(R.string.signup_err);
                                } else {
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", name);
                                    user.put("subscribers", new ArrayList<>());
                                    user.put("subscriptions", new ArrayList<>());


                                    db.collection("users").document(email)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("register", "DocumentSnapshot successfully written!");
                                                }
                                            })

                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("register", "Error writing document", e);
                                                }
                                            });
                                   showWarningDialog(R.string.signup_sucss);
                                   startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                   finish();
                                }
                            }
                        });
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}