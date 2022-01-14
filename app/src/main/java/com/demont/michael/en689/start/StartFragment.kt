package com.demont.michael.en689.start

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.demont.michael.en689.R
import com.demont.michael.en689.databinding.FragmentStartBinding
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {
     //toegevoegd
    private lateinit var viewModel: StartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val binding: FragmentStartBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_start, container, false)
        var aantal: Int? = -1
        var firma : String? = ""
        var werkpost : String? = ""
        val builder = StringBuilder()
        setHasOptionsMenu(true)

        //toegevoegd
        viewModel = ViewModelProviders.of(this).get(StartViewModel::class.java)

        binding.startButton.setOnClickListener {
            viewModel.bedrijf = input_firm.text.toString()
            viewModel.number = input_number_measure.text.toString().toIntOrNull()
            viewModel.funct = input_function_measure.text.toString()
            firma = input_firm.text.toString()
            aantal = input_number_measure.text.toString().toIntOrNull()
            werkpost = input_function_measure.text.toString()
            builder.clear()
            viewModel.verify()
        }

            binding.databaseButton.setOnClickListener {
                view?.findNavController()?.navigate((StartFragmentDirections.actionStartFragmentToPassFragment()))
        }

        viewModel.inputOk.observe(viewLifecycleOwner, Observer { good ->
            if(good) {
                view?.findNavController()?.navigate(
                    StartFragmentDirections.actionStartFragmentToChemFragment(firma!!, aantal!!, werkpost)
                )
            }
        })
        viewModel.nameMissing.observe(viewLifecycleOwner, Observer { missing ->
            if(missing) {
                builder.append(getString(R.string.nameMissing))
            }
            text_fout.text = builder
        })
        viewModel.numberFout.observe(viewLifecycleOwner, Observer { fout ->
            if(fout == 1) {
                builder.append(getString(R.string.numberMissing))
            }
            if(fout == 2) {
                builder.append(getString(R.string.numberNotEnough))
            }
            text_fout.text = builder
        })

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController())||super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

}

