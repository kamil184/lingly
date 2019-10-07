package com.kamil184.lingly.main.authorization.Registration.language_level;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.kamil184.lingly.Constants;
import com.kamil184.lingly.R;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogEngine;

public class LanguageLevelDialog extends DialogFragment implements View.OnClickListener{

    final String LOG_TAG = "myLogs";
    private ImageView l1,l2,l3,l4,l5,flag;
    private int languageId,languageLevel = 1;
    private BlurDialogEngine mBlurEngine;
    private Button dis_btn, ok_btn;
    private TextView text;

    LanguageLevelDialog(int languageId){
        this.languageId = languageId;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Уровень владения языком");

        mBlurEngine = new BlurDialogEngine(getActivity());
        mBlurEngine.setBlurRadius(8);
        mBlurEngine.setDownScaleFactor(8f);
        mBlurEngine.debug(false);
        mBlurEngine.setBlurActionBar(true);
        mBlurEngine.setUseRenderScript(false);

        View v = inflater.inflate(R.layout.dialog_language_level, null);
        l1 = v.findViewById(R.id.btn_level1);
        l2 = v.findViewById(R.id.btn_level2);
        l3 = v.findViewById(R.id.btn_level3);
        l4 = v.findViewById(R.id.btn_level4);
        l5 = v.findViewById(R.id.btn_level5);
        flag = v.findViewById(R.id.country_image);
        dis_btn = v.findViewById(R.id.dis_btn);
        ok_btn = v.findViewById(R.id.ok_btn);
        text = v.findViewById(R.id.textView);

        flag.setImageResource(Constants.Languages.flagArray[languageId]);
        text.setText(Constants.Languages.languageArray[languageId]);

        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
        dis_btn.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
        return v;
    }

    public void onClick(View v){
        Log.d(LOG_TAG, "Dialog 1: " + v.getId()+" "+languageId);
        switch (v.getId()){
            case R.id.plus:
                if(languageLevel != 5)languageLevel++;
                setImage();
                break;
            case R.id.minus:
                if(languageLevel != 1)languageLevel--;
                setImage();
                break;
            case R.id.dis_btn:
                dismiss();
                break;
            case R.id.ok_btn:
                dismiss();
                SelectLanguageLevelPresenter presenter = new SelectLanguageLevelPresenter(getContext());
                presenter.setLanguageLevel(languageLevel,languageId);
                break;
        }
    }
    private void setImage() {
        switch (languageLevel) {
            case 1:
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_grey2);
                l3.setImageResource(R.drawable.language_level_grey3);
                l4.setImageResource(R.drawable.language_level_grey4);
                l5.setImageResource(R.drawable.language_level_grey5);
                break;
            case 2:
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_color2);
                l3.setImageResource(R.drawable.language_level_grey3);
                l4.setImageResource(R.drawable.language_level_grey4);
                l5.setImageResource(R.drawable.language_level_grey5);
                break;
            case 3:
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_color2);
                l3.setImageResource(R.drawable.language_level_color3);
                l4.setImageResource(R.drawable.language_level_grey4);
                l5.setImageResource(R.drawable.language_level_grey5);
                break;
            case 4:
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_color2);
                l3.setImageResource(R.drawable.language_level_color3);
                l4.setImageResource(R.drawable.language_level_color4);
                l5.setImageResource(R.drawable.language_level_grey5);
                break;
            case 5:
                l1.setImageResource(R.drawable.language_level_color1);
                l2.setImageResource(R.drawable.language_level_color2);
                l3.setImageResource(R.drawable.language_level_color3);
                l4.setImageResource(R.drawable.language_level_color4);
                l5.setImageResource(R.drawable.language_level_color5);
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mBlurEngine.onDismiss();
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBlurEngine.onDetach();
    }

    @Override public void onResume() {
        super.onResume();
        mBlurEngine.onResume(getRetainInstance());
    }

    @Override public void onDestroyView() {
        if (getDialog() != null) {
            getDialog().setDismissMessage(null); }
        super.onDestroyView(); }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }
}