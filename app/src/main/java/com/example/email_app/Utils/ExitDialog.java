package com.example.email_app.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

public class ExitDialog {

    private Context context;

    public ExitDialog(Context context) {
        this.context = context;
    }

    public void showExitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you wish to exit?")
                .setCancelable(true)
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    ((Activity)context).finishAffinity();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel()).create()
                .show();
    }
}
