package org.jetbrains.demo.kotlinfractals

import Underscore
import org.w3c.dom.events.Event
import kotlin.browser.window

data class ScreenInfo(val width: Int, val height: Int)

fun watchScreenSize(onChange: (ScreenInfo) -> Unit) {
  fun fireUpdateSize() {
    onChange(ScreenInfo(window.innerWidth, window.innerHeight))
  }

  val onResize = Underscore.debounce(100) { _: Event ->
    fireUpdateSize()
  }
  window.addEventListener("resize", onResize)

  fireUpdateSize()
}


fun ScreenInfo.toCanvasSize() = ScreenInfo(
        width = width - Styles.canvasBorder * 2,
        height = height - Styles.canvasBorder * 2 - Styles.canvasOffsetBottom - Styles.canvasOffsetTop
)

fun PixelInfo.toScreen() = PixelInfo(
        x = x + Styles.canvasBorder,
        y = y + Styles.canvasBorder + Styles.canvasOffsetTop
)
