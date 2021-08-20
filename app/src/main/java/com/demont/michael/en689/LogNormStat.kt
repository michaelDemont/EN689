package com.demont.michael.en689

import kotlin.math.ln
import kotlin.math.sqrt

class LogNormStat (numMeasures: Int, resMeasures: List<Double>, gwMeasures: Double, cheat:Boolean, mean:Double){
        val numM: Int
        val resM: List<Double>
        val gwM: Double
        val cht: Boolean
        val mn: Double

        val mapM = mapOf<Int, Double>(6 to 2.187, 7 to 2.120, 8 to 2.072, 9 to 2.035, 10 to 2.005,
                                        11 to 1.981, 12 to 1.961, 13 to 1.944, 14 to 1.929, 15 to 1.917,
                                        16 to 1.905, 17 to 1.895, 18 to 1.886, 19 to 1.878, 20 to 1.870,
                                        21 to 1.863, 22 to 1.857, 23 to 1.851, 24 to 1.846, 25 to 1.841,
                                        26 to 1.836, 27 to 1.832, 28 to 1.828, 29 to 1.824, 30 to 1.820)

        init{
            numM = numMeasures
            resM = resMeasures
            gwM = gwMeasures
            cht = cheat
            mn = mean
        }

        fun calLnGM(): Double {
            var sum: Double = 0.00
            for (resultaat in resM) {
                sum += ln(resultaat)
            }
            if (cht == true){
                sum -= ln(mn)
                return sum/(numM-1)
                }
            else {return sum/numM }
        }

        fun calLnGSD(): Double {
            var verschil: Double = 0.00
            var kwad: Double = 0.00
            var tot: Double = 0.00
            for (resultaat in resM) {
                verschil = (ln(resultaat)-calLnGM())
                kwad = verschil*verschil
                tot += kwad
            }
            if (cht == true) {
                tot -= (ln(mn)-calLnGM())*(ln(mn)-calLnGM())
                return sqrt(tot/(numM-2))
            }
            else {return sqrt(tot/(numM-1))}
        }

        fun calUR(): Double {
            return ((ln(gwM)-calLnGM())/calLnGSD())
        }

        fun searchUT(): Double {
            return mapM.get(numM)!!
        }

        fun testStat(): Boolean {
            return (calUR()> searchUT())
        }


}