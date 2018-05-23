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


  fun renderMixed(image: JSFractalImage, area: Rect<Double>) {
    //this one is async
    renderJVM(image, area)

    //that one performs computation directly
    renderJS(image, area)
  }

}

val JSFractalImage.screenInfo get() = ScreenInfo(width, height)
