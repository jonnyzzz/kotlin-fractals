package org.jetbrains.demo.kotlinfractals

object ReactRenderer {

  fun renderJS(image: JSFractalImage, area: Rect<Double>) {
    MandelbrotRender.justRender(maxIterations = 200,
            image = image,
            area = area)

    image.commit()
  }

  fun renderJVM(image: JSFractalImage, area: Rect<Double>) {
    BackendRender.renderOnTheServer(image.screenInfo, area) {
      println("Image loaded")
      //TODO: handle dispose?
      image.loadFromImage(it)
    }
  }

}

val JSFractalImage.screenInfo get() = ScreenInfo(width, height)
