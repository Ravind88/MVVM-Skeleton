package skeleton.maurya.com.mvvmskeleton.view.common;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/**
 * OnFragmentListner is a interface to implement in activity or frgment
 */
public interface OnFragmentListner {

    /**
     * navigate to fragment on behalf of tag
     *
     * @param bundle is used to pass value from one screen to another screen
     * @param tag    is used to navigate on fragment with tag basis
     */
    void showFragment(Bundle bundle, String tag);

    /**
     * navigate to activity on behalf of tag
     *
     * @param bundle is used to pass value from one screen to another screen
     * @param tag    is used to navigate on activity with tag basis
     */
    void showActivity(Bundle bundle, String tag);

    //finish current activity
    void finishActivity();

    //remove fragment
    void popFragment();

    //retrun toolbar
    Toolbar getToolbar();

}
