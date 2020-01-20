import math.pow

trait Service {
  def realRoot(index: Int, radicand: Int): Double

  def echo(message: String): String
}

class ExampleService extends Service {
  override def realRoot(index: Int, radicand: Int): Double = pow(radicand, 1.toDouble / index)
  override def echo(message: String): String = message
}