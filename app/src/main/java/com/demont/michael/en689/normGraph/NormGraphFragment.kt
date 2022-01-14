package com.demont.michael.en689.normGraph

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.demont.michael.en689.MainActivity
import com.demont.michael.en689.R
import com.demont.michael.en689.databinding.FragmentLogNormGraphBinding
import com.demont.michael.en689.databinding.FragmentNormGraphBinding
import com.demont.michael.en689.lognNormGraph.LogNormGraphFragmentArgs
import com.demont.michael.en689.lognNormGraph.LogNormGraphFragmentDirections
import com.demont.michael.en689.lognNormGraph.LogNormViewModel
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_log_norm_graph.*
import kotlinx.android.synthetic.main.fragment_norm_graph.*
import kotlin.math.ln
import kotlin.math.sqrt


class NormGraphFragment : Fragment() {

    lateinit var series: LineGraphSeries<DataPoint>
    lateinit var axe: LineGraphSeries<DataPoint>
    var MIN:Double =0.00
    lateinit var nResults: FloatArray

    var percentage = listOf(2.67, 2.95, 3.355, 3.72, 4.00, 4.33, 5.00, 5.67, 6.00, 6.28, 6.645, 7.05, 7.33)
    private lateinit var viewModel: NormGraphViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            val binding: FragmentNormGraphBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_norm_graph, container, false)
            viewModel = ViewModelProviders.of(this).get(NormGraphViewModel::class.java)

        val args = NormGraphFragmentArgs.fromBundle(requireArguments())
        var nAantal = args.aantalM
        viewModel.nNumber = nAantal
        nResults = args.results
        var nGrens = args.gw
        var nComp = args.firma
        var nPost = args.func
        var nElement = args.elem

        var PkZvalues: ArrayList<Double>

        //Maken grafiek
        series = LineGraphSeries()
        axe = LineGraphSeries()
        viewModel.fillPk()
        viewModel.calculateMU()
        viewModel.calculateSTDV()
        PkZvalues = viewModel.fillZVal()

        var indexC:Int = 0
        while (indexC < nResults.size) {
            series.appendData(DataPoint(nResults.get(indexC).toDouble(), PkZvalues.get(indexC)), true, 30)
            indexC++
        }

        series.color = Color.BLUE
        series.thickness = 8
        series.setDrawDataPoints(true)
        series.dataPointsRadius = 12f

        MIN = nResults.get(0) *0.95

        //Now setting the values for the y-axis
        var indexD:Int = 0
        while (indexD < percentage.size) {
            axe.appendData(DataPoint(MIN, percentage.get(indexD)), true, 30)
            indexD++
        }
        axe.color = Color.BLACK
        axe.thickness = 5
        axe.setDrawDataPoints(true)
        axe.dataPointsRadius = 4f

        //TODO: parameters doorgeven
        binding.normButton.setOnClickListener {
            it.findNavController().navigate(NormGraphFragmentDirections.actionNormGraphFragmentToSelectionFragment(nAantal, nResults, nGrens, nComp, nPost, nElement))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var grootte:Int = nResults.size-1

        graph.addSeries(series)
        graph.addSeries(axe)

        graph.viewport.setMinX(MIN)
        graph.viewport.setMaxX(nResults.get(grootte)*1.05)

        graph.viewport.setMinY(2.50)
        graph.viewport.setMaxY(7.5)

        graph.title = "Normal distribution"
        graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.BOTH
        graph.titleTextSize = 50f
        graph.titleColor = Color.BLUE
        graph.viewport.setXAxisBoundsManual(true)
        graph.viewport.setYAxisBoundsManual(true)
        graph.viewport.setScrollable(true)
        graph.viewport.setScrollableY(true)
        graph.gridLabelRenderer.horizontalAxisTitle = getString(R.string.XAxis_norm)
        //  graph.gridLabelRenderer.verticalAxisTitle = getString(R.string.YAxis)
        graph.gridLabelRenderer.isVerticalLabelsVisible = false

//Hier begint de miserie!!!!
        var lb: DefaultLabelFormatter = DefaultLabelFormatter()
        //      lb.formatLabel(7.33, true) = "99%"
        graph.gridLabelRenderer.labelFormatter = lb
    }
}