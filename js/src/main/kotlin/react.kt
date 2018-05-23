package org.jetbrains.demo.kotlinfractals

import react.Component
import react.RProps
import react.RState
import react.setState


fun <P : RProps, S : RState, It> Component<P, S>.setStateAction(action: S.(It) -> Unit ) = { e : It ->
  setState { action(e)}
}



operator fun Transformation.rangeTo(p: PixelInfo): Complex = toComplex(p.x, p.y)
operator fun ScreenInfo.rem(r: Rect<Double>) = Transformation(rect, r)
val ScreenInfo.rect get() = Rect(top = 0, left = 0, right = width, bottom = height)

fun toComplex(canvas: ScreenInfo, area: Rect<Double>, p: PixelInfo) = canvas % area .. p

