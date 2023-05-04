package com.example.dictionary.customView.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.example.dictionary.R
import com.example.dictionary.utils.convertDpToPixel

object DialogUtils {

    fun showAlertDialog(
        context: Context,
        title: String? = null,
        message: String? = null,
        positiveText: String? = null,
        negativeText: String? = null,
        isCancelAble: Boolean = false,
        onNegativeClick: (() -> Unit)? = null,
        onPositiveClick: (() -> Unit)? = null,
        onMessageClick: (() -> Unit)? = null,
    ): AlertDialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_base, null)
        val builder = AlertDialog.Builder(context).setView(dialogView)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, convertDpToPixel(10f, context).toInt())
        val alertDialog = builder.show()

        alertDialog.run {
            window?.setBackgroundDrawable(inset)

            val tvTitle = dialogView.findViewById<TextView>(R.id.tv_title_base)
            val tvMessage = dialogView.findViewById<TextView>(R.id.tv_message_base)
            val btnPositive = dialogView.findViewById<Button>(R.id.btn_positive)
            val btnNegative = dialogView.findViewById<Button>(R.id.btn_negative)

            message?.let {
                tvMessage.text =
                    HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }

            if (title == null) {
                tvTitle.visibility = View.GONE
            } else {
                tvTitle.visibility = View.VISIBLE
                tvTitle.text = title
            }

            if (positiveText == null) {
                //default positive text
                btnPositive.text = context.getString(R.string.confirm)
            } else {
                btnPositive.text = positiveText
            }

            if (negativeText == null) {
                btnNegative.visibility = View.GONE
            } else {
                btnNegative.text = negativeText
            }

            setCancelable(isCancelAble)
            setOnDismissListener { onNegativeClick?.invoke() }

            btnPositive.setOnClickListener {
                dismiss()
                onPositiveClick?.invoke()
            }

            btnNegative.setOnClickListener {
                dismiss()
                onNegativeClick?.invoke()
            }

            tvMessage.setOnClickListener {
                dismiss()
                onMessageClick?.invoke()
            }
        }

        return alertDialog
    }
}