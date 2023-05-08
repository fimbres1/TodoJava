package com.example.todojava;

import android.content.DialogInterface;
import android.view.View;

public interface OnDialogCloseListner {

    void onClick(View v);

    void onDialogClose(DialogInterface dialogInterface);
}
