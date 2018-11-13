package org.jetbrains.demo.kotlinfractals

import kotlinx.cinterop.memScoped
import platform.CoreImage.CIImage

object TheRenderer {

  @ExperimentalUnsignedTypes
  fun render(width: Int, height: Int) : CIImage {
    val image = NativeTextImage(width, height)

    MandelbrotRender.justRender(
            image = image,
            maxIterations = 20,
            area = Rect(
                    left = -1.5,
                    right = .8,
                    top = -.9,
                    bottom = .9
            )
    )

    memScoped {
      return image.run { toImage() }
    }
  }
}


