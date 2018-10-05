package skeleton.maurya.com.mvvmskeleton.view.common;

/**
 * DialogActionListner.java interface is user implement and pass alert call back listner
 */
public interface DialogActionListner {

    /**
     * alert dialog Ok button listner
     *
     * @param tag A string variable to perform particular action on behalf of tag
     */
    public void dialogOk(String tag);

    /**
     * alert dialog Cancel button listner
     *
     * @param tag A string variable to perform particular action on behalf of tag
     */
    public void dialogCancel(String tag);
}
