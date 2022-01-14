package com.demont.michael.en689.input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.demont.michael.en689.R
import com.demont.michael.en689.databinding.FragmentInputBinding
import kotlin.collections.ArrayList


class InputFragment : Fragment() {
    //toegevoegd
    private lateinit var viewModel: InputViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        val binding: FragmentInputBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_input, container, false)
        val args = InputFragmentArgs.fromBundle(requireArguments())
        val iAantal = args.aantalM
        val iComp = args.firma
        val iPost = args.func
        val iElement = args.elem

        //toegevoegd
        viewModel = ViewModelProviders.of(this).get(InputViewModel::class.java)

        viewModel.iteratie = iAantal
        val tv: TextView = binding.inputText
        tv.text = getString(R.string.input_text1)
        val tve: TextView = binding.inputText1
        tve.setHint(R.string.input_hint1)

        var measureValues: MutableList<Float> = ArrayList()

        binding.submitButton.setOnClickListener {
            viewModel.evt = tve.text.toString()
            viewModel.check()
        }

        viewModel.waarde.observe(viewLifecycleOwner, Observer {  waarde ->
            measureValues.add(waarde)
        })

        viewModel.teller.observe(viewLifecycleOwner, Observer { ronde ->
            when(ronde) {
                1 ->    {}
                2 ->    {  tv.text = getString(R.string.input_globalText1) + "\t"+ "2" + getString(R.string.input_globalText3)
                            tve.setText("")
                            tve.setHint(R.string.input_globalHint3)}
                3 ->    {  tv.text = getString(R.string.input_globalText1) + "\t"+ "3" + getString(R.string.input_globalText4)
                            tve.setText("")
                            tve.setHint(R.string.input_globalHint4)}
                else -> {  tv.text = getString(R.string.input_globalText1) + "\t"+ ronde.toString() + getString(R.string.input_globalText2)
                            tve.setText("")
                            tve.setHint(ronde.toString()+resources.getString(R.string.input_globalHint))
                        }
            }
        })

        viewModel.inputOK.observe(viewLifecycleOwner, Observer { good ->
            if(good) {
                view?.findNavController()?.navigate(
                    InputFragmentDirections.actionInputFragmentToGrenswaardeFragment(iAantal, measureValues.sorted().toFloatArray(), iComp, iPost!!, iElement)
                )
            }
        })

        return binding.root
    }
}




