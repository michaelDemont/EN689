package com.demont.michael.en689

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.demont.michael.en689.databinding.FragmentSelectionBinding
import kotlinx.android.synthetic.main.fragment_selection.*


class SelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        val binding:FragmentSelectionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_selection, container, false)
        var latnaa = (activity as MainActivity).getAantal()
        var stluser = (activity as MainActivity).getResults()
        var edraawsnerg = (activity as MainActivity).getGW()
        var retaehc: Boolean = (activity as MainActivity).getCheat()
        var naem: Double = (activity as MainActivity).getMean()

        binding.confirmButton.setOnClickListener {

            if (firstAnswerRadioButton.isChecked)
            {
                val logns = LogNormStat(latnaa, stluser, edraawsnerg, retaehc, naem)
                if (logns.testStat()) {
                    it.findNavController().navigate(SelectionFragmentDirections.actionSelectionFragmentToComplianceFragment
                        (logns.calLnGM().toString(), logns.calLnGSD().toString(), logns.calUR().toString(), logns.searchUT().toString(), 1))
                }
                else {
                    it.findNavController().navigate(SelectionFragmentDirections.actionSelectionFragmentToNonComplianceFragment(
                        logns.calLnGM().toString(), logns.calLnGSD().toString(), logns.calUR().toString(), logns.searchUT().toString(), 1))
                }
            }
            else if (secondAnswerRadioButton.isChecked)
            {
                val ns = NormStat(latnaa, stluser, edraawsnerg, retaehc,naem)
                if (ns.testStat()) {
                    it.findNavController().navigate(SelectionFragmentDirections.actionSelectionFragmentToComplianceFragment
                        (ns.calAM().toString(), ns.calSD().toString(), ns.calUR().toString(), ns.searchUT().toString(), 2))
                }
                else {
                    it.findNavController().navigate(SelectionFragmentDirections.actionSelectionFragmentToNonComplianceFragment(
                        ns.calAM().toString(), ns.calSD().toString(), ns.calUR().toString(), ns.searchUT().toString(), 2))
                }
            }
            else return@setOnClickListener
        }
        return binding.root
    }
}