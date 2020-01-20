import math.pow

object Service {
    def realRoot(index: Int, radicand: Int): Double = pow(radicand, 1.toDouble/index)
    def echo(message: String): String = message
}