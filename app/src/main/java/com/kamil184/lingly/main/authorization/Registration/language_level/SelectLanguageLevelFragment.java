package com.kamil184.lingly.main.authorization.Registration.language_level;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kamil184.lingly.Constants;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectLanguageLevelFragment extends BaseFragment {

    @BindView(R.id.linear) LinearLayout layout;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.non_native_language_list) ListView nonNativeLanguageList;
    @BindView(R.id.btn_next) Button next_btn;


    AnimationDrawable anim;
    SelectLanguageLevelPresenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new SelectLanguageLevelPresenter(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_set_language_level, container, false);

        ButterKnife.bind(this, view1);
        anim = (AnimationDrawable) layout.getBackground();
        anim.setEnterFadeDuration(0);
        anim.setExitFadeDuration(1000);

        ArrayList<Integer> ar;
        ar = getArguments().getIntegerArrayList("nonNativeLanguages");
        setLanguageAdapter(ar);


        presenter.attachView(this);
        return view1;
    }

    private void setLanguageAdapter(ArrayList<Integer> ar){
        String[] languageArray = Constants.Languages.languageArray;
        int[] flagArray = Constants.Languages.flagArray;


        ArrayList<HashMap<String, Object>> data = new ArrayList<>(
                languageArray.length);
        HashMap<String, Object> map;

        for (int i = 0; i < languageArray.length; i++) {
            map = new HashMap<>();
            if(ar.contains(i)){
            map.put("LanguageName", languageArray[i]);
            map.put("LanguageFlag", flagArray[i]);
            data.add(map);}
        }

        String[] from = {"LanguageName","LanguageFlag"};
        int[] to = {R.id.country_text,R.id.country_image};

        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.set_language_item,
                from, to);

        nonNativeLanguageList.setAdapter(adapter);
        ArrayList<Integer> selectedLanguagesList = new ArrayList<>();
        nonNativeLanguageList.setOnItemClickListener((adapterView, itemClicked, position, id) -> {
            if(selectedLanguagesList.contains(position)){
                selectedLanguagesList.remove(selectedLanguagesList.indexOf(position));
                itemClicked.setAlpha((float) 1);
            }else {
                itemClicked.setAlpha((float) 0.5);
                selectedLanguagesList.add(position);
            }
            if(selectedLanguagesList.size() == 0) {
                next_btn.setVisibility(View.GONE);
            } else next_btn.setVisibility(View.VISIBLE);

            next_btn.setOnClickListener(view -> {
                next_btn.setClickable(false);

            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
