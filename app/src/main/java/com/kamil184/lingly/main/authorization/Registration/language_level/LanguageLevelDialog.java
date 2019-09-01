package com.kamil184.lingly.main.authorization.Registration.language_level;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.kamil184.lingly.Constants;
import com.kamil184.lingly.R;

public class LanguageLevelDialog extends DialogFragment implements View.OnClickListener{

    final String LOG_TAG = "myLogs";
    private ImageView l1,l2,l3,l4,l5,flag;
    private int languageId,languageLevel;

    public  LanguageLevelDialog(int languageId){
        this.languageId = languageId;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Уровень владения языком");
        View v = inflater.inflate(R.layout.dialog_language_level, null);
        l1 = v.findViewById(R.id.btn_level1);
        l2 = v.findViewById(R.id.btn_level2);
        l3 = v.findViewById(R.id.btn_level3);
        l4 = v.findViewById(R.id.btn_level4);
        l5 = v.findViewById(R.id.btn_level5);
        flag = v.findViewById(R.id.country_image);

        flag.setImageResource(Constants.Languages.flagArray[languageId]);

        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
        return v;
    }

    public void onClick(View v) {
        Log.d(LOG_TAG, "Dialog 1: " + v.getContentDescription()+" "+languageId);
        String btnId;
        btnId = v.getContentDescription().toString();
        switch (btnId) {
            case "level1":
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_grey2);
                l3.setImageResource(R.drawable.language_level_grey3);
                l4.setImageResource(R.drawable.language_level_grey4);
                l5.setImageResource(R.drawable.language_level_grey5);
               languageLevel = 1;
                break;
            case "level2":
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_color2);
                l3.setImageResource(R.drawable.language_level_grey3);
                l4.setImageResource(R.drawable.language_level_grey4);
                l5.setImageResource(R.drawable.language_level_grey5);
                languageLevel = 2;
                break;
            case "level3":
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_color2);
                l3.setImageResource(R.drawable.language_level_color3);
                l4.setImageResource(R.drawable.language_level_grey4);
                l5.setImageResource(R.drawable.language_level_grey5);
                languageLevel = 3;
                break;
            case "level4":
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_color2);
                l3.setImageResource(R.drawable.language_level_color3);
                l4.setImageResource(R.drawable.language_level_color4);
                l5.setImageResource(R.drawable.language_level_grey5);
                languageLevel = 4;
                break;
            case "level5":
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_color2);
                l3.setImageResource(R.drawable.language_level_color3);
                l4.setImageResource(R.drawable.language_level_color4);
                l5.setImageResource(R.drawable.language_level_color5);
               languageLevel = 5;
                break;
        }
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        SelectLanguageLevelPresenter presenter = new SelectLanguageLevelPresenter(getContext());
        presenter.setLanguageLevel(languageLevel,languageId);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }
}