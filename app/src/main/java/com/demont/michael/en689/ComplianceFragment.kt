package com.demont.michael.en689

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.demont.michael.en689.databinding.FragmentComplianceBinding


class ComplianceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentComplianceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_compliance, container, false)
        val args:ComplianceFragmentArgs = ComplianceFragmentArgs.fromBundle(requireArguments())

        if (args.radioB == 1) {
            binding.expView.text = "ln(GM): "+"\n"+"ln(GSD): "+"\n"+"UR: "+"\n"+"UT: "
        }
        else if (args.radioB == 2) {
            binding.expView.text = "AM: "+"\n"+"SD: "+"\n"+"UR: "+"\n"+"UT: "
        }
        else   {binding.expView.text = ""}

        binding.scoreView.text = args.gemid.toString()+"\n"+args.stdv.toString()+"\n"+args.ur.toString()+"\n"+args.ut.toString()

        return binding.root
    }
}