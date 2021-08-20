package com.demont.michael.en689

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.demont.michael.en689.databinding.FragmentPassBinding
import com.demont.michael.en689.databinding.FragmentStartBinding
import kotlinx.android.synthetic.main.fragment_input.*
import kotlinx.android.synthetic.main.fragment_pass.*
import kotlinx.android.synthetic.main.fragment_start.*

class PassFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentPassBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pass, container, false)

        binding.guessButton.setOnClickListener {
            val guess: TextView = guessText
            var content:CharSequence? = guess.text

            if (content == null)
                {}
            if (content.toString()=="")
                {}
            else {
                var result = content.toString().toInt()
                if (result == 212223) {
                    it.findNavController()
                        .navigate(PassFragmentDirections.actionPassFragmentToInputFragment(5, true))
                }
                else { guess.text =""}
            }
        }

        binding.annulatieButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root

    }
}