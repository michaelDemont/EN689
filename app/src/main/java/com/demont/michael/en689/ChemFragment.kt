package com.demont.michael.en689

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.demont.michael.en689.databinding.FragmentChemBinding
import com.demont.michael.en689.databinding.FragmentInputBinding
import com.demont.michael.en689.input.InputFragmentArgs
import com.demont.michael.en689.input.InputFragmentDirections
import com.demont.michael.en689.input.InputViewModel
import com.demont.michael.en689.start.StartFragmentDirections
import kotlinx.android.synthetic.main.fragment_chem.*
import kotlinx.android.synthetic.main.fragment_chem.view.*
import java.util.ArrayList

class ChemFragment : Fragment() {

    lateinit var myAdapter: ArrayAdapter<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentChemBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_chem, container, false)
        val args = ChemFragmentArgs.fromBundle(requireArguments())

        val cComp = args.firma
        val cAantal = args.aantalM
        val cPost = args.func

        var spin : Spinner
        spin = binding.keuzelijst
        myAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, resources.getStringArray(R.array.CH))
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = myAdapter

        var cElement = spin.selectedItem.toString()

            binding.submitButton.setOnClickListener {
            view?.findNavController()?.navigate(
                ChemFragmentDirections.actionChemFragmentToInputFragment(cAantal, cComp, cPost, cElement))
        }

        return binding.root
    }
}