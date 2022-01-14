package com.demont.michael.en689.selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.demont.michael.en689.*
import com.demont.michael.en689.databinding.FragmentSelectionBinding
import kotlinx.android.synthetic.main.fragment_selection.*
import java.util.ArrayList

class SelectionFragment : Fragment() {

    //toegevoegd
    private lateinit var viewModel: SelectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        val binding:FragmentSelectionBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_selection, container, false)

        val args = SelectionFragmentArgs.fromBundle(requireArguments())
            var latnaa = args.aantalM
            var stluser = args.results
            var wg = args.gw
            var amrif = args.firma
            var cnuf = args.func
            var mele = args.elem

        var doubleResults = ArrayList<Double>()
        stluser.forEach {result->
            doubleResults.add(result.toDouble())
        }

        //toegevoegd
        viewModel = ViewModelProviders.of(this).get(SelectionViewModel::class.java)
        viewModel.resM = stluser
        viewModel.numM = latnaa
        viewModel.gwM = wg

        binding.confirmButton.setOnClickListener {

            if (firstAnswerRadioButton.isChecked)
            {
                if (viewModel.ntestStat()) {
                    viewModel.createMeasurement()
                    viewModel.aanpassenWaarden(amrif, cnuf, mele, doubleResults, wg.toDouble(), 1)
                    it.findNavController().navigate(SelectionFragmentDirections.actionSelectionFragmentToComplianceFragment(
                        viewModel.calLnGM().toString(),
                        viewModel.calLnGSD().toString(),
                        viewModel.calUR().toString(),
                        viewModel.searchUT().toString(),
                        1
                    ))
                }
                else {
                    viewModel.createMeasurement()
                    viewModel.aanpassenWaarden(amrif, cnuf, mele, doubleResults, wg.toDouble(), 0)
                    it.findNavController().navigate(
                        SelectionFragmentDirections.actionSelectionFragmentToNonComplianceFragment(
                            viewModel.calLnGM().toString(),
                            viewModel.calLnGSD().toString(),
                            viewModel.calUR().toString(),
                            viewModel.searchUT().toString(),
                            1
                        )
                    )
                }
            }

            else if (secondAnswerRadioButton.isChecked)
            {
                if (viewModel.testStat()) {
                    viewModel.createMeasurement()
                    viewModel.aanpassenWaarden(amrif, cnuf, mele, doubleResults, wg.toDouble(), 1)
                    it.findNavController().navigate(SelectionFragmentDirections.actionSelectionFragmentToComplianceFragment(
                        viewModel.calAM().toString(),
                        viewModel.calSD().toString(),
                        viewModel.calUR().toString(),
                        viewModel.searchUT().toString(),
                        2
                    ))
                }
                else {
                    viewModel.createMeasurement()
                    viewModel.aanpassenWaarden(amrif, cnuf, mele, doubleResults, wg.toDouble(), 0)
                    it.findNavController().navigate(
                        SelectionFragmentDirections.actionSelectionFragmentToNonComplianceFragment(
                            viewModel.calAM().toString(),
                            viewModel.calSD().toString(),
                            viewModel.calUR().toString(),
                            viewModel.searchUT().toString(),
                            2
                        )
                    )
                }
            }
            else return@setOnClickListener
        }
        return binding.root
    }
}