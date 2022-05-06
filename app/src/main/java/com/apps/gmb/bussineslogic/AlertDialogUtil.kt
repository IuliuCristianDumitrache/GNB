package com.apps.gmb.bussineslogic

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog

object AlertDialogUtil {
    fun createCustomAlertDialog(context: Context,
                                alertTitle: String, alertMessage: String?,
                                positiveButton: Pair<String, ((DialogInterface, Int) -> Unit)>,
                                negativeButton: Pair<String, ((DialogInterface, Int) -> Unit)>? = null,
                                cancelOnTouchOutside: Boolean = false,
                                customDialogContentView: View? = null): AlertDialog {

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(alertTitle)
        alertDialogBuilder.setMessage(alertMessage)
        if (customDialogContentView != null) {
            alertDialogBuilder.setView(customDialogContentView)
        }
        alertDialogBuilder.setPositiveButton(positiveButton.first, positiveButton.second)
        if (negativeButton != null) {
            alertDialogBuilder.setNegativeButton(negativeButton.first, negativeButton.second)
        }

        val dialog = alertDialogBuilder.create()
        dialog.setCanceledOnTouchOutside(cancelOnTouchOutside)
        return dialog
    }
}