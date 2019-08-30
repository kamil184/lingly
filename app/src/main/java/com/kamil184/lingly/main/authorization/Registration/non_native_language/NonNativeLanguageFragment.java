package com.kamil184.lingly.main.authorization.Registration.non_native_language;

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

public class NonNativeLanguageFragment extends BaseFragment {

    @BindView(R.id.linear) LinearLayout layout;
    AnimationDrawable anim;
    @BindView(R.id.non_native_language_list)
    ListView nonNativeLanguageList;
    @BindView(R.id.btn_next)
    Button next_btn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    NonNativeLanguagePresenter presenter;

    public interface Callback{
        void toSelectLanguageLevel(Bundle bundle1);
    }

    NonNativeLanguageFragment.Callback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (NonNativeLanguageFragment.Callback) context;
        presenter = new NonNativeLanguagePresenter(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_non_native_language, container, false);

        ButterKnife.bind(this, view1);
        anim = (AnimationDrawable) layout.getBackground();
        anim.setEnterFadeDuration(0);
        anim.setExitFadeDuration(1000);


        presenter.attachView(this);

        setLanguageAdapter();
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
                presenter.addNonNativeLanguage(selectedLanguagesList);
            });
        });

        return view1;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

    private void setLanguageAdapter(){
        String[] languageArray = Constants.Languages.languageArray;
        int[] flagArray = Constants.Languages.flagArray;


        ArrayList<HashMap<String, Object>> data = new ArrayList<>(
                languageArray.length);
        HashMap<String, Object> map;

        for (int i = 0; i < languageArray.length; i++) {
            map = new HashMap<>();
            map.put("LanguageName", languageArray[i]);
            map.put("LanguageFlag", flagArray[i]);
            data.add(map);
        }

        String[] from = {"LanguageName","LanguageFlag"};
        int[] to = {R.id.country_text,R.id.country_image};

        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.set_language_item,
                from, to);

        nonNativeLanguageList.setAdapter(adapter);
    }
}
