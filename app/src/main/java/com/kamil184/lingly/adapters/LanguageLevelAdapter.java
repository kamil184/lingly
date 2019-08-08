package com.kamil184.lingly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.kamil184.lingly.Constants;
import com.kamil184.lingly.R;
import com.kamil184.lingly.models.LanguageLevelModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageLevelAdapter extends BaseAdapter {
    private Context mContext;

    @BindView(R.id.country_image) AppCompatImageView flag;
    @BindView(R.id.level1) AppCompatImageView level1;
    @BindView(R.id.level2) AppCompatImageView level2;
    @BindView(R.id.level3) AppCompatImageView level3;
    @BindView(R.id.level4) AppCompatImageView level4;
    @BindView(R.id.level5) AppCompatImageView level5;

    ArrayList<LanguageLevelModel> arrayList;

    public LanguageLevelAdapter(Context mContext, ArrayList<LanguageLevelModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view1;

        LanguageLevelModel model = (LanguageLevelModel) getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view1 = inflater.inflate(R.layout.fragment_language, parent, false);
        } else {
            view1 = convertView;
        }

        ButterKnife.bind(this, view1);

        setLanguageLevel(model.getLanguageLevel());
        setLanguage(model.getLanguage());

        return view1;
    }

    private void setLanguage(byte language) {
        flag.setImageResource(Constants.Languages.flagArray[language]);
    }

    private void setLanguageLevel(byte languageLevel) {
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
