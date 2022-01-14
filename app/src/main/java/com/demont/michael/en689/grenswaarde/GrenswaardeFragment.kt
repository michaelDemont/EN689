package com.demont.michael.en689.grenswaarde

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
import com.demont.michael.en689.databinding.FragmentGrenswaardeBinding
import java.util.ArrayList


class GrenswaardeFragment : Fragment() {
    //toegevoegd
    private lateinit var viewModel: GrenswaardeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentGrenswaardeBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_grenswaarde, container, false)
        val args = GrenswaardeFragmentArgs.fromBundle(requireArguments())

        viewModel = ViewModelProviders.of(this).get(GrenswaardeViewModel::class.java)
        viewModel.numNum = args.aantalM
        viewModel.para = args.results

        val gComp = args.firma
        val gAantal = args.aantalM
        val gResults = args.results
        val gPost = args.func
        val gElement = args.elem
        var gGrens : String =""

        var doubleResults = ArrayList<Double>()
        gResults.forEach {result->
            doubleResults.add(result.toDouble())
        }


        binding.grensButton.setOnClickListener {
            val tve: TextView = binding.inputText1
            gGrens = tve.text.toString()

            viewModel.gwT = gGrens
            viewModel.doorsturen()
            }

        viewModel.wayCOMP.observe(viewLifecycleOwner, Observer { gogo ->
            if(gogo) {
                viewModel.createMeasurement()
                viewModel.aanpassenWaarden(gComp, gPost, gElement, doubleResults, gGrens.toDouble(), 1)
                view?.findNavController()?.navigate(
                    GrenswaardeFragmentDirections.actionGrenswaardeFragmentToComplianceFragment(
                        "","","","",0))
            }
        })

        viewModel.wayNC.observe(viewLifecycleOwner, Observer { gogo ->
            if(gogo) {
                viewModel.createMeasurement()
                viewModel.aanpassenWaarden(gComp, gPost, gElement, doubleResults, gGrens.toDouble(), 0)
                view?.findNavController()?.navigate(
                    GrenswaardeFragmentDirections.actionGrenswaardeFragmentToNonComplianceFragment(
                        "","","","",0))
            }
        })

        viewModel.wayDoubt.observe(viewLifecycleOwner, Observer { gogo ->
            if(gogo) {
                viewModel.createMeasurement()
                viewModel.aanpassenWaarden(gComp, gPost, gElement, doubleResults, gGrens.toDouble(), 2)
                view?.findNavController()?.navigate(
                    R.id.action_grenswaardeFragment_to_doubtFragment)
            }
        })

        viewModel.next.observe(viewLifecycleOwner, Observer { gogo ->
            if(gogo) {
                view?.findNavController()?.navigate(
                    GrenswaardeFragmentDirections.actionGrenswaardeFragmentToLogNormGraphFragment(gAantal, gResults, gGrens.toFloat(),gComp, gPost,gElement))
            }
        })

            return binding.root
        }
    }

