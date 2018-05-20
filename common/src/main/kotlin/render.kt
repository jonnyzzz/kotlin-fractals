package org.jetbrains.demo.kotlinfractals

interface FractalImage {
  val pixelRect : Rect<Int>

  fun putPixel(p: Pixel, c: Color)

  fun commit()
}

class MandelbrotRender(
        val maxIterations : Int = 1500,
        val image: FractalImage
) {
  private lateinit var t : Transformation

  fun setArea(r : Rect<Double> = Rect(-2.0, -2.0, 2.0, 2.0)) {
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

    image.commit()
  }

  fun toComplex(p: Pixel) = t.toComplex(p)
}
