import math.pow

object Service { // todo czy to powinien byc aktor?
  def handleRealRoot(index: Int, radicand: Int): Double = pow(radicand, 1.toDouble/index)

  def handleEcho(msg: String): String = msg
}