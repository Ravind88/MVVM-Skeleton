package skeleton.maurya.com.mvvmskeleton;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icehousecorp.rogersandroid.view.common.AlertDialogListner;
import com.icehousecorp.rogersandroid.view.common.OnFragmentListner;

import javax.inject.Inject;

import skeleton.maurya.com.mvvmskeleton.di.Injectable;
import skeleton.maurya.com.mvvmskeleton.model.repository.AlertMessage;
import skeleton.maurya.com.mvvmskeleton.view.common.DialogActionListner;
import skeleton.maurya.com.mvvmskeleton.view.common.DialogType;
import skeleton.maurya.com.mvvmskeleton.view.common.MessageDialog;

/**
 * base fragment for other fragment
 *
 * @version1.0
 */
public abstract class BaseFragment extends Fragment implements Injectable, DialogActionListner {
    //    @Inject
//    protected AppViewModelFactory viewModelFactory;
    private ViewDataBinding binding;
    protected OnFragmentListner fragmentListner;
    //    private Dialog mProgressDialog;
    private ProgressDialog mProgressDialog;
    public Activity activity;
    private AlertDialogListner dialogListner;

    @Inject
    MessageDialog messageDialog;

    protected abstract int getLayoutId();

    protected abstract void onViewsInitialized(ViewDataBinding binding, View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onViewsInitialized(binding, view);
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            activity = (Activity) context;

        if (context instanceof OnFragmentListner)
            fragmentListner = (OnFragmentListner) context;

    }


    /**
     * Shows a progress dialog whenever and wherever required.
     *
     * @param show    - true/false to show/hide the progress dialog
     * @param message - message to be displayed on progress. Default is Please wait..
     */
    public void showProgress(boolean show, String message) {
        if (show) {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage(message);
                mProgressDialog.setCancelable(false);
//                mProgressDialog = ProgressDialogs.getProgressDialog(getActivity(), message);
            }
            mProgressDialog.show();

        } else {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
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
        AlertMessage alertMessageConfig = new AlertMessage(new AlertMessage.AlertBuilder(getActivity(), msg, dialogOkCancel));
        alertMessageConfig.setTitle(title);
        alertMessageConfig.setTag(tag);
        alertMessageConfig.setPositiveBtn(getString(R.string.btn_ok));
        alertMessageConfig.setNegativeBtn(getString(R.string.cancel));
        messageDialog.creatAlert(alertMessageConfig);
    }

    @Override
    public void dialogOk(String tag) {
        if (dialogListner != null)
            dialogListner.okDialogButton(tag);
    }

    @Override
    public void dialogCancel(String tag) {
        if (dialogListner != null)
            dialogListner.cancelDialogButton();
    }

}
