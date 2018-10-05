package skeleton.maurya.com.mvvmskeleton.view.common;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import skeleton.maurya.com.mvvmskeleton.R;

/**
 * show progress dialog
 *
 * @version1.0
 */
public class ProgressDialogs {

    /**
     * Creates a dialog with the weak reference of the calling activity
     *
     * @param context return current class context
     * @param message show message on progress
     * @return
     */
    public static Dialog getProgressDialog(Context context, String message) {
        WeakReference<Context> weakref = new WeakReference<>(context);
        final Dialog mProgressDialog = new Dialog(weakref.get(), R.style.stylePTRDialog);
        mProgressDialog.setContentView(R.layout.view_progress_dialog);
        if (!TextUtils.isEmpty(message)) {
            ((TextView) mProgressDialog.findViewById(R.id.progress_dialog_message_tv)).setText(message);
        }
        return mProgressDialog;
    }

    public static Dialog coloredProgress(Context context, int layout) {
        WeakReference<Context> weakref = new WeakReference<>(context);
        final Dialog dialog = new Dialog(weakref.get(), R.style.FadeDialogStyle);
        dialog.setContentView(layout);
        return dialog;
    }
}
