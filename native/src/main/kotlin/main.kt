import org.jetbrains.demo.kotlinfractals.*

fun Array<String>.arg(arg: String, def: Int) : Int  {
  val prefix = "--$arg="
  return firstOrNull { it.startsWith(prefix) }?.
          removePrefix(prefix)?.
          toIntOrNull() ?: def
}

fun main(args: Array<String>) {
  println("Kotlin Fractals Native!")

  val width = args.arg("width", 80)
  val height = args.arg("height", 40)

  val image = NativeTextImage(width, height)

  MandelbrotRender.justRender(
          image = image,
          maxIterations = 100,
          area = Rect(-2.0, -.9, .9, .9)
  )

  image.toConsole()
}
