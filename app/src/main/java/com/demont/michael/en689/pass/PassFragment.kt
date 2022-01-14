package com.demont.michael.en689.pass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.demont.michael.en689.R
import com.demont.michael.en689.databinding.FragmentPassBinding


class PassFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentPassBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pass, container, false)

        return binding.root

    }
}