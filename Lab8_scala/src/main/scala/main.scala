import Result.{Failure, Success}

import scala.annotation.targetName

@main
def main(): Unit = {
  printRes(sequence())
  printRes(bind(bind(inputConsole(), strToInt), sqrt))
  printRes(inputConsole() >>= strToInt >>= sqrt)
}

//zadanie 1
enum Result[+X]:
  case Success(x: X)
  case Failure(msg: String)

  @targetName(">>=")
  infix def >>=[Y](f: X => Result[Y]): Result[Y] = bind(this, f)

def printRes[X](res: Result[X]): Unit = res match {
  case Result.Success(x) => println("Result: " + x)
  case Result.Failure(msg) => println("Error: " + msg)
}

//zadanie 2
def inputConsole(): Result[String] = {
  val input = scala.io.StdIn.readLine()
  if (input == "") Result.Failure("Empty input")
  else Result.Success(input)
}

def strToInt(str: String): Result[Int] = {
  try {
    Result.Success(str.toInt)
  } catch {
    case e: NumberFormatException => Result.Failure("Not an integer")
  }
}

def sqrt(x: Int): Result[Int] = {
  if x < 0 then return Result.Failure("Negative number")
  val res = scala.math.sqrt(x).toInt
  if res * res != x then Result.Failure("Not a perfect square")
  else Result.Success(res)
}

def sequence(): Result[Int] = {
  inputConsole() match
    case Result.Success(x) => strToInt(x) match {
      case Result.Success(y) => sqrt(y)
      case Result.Failure(msg) => Result.Failure(msg)
    }
    case Result.Failure(msg) => Result.Failure(msg)
}

//zadanie 3
def bind[X, Y](result: Result[X], f: X => Result[Y]): Result[Y] = {
  result match {
    case Result.Success(x) => f(x)
    case Result.Failure(msg) => Result.Failure(msg)
  }
}
