package org.jetbrains.demo.kotlinfractals

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.readValue
import kotlinx.cinterop.refTo
import platform.CoreGraphics.CGSize
import platform.CoreImage.CIImage
import platform.CoreImage.kCIFormatARGB8
import platform.Foundation.NSData
import platform.Foundation.create

class NativeTextImage(
        val width: Int,
        val height: Int
) : FractalImage {
  private val length
    get() = height * width

  private val data = IntArray(length) { Colors.BLACK.color}

  override val pixelRect
    get() = Rect(0, 0, width, height)

  override fun putPixel(p: Pixel, c: Color) {
    data[p.x + p.y * width] = c.color
  }

  @ExperimentalUnsignedTypes
  fun MemScope.toImage(): CIImage {

    val data = NSData.create(
            bytes = data.refTo(0).getPointer(this),
            length = (4*length).toULong()
    )

    val sz = alloc<CGSize>().apply {
      this.height = this@NativeTextImage.height.toDouble()
      this.width = this@NativeTextImage.width.toDouble()
    }

    val size = sz.readValue()
    return CIImage.imageWithBitmapData(data, (4*width).toULong(), size, kCIFormatARGB8, null)
  }
}
