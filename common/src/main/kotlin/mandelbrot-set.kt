package org.jetbrains.demo.kotlinfractals

import org.jetbrains.demo.complex.ComplexNumber
import org.jetbrains.demo.complex.mod2
import org.jetbrains.demo.complex.plus
import org.jetbrains.demo.complex.times

object MandelbrotSet {

  fun isIncluded(x: Double, y: Double) : Double {
    val maxIt = 100_000

    val c = ComplexNumber(x,y)
    var z = ComplexNumber.ZERO

    (1 until maxIt).forEach { count ->
      z = z*z + c

      if (z.mod2 > 4) {
        return 1.0 / maxIt * count
      }
    }

    return 1.0
  }
}
