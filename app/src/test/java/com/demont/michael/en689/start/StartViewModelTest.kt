package com.demont.michael.en689.start


import android.content.Context
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider

@RunWith(AndroidJUnit4::class)
class StartViewModelTest {

    @Test
    fun verify_noName_missingTrue() {
        //fresh viewModel
        val startViewModel = StartViewModel()
        //when verifying
        startViewModel.bedrijf = ""
        startViewModel.number = 7
        startViewModel.verify()
    }
}