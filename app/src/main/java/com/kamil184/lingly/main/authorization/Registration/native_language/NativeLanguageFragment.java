package com.kamil184.lingly.main.authorization.Registration.native_language;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NativeLanguageFragment extends BaseFragment {

    @BindView(R.id.linear) LinearLayout layout;
    AnimationDrawable anim;
    @BindView(R.id.native_language_list)
    ListView native_language_list;

    NativeLanguagePresenter presenter;

    public interface Callback{
        void toNonNativeLanguage();
    }

    NativeLanguageFragment.Callback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (NativeLanguageFragment.Callback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_native_language, container, false);

        ButterKnife.bind(this, view1);
        anim = (AnimationDrawable) layout.getBackground();
        anim.setEnterFadeDuration(0);
        anim.setExitFadeDuration(1000);
        presenter = new NativeLanguagePresenter(getActivity());
        presenter.attachView(this);

        setLanguageAdapter();

        return view1;
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

    private void setLanguageAdapter(){
    String[] languageArray = presenter.languageArray;
    int[] flagArray = presenter.flagArray;
    //TODO добавить иконки флагов

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

    SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.fragment_set_language_item,
                from, to);

    native_language_list.setAdapter(adapter);
    }
}