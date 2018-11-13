package org.jetbrains.demo.kotlinfractals

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.readValue
import kotlinx.cinterop.value
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGSize
import platform.CoreImage.CIImage
import platform.CoreImage.kCIFormatARGB8
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.darwin.UInt32Var

@ExperimentalUnsignedTypes
class NativeTextImage(
        val width: Int,
        val height: Int
) : FractalImage {
  private val length
    get() = height * width

  private val data = UIntArray(length) { Colors.BLACK.color}

  override val pixelRect
    get() = Rect(0, 0, width, height)

  override fun putPixel(p: Pixel, c: Color) {
    data[p.x + p.y * width] = c.color
  }

  @ExperimentalUnsignedTypes
  fun MemScope.toImage(): CIImage {
    val array = allocArray<UInt32Var>(length) { this.value = data[it] }
    val data = NSData.dataWithBytes(
            bytes = array,
            length = (4*length).toULong()
    )

    val sz = alloc<CGSize>().apply {
      this.height = this@NativeTextImage.height.toDouble()
      this.width = this@NativeTextImage.width.toDouble()
    }

    val colorSpace = CGColorSpaceCreateDeviceRGB()
    val size = sz.readValue()
    val ciImage = CIImage.imageWithBitmapData(data, (4 * width).toULong(), size, kCIFormatARGB8, colorSpace)

    println("Render completed 4")
    return ciImage
  }
}
