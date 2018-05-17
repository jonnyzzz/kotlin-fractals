import kotlin.browser.*


interface ApplicationBase {
  fun disposeAndExtractState(): dynamic
}


fun main(args: Array<String>) {
  var application: ApplicationBase? = null

  val state: dynamic = module.hot?.let { hot ->
    hot.accept()

    hot.dispose { data ->
      data.appState = application?.disposeAndExtractState()
      application = null
    }

    hot.data
  }

  if (document.body != null) {
    application = start(state)
  } else {
    application = null
    document.addEventListener("DOMContentLoaded", { e -> application = start(state) })
  }

  println("ok...")
}

fun start(state: dynamic): ApplicationBase {
  val application = object:ApplicationBase {
    override fun disposeAndExtractState() = mapOf<String, String>()
  }

  //TODO: start the app some how
  return application
}
