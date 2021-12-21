package com.dscorp.deliverypedrivers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dscorp.deliverypedrivers.framework.hilt.modules.idlingResource
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewTransportationOrdersFeature : BaseUiTest() {


    @Test
    fun showTransportationdataUI() {
        assertDisplayed(R.id.customer_name)
        assertDisplayed(R.id.delivery_site)
        assertDisplayed(R.id.customer_phones)
        assertDisplayed(R.id.establishment_name)
    }

    @Test
    fun displayListOfTransportationOrders() {
//        assertRecyclerViewItemCount(R.id.orders_list, 3)

        onView(
            allOf(
                withId(R.id.customer_name),
                isDescendantOfA(
                    nthChildOf(withId(R.id.orders_list), 0)
                )
            )
        )
            .check(matches(withText("yordi sta anita")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.customer_phones), isDescendantOfA(
                    nthChildOf(withId(R.id.orders_list), 0)
                )
            )
        )
            .check(matches(withText("+51973168291")))
            .check(matches(isDisplayed()))


        onView(
            allOf(
                withId(R.id.establishment_name),
                isDescendantOfA(
                    nthChildOf(withId(R.id.orders_list), 0)
                )
            )
        )
            .check(matches(withText("d'nelly's")))
            .check(matches(isDisplayed()))


        onView(
            allOf(
                withId(R.id.order_total_price),
                isDescendantOfA(nthChildOf(withId(R.id.orders_list), 0))
            )
        )
            .check(matches(withText("22.50")))
            .check(matches(isDisplayed()))

    }

    @Test
    fun displayNewOrdersNavButton() {
        val textView = onView(
            allOf(
                withId(R.id.navigation_bar_item_large_label_view), withText("Nuevos"),
            )
        )
        textView.check(matches(withText("Nuevos")))
    }

    @Test
    fun displayInProgresOrderssNavButton() {
        val textView = onView(
            allOf(
                withId(R.id.navigation_bar_item_large_label_view), withText("En Progreso"),
            )
        )
        textView.check(matches(withText("En Progreso")))
    }

    @Test
    fun displayOrderHistoryNavButton() {
        val textView = onView(
            allOf(
                withId(R.id.navigation_bar_item_large_label_view), withText("Historial"),
            )
        )
        textView.check(matches(withText("Historial")))
    }


    @Test
    fun displayLoaderWhileFetchingTransportationOrders() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoaderWhenFetchIsDone() {
        assertNotDisplayed(R.id.loader)
    }


}