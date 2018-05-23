package org.jetbrains.demo.kotlinfractals

import kotlinx.html.js.onMouseDownFunction
import kotlinx.html.js.onMouseMoveFunction
import kotlinx.html.js.onMouseUpFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledCanvas
import kotlin.browser.window

data class PixelInfo(val x : Int, val y: Int)

interface AutoResizeCanvasControlProps : RProps {
  var canvasSize : ScreenInfo

  //hack to make shouldComponentUpdate work
  var renderMode : Any?
  var renderImage : ((JSFractalImage) -> Unit)?

  var onMouseMove : ((PixelInfo) -> Unit)?
  var onMouseDown: ((PixelInfo) -> Unit)?
  var onMouseUp: ((PixelInfo) -> Unit)?
}

class AutoResizeCanvasControl : RComponent<AutoResizeCanvasControlProps, RState>() {
  private var timerTimeout : Int? = null

  override fun shouldComponentUpdate(nextProps: AutoResizeCanvasControlProps, nextState: RState): Boolean {
    if (props.canvasSize != nextProps.canvasSize) return true
    if (props.renderMode != nextProps.renderMode) return true

    return false
  }

  override fun RBuilder.render() {
    styledCanvas {
      //cache it just in case

      props.renderImage?.let { builder ->
        ref {
          if (it != null) {
            timerTimeout = window.setTimeout({
              builder(fractalImageFromCanvas(it))
            })

          } else {
            timerTimeout?.let { window.clearTimeout(it) }
          }
        }
      }

      css { +Styles.canvas }

      attrs {
        width = props.canvasSize.width.toString()
        height = props.canvasSize.height.toString()

        onMouseMoveFunction = fireCoordinatesEvent(props.onMouseMove)
        onMouseDownFunction = fireCoordinatesEvent(props.onMouseDown)
        onMouseUpFunction = fireCoordinatesEvent(props.onMouseUp)
      }
    }
  }

  private fun fireCoordinatesEvent(it: ((PixelInfo) -> Unit)?) : (Event) -> Unit {
    return { e: dynamic ->
      it?.invoke(PixelInfo(e.nativeEvent.offsetX, e.nativeEvent.offsetY))
    }
  }
}
