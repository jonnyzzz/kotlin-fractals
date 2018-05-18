package org.jetbrains.demo.kotlinfractals

import kotlin.math.ln

data class Color(val r: Int, val g: Int, val b: Int) {
  companion object {
    val BLACK = Color(0, 0, 0)
    val GRAY = Color(127, 127, 127)
    val WHITE = Color(255, 255, 255)
  }
}

expect fun colorFromHSL(h : Double, s: Double, l: Double) : Color

class ColorPicker(
        private val maxIterations: Int
) {
  fun selectColour(z: MandelbrotPointIteration): Color {
    if (z.hasNext()) {
      return Color.BLACK
    }

    val s = z.iteration + 1 - ln( ln(z.mod2) / 2.0) / ln(2.0)
    return colorFromHSL(30.0 + 10 * s, 80.0, 60.0)
  }
}
