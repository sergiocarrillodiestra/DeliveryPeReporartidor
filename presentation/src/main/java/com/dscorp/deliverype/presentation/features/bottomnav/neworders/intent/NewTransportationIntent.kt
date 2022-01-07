package com.dscorp.deliverype.presentation.features.bottomnav.neworders.intent

sealed class NewTransportationIntent {

    object FetchNewTransportations : NewTransportationIntent()

}