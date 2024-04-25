import scala.annotation.tailrec
import scala.collection.mutable

@main
def main(): Unit = {
  println(stirling(1, 1))
  println(memoized_stirling(1001, 1000))
  println(memoized_stirling(1001, 1000))
  println(ste((5, 4)))
  println()
  println(from(1).head)
  println(from(3).head)
  println(bell(2).head)
  println(stream_tail(2, from(3))())
  println(nElems(10, from(0)))
  println(everySecElem(11, from(0)))
  println(stream_head(0, skipNElems(from(0), 10)))
  val s = streamPairs(10, from(1), bell(0))
  println(nElems(10, s))
  println(nElems(10, modifyStream(from(0), (x: Int) => x * x)))
}

//zadanie 1
def stirling(n: Int, m: Int): Int = {
  if m > n then 0
  else if (m == 1) || (n == m) then 1
  else stirling(n - 1, m - 1) + m * stirling(n - 1, m)
}

val mapStirling: mutable.Map[(Int, Int), Int] = mutable.HashMap[(Int, Int), Int]()

def memoized_stirling(n: Int, m: Int): Int = {
  mapStirling.getOrElseUpdate((n, m), stirling(n, m))
}

//zadanie 2
def make_memoize[A, B](fun: A => B): A => B = {
  val memo = collection.mutable.Map[A, B]()
  (x: A) => memo.getOrElseUpdate(x, fun(x))
}

def func(n: Int, m: Int): Int = {
  if (n == 0) || (m == 0) then m + n else func(n - 1, m) + func(n, m - 1)
}

val ste: ((Int, Int)) => Int = make_memoize(stirling)
val f1: ((Int, Int)) => Int = make_memoize(func)


//zadanie 3
lazy val l: Int = stirling(40, 30)

//zadanie 4
case class Cons[A](head: A, tail: () => Cons[A])

def from(n: Int): Cons[Int] =
  Cons(n, () => from(n + 1))

def bell(n: Int): Cons[Int] = {
  def bellFun(n: Int): Int = {
    if n == 0 then 1
    else (1 to n).map(k => stirling(n, k)).sum
  }

  Cons(bellFun(n), () => bell(n + 1))
}

@tailrec
def stream_head[A](n: Int, cons: Cons[A]): A = {
  if n == 0 then cons.head else stream_head(n - 1, cons.tail())
}

@tailrec
def stream_tail[A](n: Int, cons: Cons[A]): () => Cons[A] = {
  if n == 0 then cons.tail else stream_tail(n - 1, cons.tail())
}

def nElems[A](n: Int, cons: Cons[A]): List[A] = {
  nEveryMElems(n, 1, cons)
}

def everySecElem[A](n: Int, cons: Cons[A]): List[A] = {
  nEveryMElems(n, 2, cons)
}

def nEveryMElems[A](n: Int, m: Int, cons: Cons[A]): List[A] = {
  def nEveryMElemsHelper(n: Int, cons: Cons[A]): List[A] = {
    if n < 0 then Nil else stream_head(0, cons) :: nEveryMElemsHelper(n - 1, stream_tail(m - 1, cons)())
  }

  nEveryMElemsHelper(n - 1, cons)
}

@tailrec
def skipNElems[A](cons: Cons[A], n: Int): Cons[A] = {
  if (n <= 0) cons
  else skipNElems(cons.tail(), n - 1)
}

def streamPairs[A](n: Int, s1: Cons[A], s2: Cons[A]): Cons[(A, A)] = {
  def streamPairsHelper(s1: Cons[A], s2: Cons[A]): Cons[(A, A)] = {
    Cons((s1.head, s2.head), () => streamPairsHelper(s1.tail(), s2.tail()))
  }

  val res = streamPairsHelper(s1, s2)
  stream_head(n - 1, res)
  res
}

def modifyStream[A, B](cons: Cons[A], f: A => B): Cons[B] = {
  def modifyStreamHelper(cons: Cons[A]): Cons[B] = {
    Cons(f(cons.head), () => modifyStreamHelper(cons.tail()))
  }

  modifyStreamHelper(cons)
}