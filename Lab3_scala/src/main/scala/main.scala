import scala.annotation.tailrec

//:load src/main/scala/main.scala
@main
def main(): Unit = {
  val list1 = List(1, 2, 3, 4, 5, 6, 7, 8)
  val list2 = List(1, 2, 3, 2, 1)
  println(lastEle(list1))
  println(lastTwoEle(list1))
  println(listLen(list1))
  println(listReverse(list1))
  println(isPalindrome(list1))
  println(isPalindrome(list2))
  println(removeDuplicates(list2))
  println(removeEverySecEle(list2))
  println(isPrime(3))
  println(isPrime(5))
  println(isPrime(111))
}

//zadanie 1
@tailrec
def lastEle[A](list: List[A]): Option[A] = {
  if list.isEmpty then None else if list.tail.isEmpty then Some(list.head) else lastEle(list.tail)
}

@tailrec
def lastTwoEle[A](list: List[A]): Option[List[A]] = {
  if list.isEmpty || list.tail.isEmpty then None else if list.tail.tail.isEmpty then Some(list) else lastTwoEle(list.tail)
}

//zadanie 3
def listLen[A](list: List[A]): Int = {
  if list.isEmpty then 0 else 1 + listLen(list.tail)
}

//zadanie 4
def listReverse[A](list: List[A]): List[A] = {
  if list.isEmpty then List() else listReverse(list.tail) ::: List(list.head)
}

//zadanie 5
def isPalindrome[A](list: List[A]): Boolean = {
  list == listReverse(list)
}

//zadanie 6
def removeELem[A](list: List[A], elem: A): List[A] = {
  if list.isEmpty then List() else if list.head == elem then List() else list.head :: removeELem(list.tail, elem)
}

def removeDuplicates[A](list: List[A]): List[A] = {
  if list.isEmpty then List() else list.head :: removeDuplicates(removeELem(list.tail, list.head))
}

//zadanie 7
def removeEverySecEle[A](list: List[A]): List[A] = {
  if list.isEmpty then List() else if list.tail.isEmpty then list else
    list.head :: removeEverySecEle(list.tail.tail)
}

//zadanie 8
@tailrec
def isPrimeHelper(x: Int, y: Int): Boolean = {
  if x <= y then true else if (x % y) == 0 then false else isPrimeHelper(x, y + 2)
}

def isPrime(x: Int): Boolean = {
  if x < 2 then false else if x % 2 == 0 then true else isPrimeHelper(x, 3)
}