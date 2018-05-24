package org.jetbrains.demo.kotlinfractals

import kotlin.coroutines.experimental.buildSequence

interface FractalImage {
  val pixelRect: Rect<Int>
  fun putPixel(p: Pixel, c: Color)
}

typealias MandelbrotRenderTask = () -> Unit

object MandelbrotRender {
  val initialArea = Rect(-2.0, -2.0, 2.0, 2.0)

  fun justRender(maxIterations: Int = 1500,
                 isActive: () -> Boolean = {true},
                 image: FractalImage,
                 area: Rect<Double>) {

    justRenderTasks(
            maxIterations = maxIterations,
            chunkSize = 1,
            isActive = isActive,
            image = image,
            area = area).forEach { it() }
  }

  fun justRenderTasks(maxIterations: Int = 1500,
                      chunkSize: Int = 2_000,
                      isActive: () -> Boolean = { true },
                      image: FractalImage,
                      area: Rect<Double>): Sequence<MandelbrotRenderTask> {

    val t = Transformation(image.pixelRect, area)
    val picker = ColorPicker(maxIterations)

    return buildSequence {
      t.forEachPixel(isActive) { p, c ->
        yield(p to c)
      }
    }.chunked(chunkSize).map { tasks ->
      {
        tasks.forEach { (p, c) ->
          val pt = MandelbrotPointIteration(c)
                  .asSequence()
                  .take(maxIterations)
                  .last()

          image.putPixel(p, picker.selectColour(pt))
        }
      }
    }
  }

  val renderImageTask : MandelbrotRenderTask = {}

  fun layerByLayer(image: FractalImage,
                   maxIterations: Int = 1500,
                   iterationsChunk: Int = 1,
                   isActive: () -> Boolean = { true },
                   area: Rect<Double>): Sequence<MandelbrotRenderTask> {

    val t = Transformation(image.pixelRect, area)
    val picker = ColorPicker(maxIterations)

    var pixels = t.mapPixel(isActive) { p, c ->
      p to MandelbrotPointIteration(c)
    }

    return buildSequence {
      yield() {
        pixels.forEach { (p, _) ->
          image.putPixel(p, Colors.BLACK)
        }
      }

      (0 until maxIterations).forEach {
        yield() {
          pixels = pixels.mapNotNull { (p, it) ->
            val last = it
                    .asSequence()
                    .take(iterationsChunk)
                    .lastOrNull() ?: it

            if (!it.hasNext()) {
              image.putPixel(p, picker.selectColour(last))
              null
            } else {
              p to last
            }
          }
        }

        if (pixels.isEmpty()) return@buildSequence
        yield(renderImageTask)
      }
    }
  }
}
