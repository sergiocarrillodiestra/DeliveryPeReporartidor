package com.dscorp.deliverype.presentation.bottomnav.neworders.intent

sealed class NewTransportationIntent {

    object FetchNewTransportations : NewTransportationIntent()

}