package com.kamil184.lingly.main.authorization.Registration.sign_up;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseFragment;
import com.kamil184.lingly.main.authorization.ResetPasswordActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExtraUserInfoFragment extends BaseFragment {
    @BindView(R.id.birth_date_txt) TextView birth_txt;
    @BindView(R.id.first_name) TextInputEditText inputFirstName;
    @BindView(R.id.first_name_text_input_layout) TextInputLayout firstNameInputLayout;
    @BindView(R.id.second_name) TextInputEditText inputSecondName;
    @BindView(R.id.second_name_text_input_layout) TextInputLayout secondNameInputLayout;
    @BindView(R.id.datePicker) DatePicker datePicker;
    @BindView(R.id.btn_next) MaterialButton btn_next_step;
   // @BindView(R.id.progressBar)
   // ProgressBar progressBar;
    @BindView(R.id.container)
    CoordinatorLayout container1;

    AnimationDrawable anim;
    ExtraUserInfoPresenter presenter;

    Vibrator vibrator;
    long mills = 300;

    public interface Callback{
        void toNativeLanguage();
    }

    ExtraUserInfoFragment.Callback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (ExtraUserInfoFragment.Callback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_extrauserinfo, container, false);
        ButterKnife.bind(this, view1);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        presenter = new ExtraUserInfoPresenter(getActivity());
        presenter.attachView(this);
        anim = (AnimationDrawable) container1.getBackground();
        anim.setEnterFadeDuration(0);
        anim.setExitFadeDuration(1000);


        inputFirstName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstNameInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        inputSecondName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                secondNameInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        inputFirstName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String firstname = inputFirstName.getText().toString();
                if(isNameNotValidate(firstname)) {
                    firstNameInputLayout.setError(getString(R.string.empty_field_err));
                } else firstNameInputLayout.setErrorEnabled(false);
            }
        });

        inputSecondName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String secondname = inputSecondName.getText().toString();
                if(isNameNotValidate(secondname)) {
                    secondNameInputLayout.setError(getString(R.string.empty_field_err));
                } else secondNameInputLayout.setErrorEnabled(false);
            }
        });

        Calendar today = Calendar.getInstance();

        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), (view, year, monthOfYear, dayOfMonth) ->
                       birth_txt.setText(getString(R.string.date_of_birth) + " "+dayOfMonth + "." + ""
                               + (monthOfYear + 1) + "." + "" + year));



        btn_next_step.setOnClickListener(v -> {
            String firstName = inputFirstName.getText().toString();
            String secondName = inputSecondName.getText().toString();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth()+1;
            int year =  datePicker.getYear();
            if(validate(firstName,secondName))
            presenter.addExtraInfo(firstName,secondName,day,month,year);
        });



        return view1;
    }



    private boolean isNameNotValidate(String name){
        if (name.isEmpty()){
            return true;
        } return false;
    }

    private boolean validate(String firstName,String secondName) {
        boolean validate = true;
        if (isNameNotValidate(firstName)) {
            firstNameInputLayout.setError(getString(R.string.empty_field_err));
            //TODO setProgressVisibilityGone();
            YoYo.with(Techniques.Shake)
                    .duration(200)
                    .repeat(1)
                    .playOn(inputFirstName);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills);
            }
            validate = false;
        }
        if (isNameNotValidate(secondName)) {
            secondNameInputLayout.setError(getString(R.string.empty_field_err));
            //TODO setProgressVisibilityGone();
            YoYo.with(Techniques.Shake)
                    .duration(200)
                    .repeat(1)
                    .playOn(inputSecondName);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills);
            }
            validate = false;
        }
        //TODO проверка на возраст
        return validate;
    }

    @Override
    public void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
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
