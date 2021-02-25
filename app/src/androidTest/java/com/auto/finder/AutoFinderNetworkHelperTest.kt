package com.auto.finder

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.auto.finder.utils.AutoFinderNetworkHelper
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AutoFinderNetworkHelperTest {

    @Test
    fun checkWiFi() {
        val networkHelper = AutoFinderNetworkHelper(getApplicationContext<Context>().applicationContext)
        assertTrue(networkHelper.isNetworkConnected())
    }
}