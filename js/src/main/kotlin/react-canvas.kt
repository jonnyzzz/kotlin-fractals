package org.jetbrains.demo.kotlinfractals

import kotlinx.html.js.onMouseMoveFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledCanvas

data class PixelInfo(val x : Int, val y: Int)

interface AutoResizeCanvasControlProps : RProps {
  var canvasSize : ScreenInfo

  var renderImage : (JSFractalImage.() -> Unit)?

  var onMouseMove : ((PixelInfo) -> Unit)?
  var onMouseDown: ((PixelInfo) -> Unit)?
  var onMouseUp: ((PixelInfo) -> Unit)?
}

class AutoResizeCanvasControl : RComponent<AutoResizeCanvasControlProps, RState>() {

  override fun shouldComponentUpdate(nextProps: AutoResizeCanvasControlProps, nextState: RState): Boolean {
    if (props.canvasSize != nextProps.canvasSize) return true

    return false
  }

  override fun RBuilder.render() {
    styledCanvas {
      //cache it just in case

      props.renderImage?.let { builder ->
        ref {
          if (it != null) {
            println("ref called")
            builder(fractalImageFromCanvas(it))
          }
        }
      }

      css { +Styles.canvas }

      attrs {
        width = props.canvasSize.width.toString()
        height = props.canvasSize.height.toString()

        props.onMouseMove?.let {
          onMouseMoveFunction = { e : dynamic ->
            it(PixelInfo(e.nativeEvent.offsetX, e.nativeEvent.offsetY))
          }
        }
      }
    }
  }

}
