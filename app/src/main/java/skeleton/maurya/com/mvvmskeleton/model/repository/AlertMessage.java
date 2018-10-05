package skeleton.maurya.com.mvvmskeleton.model.repository;


import android.content.Context;


import skeleton.maurya.com.mvvmskeleton.view.common.DialogActionListner;
import skeleton.maurya.com.mvvmskeleton.view.common.DialogType;


/**
 * Alert message is a builder design pattern based class which is handle
 * whole app alert dialog
 */
public class AlertMessage {

    private final Context context;
    private final String message;
    private String title = "";
    private DialogActionListner listner;
    private final DialogType dialogType;
    private String positiveBtn = "";
    private String negativeBtn = "";
    private String tag = "";

    public String getPositiveBtn() {
        return positiveBtn;
    }

    public void setPositiveBtn(String positiveBtn) {
        this.positiveBtn = positiveBtn;
    }

    public String getNegativeBtn() {
        return negativeBtn;
    }

    public void setNegativeBtn(String negativeBtn) {
        this.negativeBtn = negativeBtn;
    }


    public DialogActionListner getListner() {
        return listner;
    }

    public void setListner(DialogActionListner listner) {
        this.listner = listner;
    }

    private boolean isCancelable = false;

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AlertMessage(AlertBuilder builder) {
        this.context = builder.context;
        this.message = builder.message;
        this.dialogType = builder.dialogType;
    }

    public Context getContext() {
        return context;
    }

    public String getMessage() {
        return message;
    }

    public DialogType getDialogType() {
        return dialogType;
    }


    public static class AlertBuilder {
        private final Context context;
        private final String message;
        private final DialogType dialogType;


        public AlertBuilder(Context context, String message, DialogType dialogType) {
            this.context = context;
            this.message = message;
            this.dialogType = dialogType;
        }

        public AlertMessage build() {
            return new AlertMessage(this);
        }
    }
}
