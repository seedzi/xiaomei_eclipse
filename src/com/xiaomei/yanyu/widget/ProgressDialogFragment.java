package com.xiaomei.yanyu.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

public class ProgressDialogFragment extends DialogFragment {

    public static final String TAG = ProgressDialogFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        Bundle arguments = getArguments();
        if (arguments != null) {
            dialog.setTitle(arguments.getString(Intent.EXTRA_TITLE));
        }
        return dialog;
    }

}
