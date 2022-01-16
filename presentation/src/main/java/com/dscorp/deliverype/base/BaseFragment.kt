package com.dscorp.deliverype.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.dscorp.deliverype.R
import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.ui.dialogs.ConfirmationDialog
import com.dscorp.deliverype.ui.dialogs.LoadingDialog
import com.dscorp.deliverype.ui.dialogs.MessageDialog
import com.dscorp.deliverype.ui.dialogs.MessageDialogType
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VBinding : ViewBinding> : Fragment() {
    protected lateinit var binding: VBinding

    protected lateinit var navController: NavController

    protected abstract fun getViewBinding(): VBinding

    private var loadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = findNavController()

        return binding.root
    }

    private fun init() {
        binding = getViewBinding()
    }

    protected fun showLoadingDialog(loadingText: String? = null) {
        removeLoadingDialogFromBackStack()
        if (activity != null) {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(loadingText)
            }
            if (!loadingDialog!!.isAdded) {
                loadingDialog?.loadingText = loadingText
                loadingDialog?.show(childFragmentManager, "loadingDialog")
            } else if (loadingDialog!!.isVisible) {
                loadingDialog?.updateLoadingText(loadingText)
            }
        }
    }

    protected fun closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog?.dismiss()
        }
    }

    private fun removeLoadingDialogFromBackStack() {
        if (activity != null) {
            val oldLoadingDialog = childFragmentManager.findFragmentByTag("loadingDialog")
            if (oldLoadingDialog != null) {
                if (!oldLoadingDialog.isVisible) {
                    childFragmentManager.beginTransaction()
                        .remove(oldLoadingDialog)
                        .addToBackStack(null)
                }
            }
        }
    }

    //region SnackBar
    protected fun showSnackBar(
        rootView: View,
        contentText: Any,
        duration: Int = Snackbar.LENGTH_LONG
    ) {
        val text = when (contentText) {
            is String -> contentText
            is Int -> getString(contentText)
            else -> ""
        }
        Snackbar.make(rootView, text, duration).show()
    }

    protected fun showSnackBar(message: String) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }


    protected fun showToast(message: Any?) {
        val textToShow = when (message) {
            is Int -> getString(message)
            is String -> message
            else -> {
                ""
            }
        }
        Toast.makeText(requireContext(), textToShow, Toast.LENGTH_LONG).show()
    }

    protected fun hideKeyboard() {
        try {

            val imm =
                view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)

        } catch (e: Exception) {
            showToast(e.message)
        }
    }

    protected fun logError(message: Any?) {
        Log.e(this.javaClass.simpleName, "LOG ERROR: $message")
    }

    protected fun logDebug(message: Any?) {
        Log.d(this.javaClass.simpleName, "LOG DEBUG: $message")
    }

    protected fun handleUseCaseFailureFromBase(failure: Failure): String {
        logError("handleUseCaseFailureFromBase: $failure")
        return when (failure) {
            is Failure.UnauthorizedOrForbidden -> {
//                forceLogout()
                return getString(R.string.UnauthorizedOrForbidden)
            }
            is Failure.None -> getString(R.string.error_failure_none)
            is Failure.NetworkConnectionLostSuddenly -> getString(R.string.error_failure_network_connection_lost_suddenly)
            is Failure.NoNetworkDetected -> getString(R.string.error_failure_no_network_detected)
            is Failure.SSLError -> getString(R.string.error_failure_ssl)
            is Failure.TimeOut -> getString(R.string.error_failure_time_out)
            is Failure.ServerBodyError -> failure.message
            is Failure.DataToDomainMapperFailure -> getString(R.string.error_general)
            is Failure.ServiceUncaughtFailure -> failure.uncaughtFailureMessage
        }
    }

    protected fun showMessageDialog(
        dialogType: MessageDialogType,
        message: String,
        callback: () -> Unit
    ) {
        MessageDialog(dialogType, message, callback).show(parentFragmentManager, "MessageDialog")
    }

    protected fun showConfirmDialog(message:String, callback:()->Unit)
    {
        ConfirmationDialog(message, callback).show(parentFragmentManager, "ConfirmationDialog")
    }

    //force Logout due to 401 or 403
//    protected fun forceLogout(it: Boolean) {
//        if(it){
//            showToast(R.string.log_out_by_other_session)
//            NotificationUtil.dismissNotification(requireContext())
//            preferences.deleteAccessToken()
//            preferences.deleteDriverModel()
//            activity?.startNewActivityClearStack<SplashActivity>()
//        }
//    }

}