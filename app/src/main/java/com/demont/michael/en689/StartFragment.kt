package com.demont.michael.en689


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.demont.michael.en689.databinding.FragmentStartBinding
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    private var bedrijf:String = ""
    private var number:Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val binding: FragmentStartBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start, container, false)

        setHasOptionsMenu(true)
        binding.startButton.setOnClickListener {

            bedrijf = input_firm.text.toString()
            number = input_number_measure.text.toString().toIntOrNull()
            verify(bedrijf, number, it)
        }

        binding.cheatButton.setOnClickListener {

            bedrijf = input_firm.text.toString()
            (activity as MainActivity).setNaam(bedrijf)
            (activity as MainActivity).setAantal(6)
            (activity as MainActivity).setCheat(true)
            it.findNavController().navigate(StartFragmentDirections.actionStartFragmentToPassFragment())
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController())||super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }



    private fun verify (bedrijf:String?, number:Int?, v:View) {
        val builder = StringBuilder()

        if (bedrijf == "") {
           builder.append(getString(R.string.nameMissing))
        }
        if (number == null) {
            builder.append(getString(R.string.numberMissing))
        }
        if (number != null && number < 3) {
            builder.append(getString(R.string.numberNotEnough))
        }
        else if (bedrijf != null && bedrijf != "" && number!= null && number >= 3) {
            (activity as MainActivity).setAantal(number)
            (activity as MainActivity).setNaam(bedrijf)
            v.findNavController().navigate(StartFragmentDirections.actionStartFragmentToInputFragment(number, false))
        }

        text_fout.text = builder
    }
}

