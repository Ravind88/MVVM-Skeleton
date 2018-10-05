package com.icehousecorp.rogersandroid.view.common;

import android.databinding.ViewDataBinding;

/**
 *  */

public interface BaseInterface {

    public int getLayout();

    public void initUI(ViewDataBinding binding);

    interface OKClickEventInterface {
        public void OnClickOKButton();
    }
}
