package org.jetbrains.demo.kotlinfractals

import kotlin.math.exp
import kotlin.math.pow

data class Color(val r: Int, val g: Int, val b: Int) {
  companion object {
    val BLACK = Color(0, 0, 0)
    val WHITE = Color(255, 255, 255)
  }
}


class ColorPicker(
        private val setup: IterationSetup
) {
  private val baseColors = arrayOf(
          //        R   G   B
          Color(r = 66, g = 30, b = 15), // # brown 3
          Color(r = 25, g = 7, b = 26), // # dark violett
          Color(r = 9, g = 1, b = 47),// # darkest blue
          Color(r = 4, g = 4, b = 73),// # blue 5
          Color(r = 0, g = 7, b = 100),// # blue 4
          Color(r = 12, g = 44, b = 138),// # blue 3
          Color(r = 24, g = 82, b = 177),// # blue 2
          Color(r = 57, g = 125, b = 209),// # blue 1
          Color(r = 134, g = 181, b = 229),// # blue 0
          Color(r = 211, g = 236, b = 248),// # lightest blue
          Color(r = 241, g = 233, b = 191),// # lightest yellow
          Color(r = 248, g = 201, b = 95),// # light yellow
          Color(r = 255, g = 170, b = 0),// # dirty yellow
          Color(r = 204, g = 128, b = 0),// # brown 0
          Color(r = 153, g = 87, b = 0),// # brown 1
          Color(r = 106, g = 52, b = 3),// # brown 2
          Color(r = 0, g = 0, b = 0)// # v black// 2
  )

  fun selectColour(state: IterationState): Color {

    return Color(255, 255,255)

    @Suppress("NAME_SHADOWING")
    val deep = (state.iteration.toDouble() / setup.maxIterations).normalize()

    //from https://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia
    return baseColors[(deep.pow(exp(1.0)) * baseColors.size).toInt()]
  }
}
