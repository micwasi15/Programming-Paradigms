let rec lastElem list = if list = [] then None else if List.tl list = [] then Option.some(List.hd list) else lastElem (List.tl list);;
let rec lastTwoElem list = if list = [] || (List.tl list) = [] then None else if List.tl (List.tl list) = [] then Option.some(list) else lastTwoElem (List.tl list);;
let rec listLen list = if list = [] then 0 else 1 + listLen (List.tl list);;
let rec reverseList list = if list = [] then [] else (reverseList (List.tl list)) @ [(List.hd list)];;
let isPalindrome list = if list = (reverseList list) then true else false;;

let rec remEl list elem = if list = [] then [] else if (List.hd list) = elem then (remEl (List.tl list) elem) else (List.hd list)::(remEl (List.tl list) elem);;
let rec remDupl list = if list = [] then [] else (List.hd list)::(remDupl(remEl (List.tl list) (List.hd list)));;

let rec removeEverySecEle list = if list = [] then [] else 
  if List.tl list = [] then list else
  (List.hd list) :: (removeEverySecEle (List.tl (List.tl list)));;

let rec isPrimeHelper x y = if x <= y then true else if x mod y = 0 then false else isPrimeHelper x (y+2);;
let isPrime x = if x < 2 then false else if x mod 2 = 0 then true else isPrimeHelper x 3;;

let list1 = [1; 2; 3; 4; 5; 6; 7; 8];;
let list2 = [1; 2; 3; 2; 1];;
lastElem list1;;
lastTwoElem list1;;
listLen list1;;
reverseList list1;;
isPalindrome list1;;
isPalindrome list2;;
remDupl list2;;
removeEverySecEle list1;;
isPrime 3;;
isPrime 5;;
isPrime 111;;