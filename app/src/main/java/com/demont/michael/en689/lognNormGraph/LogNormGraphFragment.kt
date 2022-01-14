package com.demont.michael.en689.lognNormGraph

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
import com.demont.michael.en689.R
import com.demont.michael.en689.databinding.FragmentLogNormGraphBinding
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_log_norm_graph.*
import kotlin.collections.ArrayList
import kotlin.math.ln


class LogNormGraphFragment : Fragment() {

    lateinit var series: LineGraphSeries<DataPoint>
    lateinit var axe: LineGraphSeries<DataPoint>
    var MIN:Double =0.00
    lateinit var lnResults: FloatArray

    var percentage = listOf(2.67, 2.95, 3.355, 3.72, 4.00, 4.33, 5.00, 5.67, 6.00, 6.28, 6.645, 7.05, 7.33)
    private lateinit var viewModel: LogNormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentLogNormGraphBinding>(inflater,
            R.layout.fragment_log_norm_graph, container, false)
        viewModel = ViewModelProviders.of(this).get(LogNormViewModel::class.java)

        val args = LogNormGraphFragmentArgs.fromBundle(requireArguments())
        var lnAantal = args.aantalM
        viewModel.vNumber = lnAantal
        lnResults = args.results
        var lnGrens = args.gw
        var lnComp = args.firma
        var lnPost = args.func
        var lnElement = args.elem

        var PkZvalues: ArrayList<Double>

        //Maken grafiek
        series = LineGraphSeries()
        axe = LineGraphSeries()
        viewModel.fillPk()
        viewModel.calculateMU()
        viewModel.calculateSTDV()
        PkZvalues = viewModel.fillZVal()

        var indexA:Int = 0
        while (indexA < lnResults.size) {
            series.appendData(DataPoint(ln(lnResults.get(indexA).toDouble()), PkZvalues.get(indexA)), true, 30)
            indexA++
        }


        series.color = Color.RED
        series.thickness = 8
        series.setDrawDataPoints(true)
        series.dataPointsRadius = 12f

        if (ln(lnResults.get(0)) < 0) {MIN = lnResults.get(0).toDouble()*1.05}
        else {MIN = ln(lnResults.get(0))*0.95}

    //Now setting the values for the y-axis
        var indexB:Int = 0
        while (indexB < percentage.size) {
            axe.appendData(DataPoint(MIN, percentage.get(indexB)), true, 30)
            indexB++
        }
        axe.color = Color.BLACK
        axe.thickness = 5
        axe.setDrawDataPoints(true)
        axe.dataPointsRadius = 6f

        binding.logNormButton.setOnClickListener {
           it.findNavController().navigate(LogNormGraphFragmentDirections.actionLogNormGraphFragmentToNormGraphFragment(lnAantal, lnResults, lnGrens, lnComp, lnPost, lnElement))
        }
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var grootte:Int = lnResults.size-1

        LogGraph.addSeries(series)
        LogGraph.addSeries(axe)

        LogGraph.viewport.setMinX(MIN)
        LogGraph.viewport.setMaxX(ln(lnResults.get(grootte))*1.05)

        LogGraph.viewport.setMinY(2.50)
        LogGraph.viewport.setMaxY(7.5)

        LogGraph.title = "Lognormal distribution"
        LogGraph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.BOTH
        LogGraph.titleTextSize = 50f
        LogGraph.titleColor = Color.RED
        LogGraph.viewport.setXAxisBoundsManual(true)
        LogGraph.viewport.setYAxisBoundsManual(true)
        LogGraph.viewport.setScrollable(true)
        LogGraph.viewport.setScrollableY(true)
        LogGraph.gridLabelRenderer.horizontalAxisTitle = getString(R.string.XAxis)
    //  LogGraph.gridLabelRenderer.verticalAxisTitle = getString(R.string.YAxis)
        LogGraph.gridLabelRenderer.isVerticalLabelsVisible = false

//Hier begint de miserie!!!!
        var lb: DefaultLabelFormatter = DefaultLabelFormatter()
  //      lb.formatLabel(7.33, true) = "99%"
        LogGraph.gridLabelRenderer.labelFormatter = lb
    }
}