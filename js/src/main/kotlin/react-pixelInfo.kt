package org.jetbrains.demo.kotlinfractals

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.br
import react.dom.span
import styled.css
import styled.styledDiv


interface PixelInfoProps : RProps {
  var pixelInfo: PixelInfo?
  var fractalRect: Rect<Double>?
  var canvasSize: ScreenInfo
}


class PixelInfoComponent : RComponent<PixelInfoProps, RState>() {
  override fun RBuilder.render() {
    val pixelInfo = props.pixelInfo ?: return
    val screenInfo = props.canvasSize
    val fractalRect = props.fractalRect

    styledDiv {
      css { +Styles.infoBlock }

      span {
        +"x=${pixelInfo.x}, y=${pixelInfo.y}"
      }

      if (fractalRect != null) {
        br {}
        span {
          +"${toComplex(screenInfo, fractalRect, pixelInfo)}"
        }
        br { }
        span {
          +"[${fractalRect.left}, ${fractalRect.right}] x [${fractalRect.top}, ${fractalRect.bottom}]"
        }
      }
    }
  }
}

