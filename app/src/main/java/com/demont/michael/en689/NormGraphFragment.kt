package com.demont.michael.en689

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.demont.michael.en689.databinding.FragmentNormGraphBinding
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
    var PK: MutableList<Double> = ArrayList()
    var PkZvalues: MutableList<Double> = ArrayList()
    var percentage: MutableList<Double> = ArrayList()
    var mu:Double =0.00
    var stdv:Double =0.00
    var MIN:Double =0.00

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding: FragmentNormGraphBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_norm_graph, container, false)
        var res: List<Double> = (activity as MainActivity).getResults()
        series = LineGraphSeries()
        axe = LineGraphSeries()

        fillPk()
        mu = calculateMU(PK)
        stdv = calculateSTDV(PK, mu)
        fillZVal(PK, mu, stdv)

        var indexC:Int = 0
        while (indexC < res.size) {
            series.appendData(DataPoint(res.get(indexC), PkZvalues.get(indexC)), true, 30)
            indexC++
        }

        series.color = Color.BLUE
        series.thickness = 8
        series.setDrawDataPoints(true)
        series.dataPointsRadius = 12f

        MIN = res.get(0) *0.95

        //Now setting the values for the y-axis
        setPercentages()
        var indexD:Int = 0
          while (indexD < percentage.size) {
               axe.appendData(DataPoint(MIN, percentage.get(indexD)), true, 30)
               indexD++
           }
        axe.color = Color.BLACK
        axe.thickness = 5
        axe.setDrawDataPoints(true)
        axe.dataPointsRadius = 4f

        binding.normButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_normGraphFragment_to_selectionFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var res: List<Double> = (activity as MainActivity).getResults()
        var grootte:Int = res.size-1

        graph.addSeries(series)
        graph.addSeries(axe)

        graph.viewport.setMinX(MIN)
        graph.viewport.setMaxX(res.get(grootte)*1.05)

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



