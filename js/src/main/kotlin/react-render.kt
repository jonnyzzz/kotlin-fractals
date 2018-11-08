package org.jetbrains.demo.kotlinfractals

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.yield

object ReactRenderer {
  suspend fun CoroutineScope.renderJS(image: JSFractalImage, area: Rect<Double>) {
    MandelbrotRender.justRenderTasks(
            chunk = 1_000,
            maxIterations = 200,
            isActive = {isActive},
            image = image,
            area = area).forEach {
      it()
      yield()
    }

    yield()
    image.commit()
  }

  @InternalCoroutinesApi
  suspend fun CoroutineScope.renderJVM(image: JSFractalImage, area: Rect<Double>) {
    BackendRender.apply {
      val htmlImage = renderOnTheServer(image.screenInfo, area)

      println("Image loaded active = $isActive")

      delay(2_500)

      println("Image is about to deliver active = $isActive")

      image.loadFromImage(htmlImage)
    }
  }

  @InternalCoroutinesApi
  suspend fun CoroutineScope.renderMixed(image: JSFractalImage, area: Rect<Double>) {

    val jvm = async(coroutineContext) {
      renderJVM(image, area)
    }

    val js = async(coroutineContext) {
      renderJS(image, area)
    }

    jvm.await()

    ///stop render in JS
    // when JVM is done
    js.cancelAndJoin()
  }
}

val JSFractalImage.screenInfo get() = ScreenInfo(width, height)
