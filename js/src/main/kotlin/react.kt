package org.jetbrains.demo.kotlinfractals

import react.Component
import react.RProps
import react.RState
import react.setState


fun <P : RProps, S : RState, It> Component<P, S>.setStateAction(action: S.(It) -> Unit ) = { e : It ->
  setState { action(e)}
}
