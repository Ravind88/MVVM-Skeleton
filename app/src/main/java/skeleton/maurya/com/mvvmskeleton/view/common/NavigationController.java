package skeleton.maurya.com.mvvmskeleton.view.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import javax.inject.Inject;

import skeleton.maurya.com.mvvmskeleton.di.AppInjector;

/**
 * Created by jeetendrapal on 11/9/18.
 */
public class NavigationController {
    private Activity activity;

    @Inject
    public NavigationController() {
        this.activity = AppInjector.getActivity();
    }

/*
    //navigating on participant screen
    public void navigateToParticipant() {
        Intent intent = new Intent(activity, ParticipantActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
*/


}
