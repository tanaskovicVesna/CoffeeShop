package com.example.android.gridview.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.example.android.gridview.R;

/**
 * Created by Tanaskovic on 2/4/2018.
 */

public class AboutDialog extends AlertDialog.Builder {

    public AboutDialog(@NonNull Context context) {
        super(context);
        setTitle(R.string.dialog_about_app);
        setMessage(R.string.message);
        setCancelable(false);

        setPositiveButton(R.string.dialog_about_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
    }

    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
