import scala.annotation.tailrec
import scala.collection.immutable.::

@main
def main(): Unit = {
  println("Hello world!")
  println(Log("WARN")("22-01-2022")("abc"))
  println(Log("INFO")("22-01-2022")("abc"))
  println(Log("DEBUG")("22-01-2022")("abc"))
  println(Log("CRITICAL")("22-01-2022")("abc"))
  println(Log("WA")("22-01-2022")("abc"))
  println(average(List(1, 2, 3, 4)))
  println(sqrs(List(1, 2, 3, 4, 5, 100)))
  println(acronym("Zaklad Ubezpieczen Spolecznych"))
}

//zadanie0
def Log(prefix: String)(datetime: String)(text: String): String = {
  val color = prefix match {
    case "WARN" => Console.YELLOW
    case "INFO" => Console.BLUE
    case "DEBUG" => Console.GREEN
    case "CRITICAL" => Console.RED
    case _ => Console.RESET
  }
  color + "[" + prefix + "] " + datetime + "\t" + text + Console.RESET
}

//zadanie1
def Map[A](list: List[A])(f: A => A): List[A] = list match {
  case h :: t => f(h) :: Map(t)(f)
  case _ => Nil
}

//zadanie 2
def Filter[A](list: List[A])(pred: A => Boolean): List[A] = list match {
  case h :: t => if pred(h) then h :: Filter(t)(pred) else Filter(t)(pred)
  case _ => Nil
}

//Zadanie3
@tailrec
def Reduce[A](list: List[A])(f: A => A => A)(acc: A): A = list match {
  case h :: t => Reduce(t)(f)(f(acc)(h))
  case _ => acc
}

//Zadanie4
def listLen[A](list: List[A]): Int = {
  @tailrec
  def listLenIter[A](list: List[A], res: Int): Int = list match {
    case h :: t => listLenIter(t, res + 1)
    case _ => res
  }

  listLenIter(list, 0)
}

def sum(x: Int)(y: Int): Int = x + y

def average(list: List[Int]): Float = {
  if list.isEmpty then 0 else
    Reduce(list)(sum)(0).toFloat / listLen(list)
}

//zadanie 5
def acronym(string: String): String = {
  val list = string.split(" ").toList
  val map = Map(list)(s => s.charAt(0).toString)
  Reduce(map)(a => b => a + b)("")
}

//zadanie 6
def sqrs(list: List[Int]): List[Int] = {
  val sumVal = Reduce(list)(sum)(0)
  Map(Filter(list)(x => x * x * x <= sumVal))(x => x * x)
}