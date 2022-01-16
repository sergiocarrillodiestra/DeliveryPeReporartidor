package com.dscorp.deliverype.ui.dialogs

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.dscorp.deliverype.R
import com.dscorp.deliverype.databinding.DialogFragmentLoadingBinding

class LoadingDialog(var loadingText: String? = null) : DialogFragment() {

    lateinit var binding: DialogFragmentLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentLoadingBinding.inflate(LayoutInflater.from(context))

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        binding.root.background = context?.getDrawable(R.drawable.dialog_background_rounded)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvProgressText.text = loadingText ?: getString(R.string.label_loading)
    }

    fun updateLoadingText(someText: String?) {
        if (someText != null) {
            binding.tvProgressText.text = someText
        }
    }
}