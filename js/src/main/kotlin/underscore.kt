
@JsModule("underscore")
private external object UnderscoreImpl {
  fun <T : Function<R>, R> debounce(ƒ: T, timeout: Int): T
}

object Underscore {
  fun <T : Function<R>, R> debounce(timeout: Int, ƒ : T) : T = UnderscoreImpl.debounce(ƒ, timeout)
}

