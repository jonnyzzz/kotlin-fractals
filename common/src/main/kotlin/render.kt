package org.jetbrains.demo.kotlinfractals

interface FractalImage {
  val pixelRect : Rect<Int>
  fun putPixel(p: Pixel, c: Color)
}

class MandelbrotRender(
        val maxIterations : Int = 1500,
        val image: FractalImage
) {
  private lateinit var t : Transformation

  companion object {
    val initialArea = Rect(-2.0, -2.0, 2.0, 2.0)
  }

  var fractalArea : Rect<Double>
    get() = t.fractalRect
    set(value) = setArea(value)

  fun setArea(r : Rect<Double> = initialArea) {
    t = Transformation(image.pixelRect, r)
  }

  init {
    setArea()
  }

  fun render() {
    val picker = ColorPicker(maxIterations)

    t.forEachPixel { p, c ->

      var pt = MandelbrotPointIteration(c)
      repeat(maxIterations) {
        if (pt.hasNext()) {
          pt = pt.next()
        }
      }

      image.putPixel(p, picker.selectColour(pt))
    }
  }

  fun toComplex(p: Pixel) = t.toComplex(p)
}
