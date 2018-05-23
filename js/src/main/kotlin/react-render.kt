package org.jetbrains.demo.kotlinfractals

object ReactRenderer {

  fun renderJS(image: JSFractalImage, area: Rect<Double>) {
    MandelbrotRender.justRender(maxIterations = 200,
            image = image,
            area = area)

    image.commit()
  }

}

