package com.kamil184.lingly.main.Fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kamil184.lingly.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

   @BindView(R.id.container1) ScrollView container1;
   @BindView(R.id.user_avatar) ImageView avatar;

   AnimationDrawable anim;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view1);


        anim = (AnimationDrawable) container1.getBackground();
        anim.setEnterFadeDuration(0);
        anim.setExitFadeDuration(1000);


        StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/lingly-app.appspot.com/o/users%2Ftest%40gmail.com%2Favatar%2FCapture001.png?alt=media&token=c2e438e7-d1c6-48bc-8313-27640fe74288");

        Glide.with(this )
                .load("https://firebasestorage.googleapis.com/v0/b/lingly-app.appspot.com/o/users%2Ftest%40gmail.com%2Favatar%2FCapture001.png?alt=media&token=c2e438e7-d1c6-48bc-8313-27640fe74288")
                .into(avatar);


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
