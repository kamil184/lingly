package com.kamil184.lingly.util;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kamil184.lingly.base.BaseActivity;

public class AnimationsUtil {

    public static void shakeAnimation(View view){
        YoYo.with(Techniques.Shake)
                .duration(200)
                .repeat(1)
                .playOn(view);
    }

}
