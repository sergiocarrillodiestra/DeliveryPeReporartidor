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
import com.dscorp.deliverype.databinding.DialogFragmentMessageBinding

class MessageDialog(
    private val messageDialogType: MessageDialogType,
    private val message: String?,
    private val onButtonClick: () -> Unit
) :
    DialogFragment() {

    private lateinit var binding: DialogFragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentMessageBinding.inflate(LayoutInflater.from(context))

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            when (messageDialogType) {
                MessageDialogType.Info -> iconIv.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_info,
                        null
                    )
                )
                MessageDialogType.Success -> iconIv.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_check,
                        null
                    )
                )
                MessageDialogType.Error -> iconIv.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_cancel,
                        null
                    )
                )
            }


            message?.let {
                messageTv.text = it
            }
            okDlgBtn.setOnClickListener {
                dismiss()
                onButtonClick.invoke()
            }
        }

    }

}


sealed class MessageDialogType {
    object Success : MessageDialogType()
    object Info : MessageDialogType()
    object Error : MessageDialogType()
}