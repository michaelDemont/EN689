package com.demont.michael.en689

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.demont.michael.en689.databinding.FragmentInputBinding
import kotlinx.android.synthetic.main.fragment_input.*

class InputFragment : Fragment() {

    var teller: Int = 1
    var hoev:Int = 7
    var measureValues: MutableList<Double> = ArrayList()
    var cheat: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        val binding: FragmentInputBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)
        val args:InputFragmentArgs = InputFragmentArgs.fromBundle(requireArguments())
        hoev = args.loopNum
        cheat = args.cheater

        binding.submitButton.setOnClickListener {
            val tv: TextView = inputText
            val tve: TextView = inputText1

            if (tve.text.toString().toDoubleOrNull() ==null) {
                return@setOnClickListener
            }
            else {
                    measureValues.add(tve.text.toString().toDouble())
                    tve.text = ""
                    teller++

                    if (teller == 2) {
                        tv.text = getString(R.string.input_globalText1) +"\t"+ teller.toString() + getString(R.string.input_globalText3)
                        tve.setHint(teller.toString()+ getString(R.string.input_globalHint3))
                    }

                    if (teller == 3) {
                        tv.text = getString(R.string.input_globalText1) +"\t"+ teller.toString() + getString(R.string.input_globalText4)
                        tve.setHint(teller.toString()+ getString(R.string.input_globalHint4))
                    }

                    if (teller > 3 && teller <= hoev) {
                        tv.text = getString(R.string.input_globalText1) + "\t"+ teller.toString() + getString(R.string.input_globalText2)
                        tve.setHint(teller.toString()+ getString(R.string.input_globalHint))
                     }
    // cheat code
                    else if (teller > hoev && cheat) {
                        var mean:Double = (measureValues.get(0)+measureValues.get(1)+measureValues.get(2)+measureValues.get(3)+measureValues.get(4))/5
                        measureValues.add(mean)
                        (activity as MainActivity).setMean(mean)
                        (activity as MainActivity).setResults(measureValues)
                        it.findNavController().navigate(R.id.action_inputFragment_to_grenswaardeFragment)
                    }
     // normal code
                    else if (teller > hoev) {
                        (activity as MainActivity).setResults(measureValues)
                        it.findNavController().navigate(R.id.action_inputFragment_to_grenswaardeFragment)
                    }
                 }
            }
            return binding.root
    }
}


