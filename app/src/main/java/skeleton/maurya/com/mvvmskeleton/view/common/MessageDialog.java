package skeleton.maurya.com.mvvmskeleton.view.common;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;


import javax.inject.Inject;

import skeleton.maurya.com.mvvmskeleton.R;
import skeleton.maurya.com.mvvmskeleton.di.AppInjector;
import skeleton.maurya.com.mvvmskeleton.model.repository.AlertMessage;


/**
 * MessageDialog.java class is common alert dialog class for whole app
 * using builder design patter
 */
public class MessageDialog {

    Context context;

    @Inject
    MessageDialog(Application context) {
        this.context = context.getApplicationContext();
    }

    /**
     * createMessageAlert dialog
     *
     * @param title   show on alert dialog
     * @param messgae show on alert dialog
     * @param btnOk   show btn name
     */
    public void createMessageAlert(String title, String messgae, String btnOk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppInjector.getActivity(), R.style.AppCompatAlertDialog);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(messgae);
        builder.setPositiveButton(btnOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialogs = builder.create();
        dialogs.show();

    }


    /**
     * createMessageAlert dialog
     *
     * @param title      return dialog title string value
     * @param messgae    return dialog messge
     * @param btnOk      return button name
     * @param btnListner button click event listner
     */
    public void createMessageAlert(String title, String messgae, String btnOk,
                                   DialogInterface.OnClickListener btnListner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppInjector.getActivity(), R.style.AppCompatAlertDialog);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(messgae);
        builder.setPositiveButton(btnOk, btnListner);
        AlertDialog dialogs = builder.create();
        dialogs.show();

    }


    /**
     * simpleMsg is a alert dialog with single button
     *
     * @param msg is a string message to show on alert dialog
     */
    public void simpleMsg(String msg) {
        AlertDialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(AppInjector.getActivity(), R.style.AppCompatAlertDialog);
        builder.setCancelable(false);
        builder.setTitle(AppInjector.getActivity().getString(R.string.dialog_title_message));
        builder.setMessage(msg);
        builder.setPositiveButton(AppInjector.getActivity().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();

    }


    /**
     * createAlert dialog on behalf of dialogtype
     *
     * @param alertMessage retrun alert dialog
     */
    public void creatAlert(AlertMessage alertMessage) {
        context = alertMessage.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialog);
        builder.setCancelable(alertMessage.isCancelable());
        builder.setTitle(alertMessage.getTitle().isEmpty() ? alertMessage.getContext().getResources().getString(R.string.app_name) : alertMessage.getTitle());
        builder.setMessage(alertMessage.getMessage());

        switch (alertMessage.getDialogType()) {
            case DIALOG_OK:
                createOkAction(builder, alertMessage);
                break;
            case DIALOG_OK_CANCEL:
                createOkAction(builder, alertMessage);
                createCancelAction(builder, alertMessage);
                break;
            default:
                break;

        }
        builder.create();
        builder.show();
    }

    /**
     * createCancelAction is called on click cancel button
     *
     * @param builder      to set click event
     * @param alertMessage is custom class for laert dialog using builder design patter
     */
    private void createCancelAction(AlertDialog.Builder builder, final AlertMessage alertMessage) {
        builder.setNegativeButton(alertMessage.getNegativeBtn().isEmpty() ? context.getResources().getString(R.string.cancel) : alertMessage.getNegativeBtn(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (alertMessage.getListner() != null)
                    alertMessage.getListner().dialogCancel(alertMessage.getTag());
            }
        });
    }

    /**
     * createOkAction is called on click ok button
     *
     * @param builder      to set click event
     * @param alertMessage is custom class for laert dialog using builder design patter
     */
    private void createOkAction(AlertDialog.Builder builder,final AlertMessage alertMessage) {
        builder.setPositiveButton(alertMessage.getPositiveBtn().isEmpty() ? context.getResources().getString(R.string.btn_ok) : alertMessage.getPositiveBtn(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (alertMessage.getListner() != null)
                    alertMessage.getListner().dialogOk(alertMessage.getTag());
            }
        });
    }

}
