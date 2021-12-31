package com.dscorp.deliverypedrivers

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dscorp.deliverypedrivers.framework.hilt.modules.idlingResource
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HistoryTransportationOrderFeature : BaseUiTest() {

    @Test
    fun showTransportationDataUi() {
        navigateToHistoryTransportOrders()
        BaristaVisibilityAssertions.assertDisplayed(R.id.customer_name_history_transportation)
        BaristaVisibilityAssertions.assertDisplayed(R.id.customer_phones_site_history_transportation)
        BaristaVisibilityAssertions.assertDisplayed(R.id.customer_delivery_site_history_transportation)
        BaristaVisibilityAssertions.assertDisplayed(R.id.establishment_name_site_history_transportation)
    }

    @Test
    fun displayListOfHistoryTransportationOrders() {
        navigateToHistoryTransportOrders()
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.customer_name_history_transportation),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.orders_list_history),
                        0
                    )
                )
            )
        ).check(matches(ViewMatchers.withText("yordi sta anita")))
            .check(matches(ViewMatchers.isDisplayed()))


        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.customer_phones_site_history_transportation),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(ViewMatchers.withId(R.id.orders_list_history), 0)
                )
            )
        )
            .check(matches(ViewMatchers.withText("+51973168291")))
            .check(matches(ViewMatchers.isDisplayed()))


        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.establishment_name_site_history_transportation),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(ViewMatchers.withId(R.id.orders_list_history), 0)
                )
            )
        )
            .check(matches(ViewMatchers.withText("d'nelly's")))
            .check(matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.order_total_price_history_transportation),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(ViewMatchers.withId(R.id.orders_list_history), 0)
                )
            )
        )
            .check(matches(ViewMatchers.withText("22.50")))
            .check(matches(ViewMatchers.isDisplayed()))


    }


    private fun navigateToHistoryTransportOrders() {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.historytransportationFragment),
                ViewMatchers.withContentDescription("Historial"),
                ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(R.id.nav_view))),
            )
        ).perform(ViewActions.click())
    }

    @Test
    fun displayLoaderWhileFetchingTransportationOrders() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(2000)
        navigateToHistoryTransportOrders()
        BaristaVisibilityAssertions.assertDisplayed(R.id.loader_history)
    }

    @Test
    fun hideLoaderWhenFetchIsDone() {
        navigateToHistoryTransportOrders()
        BaristaVisibilityAssertions.assertNotDisplayed(R.id.loader_history)
    }
}