package com.dscorp.deliverypedrivers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dscorp.deliverypedrivers.framework.hilt.modules.idlingResource
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TakedTransportationOrdersFeature : BaseUiTest() {


    @Test
    fun showTransportationdataUI() {
        navigateToTakedTransportationOrders()
        assertDisplayed(R.id.customer_name_taked_transportations)
        assertDisplayed(R.id.delivery_site_taked_transportations)
        assertDisplayed(R.id.customer_phones_taked_transportations)
        assertDisplayed(R.id.establishment_name_taked_transportations)
    }

    @Test
    fun showTakedTransportationOrderListByDriver() {

        navigateToTakedTransportationOrders()

        onView(
            allOf(
                withId(R.id.customer_name_taked_transportations),
                isDescendantOfA(nthChildOf(withId(R.id.orders_list_taked_transportations), 0))
            )
        )
            .check(matches(withText("yordi sta anita")))
            .check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.customer_phones_taked_transportations), isDescendantOfA(
                    nthChildOf(withId(R.id.orders_list_taked_transportations), 0)
                )
            )
        )
            .check(matches(withText("+51973168291")))
            .check(matches(isDisplayed()))


        onView(
            allOf(
                withId(R.id.establishment_name_taked_transportations),
                isDescendantOfA(
                    nthChildOf(withId(R.id.orders_list_taked_transportations), 0)
                )
            )
        )
            .check(matches(withText("d'nelly's")))
            .check(matches(isDisplayed()))


        onView(
            allOf(
                withId(R.id.order_total_price_taked_transportations),
                isDescendantOfA(nthChildOf(withId(R.id.orders_list_taked_transportations), 0))
            )
        )
            .check(matches(withText("22.50")))
            .check(matches(isDisplayed()))
    }

    private fun navigateToTakedTransportationOrders() {

        onView(
            CoreMatchers.allOf(
                withId(R.id.customer_name),
                isDescendantOfA(nthChildOf(withId(R.id.orders_list), 0))
            )
        ).perform(ViewActions.click())
    }

    @Test
    fun displayLoaderWhileFetchingTransportationOrders() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(1000)
        navigateToTakedTransportationOrders()
        assertDisplayed(R.id.loader_taked_transportations)
    }

    @Test
    fun hideLoaderWhenFetchIsDone() {
        navigateToTakedTransportationOrders()
        assertNotDisplayed(R.id.loader_taked_transportations)
    }

}