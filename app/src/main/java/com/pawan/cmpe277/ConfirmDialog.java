package com.pawan.cmpe277;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;


public class ConfirmDialog {

    public static void show(Context context, @StringRes int titleId, @StringRes int messageId,
                            @StringRes int buttonId, DialogInterface.OnClickListener listener) {
        Dialog dialog = new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setMessage(messageId)
                .setNeutralButton(buttonId, listener)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
