package com.auto.finder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.auto.finder.datasource.StubDataSource
import com.auto.finder.ui.build.dates.BuildDatesViewModel
import com.auto.finder.ui.main.AutoListViewModel
import com.auto.finder.ui.types.MainTypesViewModel
import com.auto.finder.utils.AutoFinderNetworkHelper

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Test cases for all view model in a single file.
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class CommonViewModelTest {

    private lateinit var autoListViewModel: AutoListViewModel

    private lateinit var mainTypesViewModel: MainTypesViewModel

    private lateinit var buildDatesViewModel: BuildDatesViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var networkHelper: AutoFinderNetworkHelper

    @Before
    fun setup() {
        autoListViewModel = AutoListViewModel(StubDataSource(), networkHelper)
        mainTypesViewModel = MainTypesViewModel(StubDataSource(), networkHelper)
        buildDatesViewModel = BuildDatesViewModel(StubDataSource(), networkHelper)
    }

    @Test
    fun testManufacturerList() {
        testCoroutineRule.runBlockingTest {
            autoListViewModel.fetchManufacturerList(0,0,"")
            val auto = autoListViewModel.auto
            Assert.assertNotNull(auto.value)
        }
    }

    @Test
    fun testManufacturerClearViewModelData() {
        testCoroutineRule.runBlockingTest {
            autoListViewModel.fetchManufacturerList(0,0,"")

            val auto = autoListViewModel.auto
            autoListViewModel.clearViewModelData()
            Assert.assertNull(auto.value)
        }
    }

    @Test
    fun testMainTyeList() {
        testCoroutineRule.runBlockingTest {
            mainTypesViewModel.fetchMainTypesList("","")
            val auto = mainTypesViewModel.auto
            Assert.assertNotNull(auto.value)
        }
    }

    @Test
    fun testMainTypeClearViewModelData() {
        testCoroutineRule.runBlockingTest {
            mainTypesViewModel.fetchMainTypesList("","")

            val auto = mainTypesViewModel.auto
            mainTypesViewModel.clearViewModelData()
            Assert.assertNull(auto.value)
        }
    }

    @Test
    fun testBuildDateList() {
        testCoroutineRule.runBlockingTest {
            buildDatesViewModel.fetchBuildDates("","","")
            val auto = buildDatesViewModel.auto
            Assert.assertNotNull(auto.value)
        }
    }

    @Test
    fun testBuildDateClearViewModelData() {
        testCoroutineRule.runBlockingTest {
            buildDatesViewModel.fetchBuildDates("","","")

            val auto = buildDatesViewModel.auto
            buildDatesViewModel.clearViewModelData()
            Assert.assertNull(auto.value)
        }
    }
}