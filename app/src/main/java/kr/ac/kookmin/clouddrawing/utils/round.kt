import kotlin.math.roundToInt

fun round(num: Double, factor: Double=10000.0) : Double {
    return (num * factor).roundToInt() / factor
}