package com.kamil184.lingly.main.authorization.Registration.language_level;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectLanguageLevelFragment extends BaseFragment {

    @BindView(R.id.linear) LinearLayout layout;


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


        presenter.attachView(this);
        return view1;
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
