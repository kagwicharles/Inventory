package com.kagwisoftwares.inventory.utils;

import android.app.Activity;
import android.os.Build;
import android.transition.Fade;
import android.view.Window;

public class MyTransitions {

    public MyTransitions() {}

    public void animateFade(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Fade fade = new Fade();
            fade.setDuration(600);
            activity.getWindow().setExitTransition(fade);
            activity.getWindow().setEnterTransition(fade);
        }
    }
}
