package com.toldas.sampleapplication.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.toldas.sampleapplication.R
import com.toldas.sampleapplication.di.Injectable

abstract class BaseDialogFragment : DialogFragment(), Injectable {

    override fun onStart() {
        super.onStart()

        val window = dialog.window
        val windowParams = window.attributes
        windowParams.dimAmount = 0.0f

        window.attributes = windowParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        dialog.window.statusBarColor = ContextCompat.getColor(context!!, R.color.navy)
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme)
        setUp()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window
                .attributes.windowAnimations = R.style.DialogAnimation
    }

    abstract fun setUp()
}