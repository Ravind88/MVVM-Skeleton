package skeleton.maurya.com.mvvmskeleton;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import skeleton.maurya.com.mvvmskeleton.di.Injectable;
import skeleton.maurya.com.mvvmskeleton.model.repository.Pojo.AlertMessage;
import skeleton.maurya.com.mvvmskeleton.view.common.AlertDialogListner;
import skeleton.maurya.com.mvvmskeleton.view.common.BaseInterface;
import skeleton.maurya.com.mvvmskeleton.view.common.DialogActionListner;
import skeleton.maurya.com.mvvmskeleton.view.common.DialogType;
import skeleton.maurya.com.mvvmskeleton.view.common.MessageDialog;
import skeleton.maurya.com.mvvmskeleton.view.common.OnFragmentListner;
import skeleton.maurya.com.mvvmskeleton.view.common.ProgressDialogs;
//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * BaseActivity.java class is a parent for all activity in this class defind common
 * funtion which need to override in activity
 * {@link AppCompatActivity}
 * {@link Injectable}
 * {@link LifecycleOwner}
 * {@link BaseInterface}
 * {@link OnFragmentListner}
 */
public abstract class BaseActivity extends AppCompatActivity implements Injectable,BaseInterface, LifecycleOwner, OnFragmentListner, DialogActionListner {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private Dialog mProgressDialog;
    private boolean isRunning = true;
    private AlertDialogListner dialogListner;

    @Inject
    MessageDialog messageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        int layoutResId = getLayout();
        ViewDataBinding binding = null;
        if (layoutResId != 0) {
            try {
                setContentView(layoutResId);
                binding = DataBindingUtil.setContentView(this, layoutResId);
                initUI(binding);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error <> ", Log.getStackTraceString(e));
            }
        }


    }


    @Override
    protected void onStart() {
        isRunning = true;
        super.onStart();
    }

    @Override
    protected void onStop() {
        isRunning = false;
        super.onStop();
    }

    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }


    /**
     * Shows a progress dialog whenever and wherever required.
     *
     * @param show    - true/false to show/hide the progress dialog
     * @param message - message to be displayed on progress. Default is Please wait..
     */
    public void showProgress(boolean show, String message) {

        if (isRunning) {
            if (show) {
                if (mProgressDialog == null) {
                    mProgressDialog = ProgressDialogs.getProgressDialog(this, message);
                }
                mProgressDialog.show();

            } else {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        }
    }


//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//
//    }

    /**
     * showFragment on behlaf of tag
     *
     * @param bundle is used to pass value from one screen to another screen
     * @param tag    is used to navigate on fragment with tag basis
     */
    @Override
    public void showFragment(Bundle bundle, String tag) {

    }

    /**
     * showActivity is a method to navigate particular activity
     *
     * @param bundle navigate other activity with data inside bundle
     * @param tag    navigate activity screen on tag basis
     */
    @Override
    public void showActivity(Bundle bundle, String tag) {

    }

    @Override
    public void popFragment() {
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       /* Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }*/
    }

    /**
     * show alert message
     *
     * @param title          show title on alert dialog
     * @param msg            show on alert dialog
     * @param dialogOkCancel show dialog with both Ok and Cancel
     */
    public void showConnectionDialog(String tag, String title, String msg, DialogType dialogOkCancel, AlertDialogListner listner) {
        this.dialogListner = listner;
        AlertMessage alertMessageConfig = new AlertMessage(new AlertMessage.AlertBuilder(this, msg, dialogOkCancel));
        alertMessageConfig.setTitle(title);
        alertMessageConfig.setTag(tag);
        alertMessageConfig.setPositiveBtn(getString(R.string.btn_ok));
        alertMessageConfig.setNegativeBtn(getString(R.string.cancel));
        alertMessageConfig.setListner(this);
        messageDialog.creatAlert(alertMessageConfig);
    }


    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initUI(ViewDataBinding binding) {

    }

    @Override
    public void dialogOk(String tag) {

    }

    @Override
    public void dialogCancel(String tag) {

    }
}
