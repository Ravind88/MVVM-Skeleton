package com.icehousecorp.rogersandroid.view.common;

/**
 * AlertDialogListner is used perform call back action on click of
 * Ok button and cancel
 */
public interface AlertDialogListner {

    /***
     * okDialogButton to perform callback listner
     * @param tag perform action on basis of tag
     */
    public void okDialogButton(String tag);

    /***
     * cancelDialogButton call when click on cancel button
     */
    public void cancelDialogButton();
}
