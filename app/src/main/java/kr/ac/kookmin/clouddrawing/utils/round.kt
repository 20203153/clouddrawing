import kotlin.math.roundToInt

fun round(num: Double) : Double {
    return (num * 10000).roundToInt() / 10000.0
}