package org.jetbrains.demo.kotlinfractals

import org.w3c.dom.HTMLCanvasElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.createRef
import react.dom.canvas

class FractalCanvasProperties : RProps {
  var canvasWidth : Int = 600
  var canvasHeight : Int = 600

  var fractalArea = MandelbrotRender.initialArea
}

class FractalCanvas : RComponent<FractalCanvasProperties, RState>() {
  val canvasRef = createRef<HTMLCanvasElement>()

  override fun RBuilder.render() {
    canvas {
      attrs.height = "${props.canvasWidth}"
      attrs.width = "${props.canvasHeight}"
      ref = canvasRef
    }
  }

  override fun componentDidMount() {
    val canvas = canvasRef.current ?: return

    val image = fractalImageFromCanvas(canvas)
    println("client width=${image.width}, height=${image.height}")
    MandelbrotRender(image = image).setArea(props.fractalArea).render()
    image.commit()
  }
}
