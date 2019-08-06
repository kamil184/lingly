package com.kamil184.lingly.main.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.kamil184.lingly.Constants;
import com.kamil184.lingly.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageFragment extends Fragment {

    @BindView(R.id.country_image) AppCompatImageView flag;
    @BindView(R.id.level1) AppCompatImageView level1;
    @BindView(R.id.level2) AppCompatImageView level2;
    @BindView(R.id.level3) AppCompatImageView level3;
    @BindView(R.id.level4) AppCompatImageView level4;
    @BindView(R.id.level5) AppCompatImageView level5;

    //уровней 6, так как есть нулевой, то есть вообще начинающий
    byte languageLevel;

    /* можно использовать другой тип данных
     byte потому что планируется использовать индекс языка в константах
     */
    byte language;

    LanguageFragment(byte language, byte languageLevel){
        this.language = language;
        this.languageLevel = languageLevel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_language, container, false);
        ButterKnife.bind(this, view1);

        fillLanguageLevel();
        fillLanguage();

        return view1;
    }

    private void fillLanguage() {
        flag.setImageResource(Constants.Languages.flagArray[language]);
    }

    private void fillLanguageLevel() {
        switch (languageLevel){
            case 0:
                level1.setImageResource(R.drawable.language_level_grey1);
                level2.setImageResource(R.drawable.language_level_grey2);
                level3.setImageResource(R.drawable.language_level_grey3);
                level4.setImageResource(R.drawable.language_level_grey4);
                level5.setImageResource(R.drawable.language_level_grey5);
                break;

            case 1:
                level1.setImageResource(R.drawable.language_level_color1);
                level2.setImageResource(R.drawable.language_level_grey2);
                level3.setImageResource(R.drawable.language_level_grey3);
                level4.setImageResource(R.drawable.language_level_grey4);
                level5.setImageResource(R.drawable.language_level_grey5);
                break;

            case 2:
                level1.setImageResource(R.drawable.language_level_color1);
                level2.setImageResource(R.drawable.language_level_color2);
                level3.setImageResource(R.drawable.language_level_grey3);
                level4.setImageResource(R.drawable.language_level_grey4);
                level5.setImageResource(R.drawable.language_level_grey5);
                break;

            case 3:
                level1.setImageResource(R.drawable.language_level_color1);
                level2.setImageResource(R.drawable.language_level_color2);
                level3.setImageResource(R.drawable.language_level_color3);
                level4.setImageResource(R.drawable.language_level_grey4);
                level5.setImageResource(R.drawable.language_level_grey5);
                break;

            case 4:
                level1.setImageResource(R.drawable.language_level_color1);
                level2.setImageResource(R.drawable.language_level_color2);
                level3.setImageResource(R.drawable.language_level_color3);
                level4.setImageResource(R.drawable.language_level_color4);
                level5.setImageResource(R.drawable.language_level_grey5);
                break;

            case 5:
                level1.setImageResource(R.drawable.language_level_color1);
                level2.setImageResource(R.drawable.language_level_color2);
                level3.setImageResource(R.drawable.language_level_color3);
                level4.setImageResource(R.drawable.language_level_color4);
                level5.setImageResource(R.drawable.language_level_color5);
                break;
        }
    }
}
