package com.demirkiran.baseApp.util

import android.app.AlertDialog
import android.content.Context

class MainHelper {
    companion object {
        fun showAlertDialog(context: Context, message: String) {
            val alertDialog = AlertDialog.Builder(context)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Tamam") { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    }.show()
        }
    }
}