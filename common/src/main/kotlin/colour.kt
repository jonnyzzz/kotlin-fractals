package org.jetbrains.demo.kotlinfractals

import kotlin.math.ln

      // common code
      expect class Color

      object Colors

      expect fun Colors.hsl(h: Double,
                           s: Double,
                           l: Double) : Color

      expect val Colors.BLACK: Color
      expect val Colors.WHITE: Color


class ColorPicker(
        private val maxIterations: Int
) {
  fun selectColour(z: MandelbrotPointIteration): Color {
    if (z.hasNext()) {
      return Colors.BLACK
    }

    val s = z.iteration + 1 - ln( ln(z.mod2) / 2.0) / ln(2.0)
    return Colors.hsl(30.0 + 10 * s, 90.0, 50.0)
  }
}
