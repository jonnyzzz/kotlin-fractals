package org.jetbrains.demo.kotlinfractals

interface FractalImage {
  val pixelRect: Rect<Int>
  fun putPixel(p: Pixel, c: Color)
}

object MandelbrotRender {
  val initialArea = Rect(-2.0, -2.0, 2.0, 2.0)

  fun justRender(maxIterations: Int = 1500,
                 image: FractalImage,
                 area: Rect<Double>) {
    val t = Transformation(image.pixelRect, area)

    val picker = ColorPicker(maxIterations)

    //TODO: split the job in chinks
    t.forEachPixel { p, c ->

      val pt = MandelbrotPointIteration(c)
              .asSequence()
              .take(maxIterations)
              .last()

      image.putPixel(p, picker.selectColour(pt))
    }
  }
}
