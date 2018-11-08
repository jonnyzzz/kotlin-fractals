package org.jetbrains.demo.kotlinfractals

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import org.w3c.dom.HTMLImageElement
import kotlin.js.Date

@InternalCoroutinesApi
object BackendRender {

  fun jvmClientURL(image: ScreenInfo,
                   area: Rect<Double>,
                   jvm: Boolean = false): String {
    return "http://localhost:8888/mandelbrot?" +
            "top=${area.top}" +
            "&right=${area.right}" +
            "&bottom=${area.bottom}" +
            "&left=${area.left}" +
            "&width=${image.width}" +
            "&height=${image.height}" +
            "&jvm=$jvm" +
            "&noCache=${Date.now()}"

  }

  suspend fun CoroutineScope.renderOnTheServer(image: ScreenInfo,
                                area: Rect<Double>): HTMLImageElement = suspendCancellableCoroutine { ctx ->
    val loader = js("new Image();") as HTMLImageElement

    loader.addEventListener("load", {

      println("Loaded image from the file: isActive=$isActive")
      ctx.tryResume(loader)?.let { ctx.completeResume(it) }

    })

    loader.setAttribute("src", jvmClientURL(image, area, jvm = true))
  }

}
