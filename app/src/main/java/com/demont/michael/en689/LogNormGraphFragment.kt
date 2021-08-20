package com.demont.michael.en689

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.demont.michael.en689.databinding.FragmentLogNormGraphBinding
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_log_norm_graph.*
import kotlin.math.ln
import kotlin.math.sqrt


class LogNormGraphFragment : Fragment() {

    lateinit var series: LineGraphSeries<DataPoint>
    lateinit var axe: LineGraphSeries<DataPoint>
    var PK: MutableList<Double> = ArrayList()
    var PkZvalues: MutableList<Double> = ArrayList()
    var percentage: MutableList<Double> = ArrayList()
    var mu:Double =0.00
    var stdv:Double =0.00
    var MIN:Double =0.00

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = DataBindingUtil.inflate<FragmentLogNormGraphBinding>(inflater, R.layout.fragment_log_norm_graph, container, false)
        var res: List<Double> = (activity as MainActivity).getResults()
        series = LineGraphSeries()
        axe = LineGraphSeries()

        fillPk()
        mu = calculateMU(PK)
        stdv = calculateSTDV(PK, mu)
       fillZVal(PK, mu, stdv)

        var indexA:Int = 0
        while (indexA < res.size) {
            series.appendData(DataPoint(ln(res.get(indexA)), PkZvalues.get(indexA)), true, 30)
            indexA++
        }

        series.color = Color.RED
        series.thickness = 8
        series.setDrawDataPoints(true)
        series.dataPointsRadius = 12f

        if (ln(res.get(0)) < 0) {MIN = ln(res.get(0))*1.05}
        else {MIN = ln(res.get(0))*0.95}

    //Now setting the values for the y-axis
        setPercentages()
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
           it.findNavController().navigate(R.id.action_logNormGraphFragment_to_normGraphFragment)
        }
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var res: List<Double> = (activity as MainActivity).getResults()
        var grootte:Int = res.size-1

        LogGraph.addSeries(series)
        LogGraph.addSeries(axe)

        LogGraph.viewport.setMinX(MIN)
        LogGraph.viewport.setMaxX(ln(res.get(grootte))*1.05)

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

    fun fillPk() {
        var points: Int = (activity as MainActivity).getAantal()
        var iterator = 1
        while (iterator <= points)
        {
            var value:Double = (iterator-3.00/8.00)/(points+1.00/4.00)*100
            PK.add(value)
            iterator++
        }
    }

    fun calculateMU (source: MutableList<Double>): Double {
        val n:Int = source.size
        var sum: Double = 0.00

        for (element in source) {
            sum += element
        }
       return sum/n
    }

    fun calculateSTDV (bron: MutableList<Double>, mu: Double): Double {
        var kwad: Double = 0.00
        val n:Int = bron.size
        for (element in bron) {
            kwad += (element - mu) * (element - mu)
        }
        return sqrt(kwad / n)
    }

    fun fillZVal (basis: MutableList<Double>, mu: Double, stdv:Double) {
        for (element in basis) {
            PkZvalues.add((element - mu) / stdv+5)
        }
    }

    fun setPercentages () {
        percentage.add(2.67)
        percentage.add(2.95)
        percentage.add(3.355)
        percentage.add(3.72)
        percentage.add(4.00)
        percentage.add(4.33)
        percentage.add(5.00)
        percentage.add(5.67)
        percentage.add(6.00)
        percentage.add(6.28)
        percentage.add(6.645)
        percentage.add(7.05)
        percentage.add(7.33)
    }
}