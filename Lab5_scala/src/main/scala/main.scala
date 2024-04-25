import WeekDay.{Friday, Monday, Saturday, Sunday, Thursday, Tuesday, Wednesday}

@main
def main(): Unit = {
  println(distance(3.0f, 4.0f)(0.0f, 0.0f))

  val tPerson1: tPerson = ("Jan", "Kowalski", 28, "M", 43)
  val tPerson2: tPerson = ("Anna", "Kowalska", 26, "F", 38)
  val tPartner: tPartnership = (tPerson1, tPerson2)
  val cPerson1 = cPerson("Jan", "Kowalski", 28, "M", 43)
  val cPerson2 = cPerson("Anna", "Kowalska", 26, "F", 38)
  val cPartner = cPartnership(cPerson1, cPerson2)

  println(tChild(tPartner))
  println(cPersonToStr(cChild(cPartner)))
  cPerson1.lastName = "Nowak"
  println(cPersonToStr(cPerson1))

  println(weekDayToString(WeekDay.Friday))
  println(nextDay(WeekDay.Sunday))
  println(nextDay(WeekDay.Monday))
  println(safeHead(List()))
  println(safeHead(List(3, 2)))
  println(volume(SolidFigure.Cone(2.0, 3.0)))
}

//zadanie 1
type Point2D = (Float, Float)
def distance(p1: Point2D)(p2: Point2D): Float = {
  math.sqrt(math.pow(p1._1 - p2._1, 2) + math.pow(p1._2 - p2._2, 2)).toFloat
}

//zadanie 2
type tPerson = (String, String, Int, String, Int)
type tPartnership = (tPerson, tPerson)
class cPerson(var firstName: String, var lastName: String, var age: Int, var gender: String, var shoeSize: Int)
class cPartnership(var p1: cPerson, var p2: cPerson)

val rand = new scala.util.Random
val names: List[(String, String)] = List(("Adam", "M"), ("Tomasz", "M"), ("Natalia", "F"), ("Agnieszka", "F"))
val namesCount = 4

def tChild(ps: tPartnership): tPerson = {
  val nr = rand.nextInt(namesCount)
  (names(nr)._1, ps._1._2, 0, names(nr)._2, rand.between(9, 12))
}

def cChild(ps: cPartnership): cPerson = {
  val nr = rand.nextInt(namesCount)
  cPerson(names(nr)._1, ps.p1.lastName, 0, names(nr)._2, rand.between(9, 12))
}

def cPersonToStr(person: cPerson): String = {
  person.firstName + "," + person.lastName + "," + person.age + "," + person.gender + "," + person.shoeSize
}

//zadanie 3
enum WeekDay:
  case Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday

def weekDayToString(day: WeekDay): String = {
  day match
    case Monday => "Poniedzialek"
    case Tuesday => "Wtorek"
    case Wednesday => "Sroda"
    case Thursday => "Czwartek"
    case Friday => "Piatek"
    case Saturday => "Sobota"
    case Sunday => "Niedziela"
}

def nextDay(day: WeekDay): WeekDay = {
  WeekDay.fromOrdinal((day.ordinal + 1) % 7)
}

//zadanie 4
enum Maybe[+A]:
  case Nothing
  case Just(value: A)

def safeHead[A](list: List[A]): Maybe[A] = {
  list match
    case hd :: tl => Maybe.Just(hd)
    case _ => Maybe.Nothing
}

//zadanie 5
enum SolidFigure:
  case Cuboid(x: Double, y: Double, z: Double)
  case Cone(r: Double, h: Double)
  case Sphere(r: Double)
  case Cylinder(r: Double, h: Double)

def volume(figure: SolidFigure): Double = figure match {
  case SolidFigure.Cuboid(x, y, z) => x * y * z
  case SolidFigure.Cone(r, h) => Math.PI * r * r * h / 3
  case SolidFigure.Sphere(r) => (4.0 / 3) * Math.PI * r * r * r
  case SolidFigure.Cylinder(r, h) => Math.PI * r * r * h
}
