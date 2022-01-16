package com.dscorp.deliverype.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.dscorp.deliverype.R
import com.dscorp.deliverype.databinding.DialogFragmentConfirmationBinding

class ConfirmationDialog(
    private val message: String?,
    private val onButtonClick: () -> Unit
) :
    DialogFragment() {

    private lateinit var binding: DialogFragmentConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentConfirmationBinding.inflate(LayoutInflater.from(context))
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        binding.root.background= context?.getDrawable(R.drawable.dialog_background_rounded)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            message?.let {
                tvMessage.text = it
            }
            btnOkDlg.setOnClickListener {
                dismiss()
                onButtonClick.invoke()
            }

            btnCancelDlg.setOnClickListener {
                dismiss()
            }

        }

    }

}

