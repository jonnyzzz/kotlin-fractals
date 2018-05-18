package org.jetbrains.demo.kotlinfractals

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

interface IterationState {
  val mod2 : Double

  ///from 0 to 1
  val iteration: Int
}

data class IterationSetup(
  val maxIterations : Int
)

class MandelbrotPointIteration(
        val c: ComplexNumber
) : IterationState {
  private var z = ComplexNumber.ZERO

  override var iteration = 0
    private set

  override var mod2 = 0.0
    private set

  fun next(setup: IterationSetup) : MandelbrotPointIteration? {
    z = z * z + c
    mod2 = z.mod2

    iteration ++

    return if (!hasNext(setup)) {
      null
    } else this
  }

  fun hasNext(setup: IterationSetup) = iteration < setup.maxIterations && mod2 < 4
}
