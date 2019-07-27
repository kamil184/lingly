package com.kamil184.lingly.main.authorization.Registration.non_native_language;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseFragment;
import com.kamil184.lingly.main.authorization.Registration.native_language.NativeLanguageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NonNativeLanguageFragment extends BaseFragment {

    @BindView(R.id.linear) LinearLayout layout;
    AnimationDrawable anim;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_non_native_language, container, false);

        ButterKnife.bind(this, view1);
        anim = (AnimationDrawable) layout.getBackground();
        anim.setEnterFadeDuration(0);
        anim.setExitFadeDuration(1000);

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
}
