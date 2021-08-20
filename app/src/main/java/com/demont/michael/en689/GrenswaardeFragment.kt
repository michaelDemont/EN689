package com.demont.michael.en689

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.demont.michael.en689.databinding.FragmentGrenswaardeBinding
import kotlinx.android.synthetic.main.fragment_input.*

class GrenswaardeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentGrenswaardeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_grenswaarde, container, false)

        binding.grensButton.setOnClickListener {

            val tve: TextView = inputText1

            if (tve.text.toString().toDoubleOrNull() == null) {
                return@setOnClickListener
            }
            else {
                    var valeurLim: Double = tve.text.toString().toDouble()
                    (activity as MainActivity).setGW(valeurLim)
                    var numNum: Int = (activity as MainActivity).getAantal()
                    var para: List<Double> = (activity as MainActivity).getResults()

                    if (para[numNum - 1] > valeurLim) {
                        it.findNavController().navigate(
                            GrenswaardeFragmentDirections.actionGrenswaardeFragmentToNonComplianceFragment(
                                "","","","",0))
                    }

                    else if (para[numNum - 1] < 0.1*valeurLim) {
                        it.findNavController().navigate(
                            GrenswaardeFragmentDirections.actionGrenswaardeFragmentToComplianceFragment(
                                "","","","",0))
                    }

                    else if (numNum >= 4 && para[numNum - 1] < 0.15*valeurLim) {
                        it.findNavController().navigate(
                            GrenswaardeFragmentDirections.actionGrenswaardeFragmentToComplianceFragment(
                                "","","","",0))
                    }
                    else if (numNum >= 5 && para[numNum - 1] < 0.2*valeurLim) {
                        it.findNavController().navigate(
                            GrenswaardeFragmentDirections.actionGrenswaardeFragmentToComplianceFragment(
                                "","","","",0))
                    }
                    else if (numNum >5) {
                        it.findNavController().navigate(R.id.action_grenswaardeFragment_to_logNormGraphFragment)
                    }

                    else {
                        it.findNavController().navigate(R.id.action_grenswaardeFragment_to_doubtFragment)
                    }

                }
            }
            return binding.root
        }
    }

