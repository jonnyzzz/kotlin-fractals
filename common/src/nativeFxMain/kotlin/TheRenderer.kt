package org.jetbrains.demo.kotlinfractals

import kotlinx.cinterop.memScoped
import platform.CoreImage.CIImage

object TheRenderer {

  @ExperimentalUnsignedTypes
  fun render(width: Int, height: Int) : CIImage {
    val image = NativeTextImage(width, height)

    MandelbrotRender.justRender(
            image = image,
            maxIterations = 100,
            area = Rect(-2.0, -.9, .9, .9)
    )

    memScoped {
      return image.run { toImage() }
    }
  }
}


