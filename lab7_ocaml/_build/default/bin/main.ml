module type Point3DType = sig
  type point
  val create : float -> float -> float -> point
  val getX : point -> float
  val getY : point -> float
  val getZ : point -> float
  val distance : point -> point -> float
end;;

module Point : Point3DType = struct
  type point = { x: float; y: float; z: float }

  let create x y z = { x; y; z }

  let getX p = p.x
  let getY p = p.y
  let getZ p = p.z

  let distance p1 p2 = sqrt((p1.x -. p2.x) ** 2. +. (p1.y -. p2.y) ** 2. +. (p1.z -. p2.z) ** 2.)
end;;

module type SegmentType = sig
  type segment
  val createLine : Point.point -> Point.point -> segment
  val length : segment -> float
end;;

module Segment : SegmentType = struct
  type segment = { p1: Point.point; p2: Point.point }

  let createLine p1 p2 = { p1; p2 }

  let length l = Point.distance l.p1 l.p2
end;;

module type BinaryTreeType = sig
  type 'a tree
  val getEmpty : 'a tree
  val insert : 'a -> 'a tree -> 'a tree
  val delete : 'a -> 'a tree -> 'a tree
  val preorder : 'a tree -> 'a list
  val inorder : 'a tree -> 'a list
  val postorder : 'a tree -> 'a list
  val listOfAllLeafs : 'a tree -> 'a list
end;;

module BinaryTree : BinaryTreeType = struct
  type 'a tree = Nil | Cons of 'a * 'a tree * 'a tree
  let getEmpty = Nil

  let rec insert c = function
    | Nil -> Cons (c, Nil, Nil)
    | Cons (ct, left, right) ->
      if c < ct then Cons (ct, insert c left, right)
      else Cons (ct, left, insert c right)
  
  let rec delete c = function
    | Nil -> Nil
    | Cons (ct, left, right) ->
      if c = ct then
        match left, right with
        | Nil, Nil -> Nil
        | Nil, _ -> right
        | _, Nil -> left
        | _, _ ->
          let rec findMin = function
            | Cons (ct1, Nil, _) -> ct1
            | Cons (_, left, _) -> findMin left
            | _ -> failwith "impossible"
          in
          let min = findMin right in
          Cons (min, left, delete min right)
      else if c < ct then Cons (ct, delete c left, right)
      else Cons (ct, left, delete c right)
  
  let rec preorder = function
    | Nil -> []
    | Cons (c, left, right) -> c :: (preorder left) @ (preorder right)

  let rec inorder = function
    | Nil -> []
    | Cons (c, left, right) -> (inorder left) @ [c] @ (inorder right)

  let rec postorder = function
    | Nil -> []
    | Cons (c, left, right) -> (postorder left) @ (postorder right) @ [c]

  let rec listOfAllLeafs = function
    | Nil -> []
    | Cons (v, Nil, Nil) -> [v]
    | Cons (_, left, right) -> (listOfAllLeafs left) @ (listOfAllLeafs right)
end;;

let tree = BinaryTree.insert 5 BinaryTree.getEmpty;;
let tree = BinaryTree.insert 3 tree;;
let tree = BinaryTree.insert 7 tree;;
let _l1 = BinaryTree.preorder tree;;
let _l2 = BinaryTree.inorder tree;;
let _l3 = BinaryTree.postorder tree;;
let tree = BinaryTree.delete 5 tree;;
let _l4 = BinaryTree.listOfAllLeafs tree;;

module type CoordType = sig
  type t
  val create : float -> t
  val getVal : t -> float
end;;

module FloatCoord : CoordType = struct
  type t = float
  let create x = x
  let getVal x = x
end;;

module IntCoord : CoordType = struct
  type t = int
  let create x = int_of_float x
  let getVal x = float_of_int x
end;;

module MakePoint (Coord : CoordType) : Point3DType = struct
  type point = { x: Coord.t; y: Coord.t; z: Coord.t }

  let create x y z = { x = Coord.create x; y = Coord.create y; z = Coord.create z}

  let getX p = Coord.getVal p.x
  let getY p = Coord.getVal p.y
  let getZ p = Coord.getVal p.z

  let distance p1 p2 = sqrt((Coord.getVal p1.x -. Coord.getVal p2.x) ** 2. +. (Coord.getVal p1.y -. Coord.getVal p2.y) ** 2. +. (Coord.getVal p1.z -. Coord.getVal p2.z) ** 2.)
end;;

module FloatPoint = MakePoint(FloatCoord);;
module IntPoint = MakePoint(IntCoord);;

let floatPoint = FloatPoint.create 1. 2. 3.;;
let floatPoint2 = FloatPoint.create 1. 2. 4.;;
let distance = FloatPoint.distance floatPoint floatPoint2;;
let() = Printf.printf "distance: %f\n" distance;;

module type ShiftType = sig
  val getX : float
  val getY : float
  val getZ : float
end;;

module Shift : ShiftType = struct
  let getX = 2.
  let getY = 3.
  let getZ = 4.
end;;

module Translate_Point (Point : Point3DType) (Sh: ShiftType) : Point3DType = struct
  type point = Point.point

  let create x y z = Point.create (Sh.getX +. x) (Sh.getY +. y) (Sh.getZ +. z)

  let getX p = Point.getX p
  let getY p = Point.getY p
  let getZ p = Point.getZ p
  let distance p1 p2 = Point.distance p1 p2
end;;

module Translate_Segment (Seg : SegmentType) (Sh: ShiftType) : SegmentType = struct
  type segment = Seg.segment

  let createLine p1 p2 = Seg.createLine (Point.create ((Point.getX p1) +. Sh.getX) ((Point.getY p1) +. Sh.getY) ((Point.getZ p1) +. Sh.getZ)) 
  (Point.create ((Point.getX p2) +. Sh.getX) ((Point.getY p2) +. Sh.getY) ((Point.getZ p2) +. Sh.getZ))
  let length l = Seg.length l
end;;

module ShiftedPoint = Translate_Point (FloatPoint) (Shift);;
let _shiftedPoint = ShiftedPoint.create 1. 2. 3.;;
module ShiftedSegment = Translate_Segment (Segment) (Shift);;
let _shiftedSegment = ShiftedSegment.createLine (Point.create 1. 2. 3.) (Point.create 1. 2. 4.);;
let _length = ShiftedSegment.length _shiftedSegment;;
let _x = ShiftedPoint.getX (ShiftedPoint.create 1. 2. 3.);;