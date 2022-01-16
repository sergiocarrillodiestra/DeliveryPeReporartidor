package com.dscorp.deliverype.presentation.ui.features.config

import com.dscorp.deliverype.domain.entity.Failure

interface BaseIntentCallback {
    fun onLoading()
    fun onError(error: Failure)
}