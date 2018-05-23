package org.jetbrains.demo.kotlinfractals

import org.w3c.dom.HTMLImageElement
import kotlin.browser.window


object BackendRender {

  fun jvmClientURL(image: ScreenInfo,
                          area: Rect<Double>,
                          jvm : Boolean = false): String {
    return "http://localhost:8888/mandelbrot?" +
            "top=${area.top}" +
            "&right=${area.right}" +
            "&bottom=${area.bottom}" +
            "&left=${area.left}" +
            "&width=${image.width}" +
            "&height=${image.height}" +
            "&jvm=$jvm"

  }

  fun renderOnTheServer(image: ScreenInfo,
                        area: Rect<Double>,
                        onComplete: (HTMLImageElement) -> Unit) {

    val loader = js("new Image();") as HTMLImageElement

    loader.addEventListener("load", {
      println("Loaded image from the file")
      window.setTimeout({
        onComplete(loader)
      }, 3_000)
    })

    loader.setAttribute("src", jvmClientURL(image, area, jvm = true))

    //fake server is too slow

  }
}