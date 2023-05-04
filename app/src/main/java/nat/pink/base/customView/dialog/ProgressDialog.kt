package com.example.dictionary.customView.dialog

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import com.example.dictionary.R

class ProgressDialog @JvmOverloads constructor(
    var context: Context,
    var isCancelable: Boolean = false
) {

    lateinit var progressDialog: AlertDialog
    private lateinit var imgOutSideLoading : ImageView

    private var isShowing: Boolean = false

    init {
        setupDialog()
    }

    /**
     * Setup progress dialog
     * */
    private fun setupDialog() {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_progress_sample, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        progressDialog = mBuilder.show()
        progressDialog.setCancelable(isCancelable)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//        imgOutSideLoading = progressDialog.findViewById(R.id.iv_outside_loading)
    }


    /**
     * Show progress dialog
     * */
    fun show(){
        progressDialog.show()
//        progressAnimation()
        isShowing = true
    }

    /**
    * Hide progress dialog
    * */
    fun hide(){
//        imgOutSideLoading.clearAnimation()
        isShowing = false
        progressDialog.dismiss()
    }

    /**
    * Set up cancelable
    * */
    fun setupCancelable(cancelable: Boolean) {
        isCancelable = cancelable
        progressDialog.setCancelable(cancelable)
    }

    /**
    * dialog run animation
    * */
    private fun progressAnimation() {
        val scale = ScaleAnimation(
            1f,
            0.9f,
            1f,
            0.9f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        scale.duration = 1000
        scale.repeatCount = -1
        scale.repeatMode = ValueAnimator.REVERSE
        imgOutSideLoading.startAnimation(scale)
    }
}