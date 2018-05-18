package org.jetbrains.demo.kotlinfractals

import kotlin.math.ln

object MandelbrotSet {

  fun isIncluded(x: Double, y: Double): Double {
    val maxIt = 100_000

    val c = ComplexNumber(x, y)
    var z = ComplexNumber.ZERO

    (1 until maxIt).forEach { count ->
      z = z * z + c

      if (z.mod2 > 4) {
        return 1.0 / maxIt * count
      }
    }

    return 1.0
  }
}

class MandelbrotPointIteration(
        val c: ComplexNumber
) : Iterator<MandelbrotPointIteration> {
  private var z = ComplexNumber.ZERO

  var iteration = 0
    private set

  var mod2 = 0.0
    private set

  override fun next() : MandelbrotPointIteration {
    z = z * z + c
    mod2 = z.mod2

    iteration ++

    return this
  }

  override fun hasNext() = mod2 < 4
}
