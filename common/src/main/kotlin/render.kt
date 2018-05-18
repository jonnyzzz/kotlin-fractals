package org.jetbrains.demo.kotlinfractals


class MondelbrotSetUpdatingRenderer (
        val t : Transformation,
        val colorPicker: ColorPicker,
        val setup : IterationSetup
) {
  val sz = t.pixelRect.pixels

  val area = Array<MandelbrotPointIteration?>(sz) {
    MandelbrotPointIteration(t.fromIndex(it))
  }

  val done = Array<Color?>(sz) { null }

  fun iterateOnce() {
    area.updateNotNull { idx, it ->
      val prev = it
      val next = it.next(setup)

      if (next == null) {
        done[idx] = colorPicker.selectColour(prev)
        null
      } else {
        next
      }
    }
  }

  inline fun iteratePixels(data: (p: Point, c: Color) -> Unit) {
    (0 until sz).drop(10000).take(100).forEach {
      val pixel = t.fromIndexToPixel(it)
      val d = done[it]

      val c = if (d == null) Color.BLACK else Color.WHITE
      data(pixel, c)
//
//      if (d != null) {
//        data(pixel, d)
//      }
//
//      data(pixel, area[it]?.let { colorPicker.selectColour(it) } ?: Color.BLACK)
    }
  }

  private inline fun <T : Any> Array<T?>.updateNotNull(action: (Int, T) -> T?) {
    forEachIndexed { idx, t ->
      if (t != null) {
        this[idx] = action(idx, t)
      }
    }
  }
}
