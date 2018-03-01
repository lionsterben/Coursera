(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)
fun all_except_option (x,xs) = 
	case xs of
		[] => NONE
		|y::ys => case all_except_option(x,ys) of
 			    NONE => if same_string(y,x) then SOME(ys) else NONE
				|SOME str => if same_string(y,x) then SOME(str) else SOME(y::str)

fun get_substitutions1 (xs,x) = 
	case xs of
		[] => []
		|y::ys => case all_except_option(x,y) of
			 NONE => get_substitutions1(ys,x)
			 |SOME str => str @ get_substitutions1(ys,x)

fun get_substitutions2 (xs,x) = 
	let fun aux(xs,x,acc) = 
		case xs of
			[] => acc
			|y::ys => case all_except_option(x,y) of
				NONE => aux(ys,x,acc)
				|SOME str => aux(ys,x,acc@str)
	in
		aux(xs,x,[])
	end

fun similar_names (xs,{first = x,middle = y,last = z}) = 
	let fun helper (name,{first = x,middle = y,last = z}) = 
		case name of
			[] => []
			|f::zs => {first = f,middle = y,last = z} :: helper(zs,{first = x,middle = y,last = z})
	in
		helper(x::get_substitutions2(xs,x),{first = x,middle = y,last = z})
	end



(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove

(* put your solutions for problem 2 here *)
fun card_color x = 
	case x of
		(Spades,_) => Black
		|(Clubs,_) => Black
		|_ => Red 

fun card_value (suit,rank) =
	case rank of
		Num y => y
		|Ace => 11
		|_ => 10

fun remove_card (cs,c,e) = 
	let fun helper (x,xs) = 
			case xs of
				[] => NONE
				|y::ys => case helper(x,ys) of
		 			    NONE => if y = x then SOME(ys) else NONE
						|SOME str => if y = x then SOME(str) else SOME(y::str)
	in
		case helper(c,cs) of
			NONE => raise e
			|SOME lst => lst
	end

fun all_same_color cs = 
	case cs of
		[] => true
		|x::[] => true
		|m::(n::z) => (card_color m = card_color n andalso all_same_color (n::z))

fun sum_cards cs = 
	let fun helper (cs,acc) = 
		case cs of
			[] => acc
			|x::xs => helper(xs,acc + card_value(x))
	in
		helper(cs,0)
	end

fun score (cs,goal) = 
	let val sc = sum_cards cs
		val pr = if (sc-goal) > 0 then 3*(sc-goal) else (goal - sc)
	in 
		if all_same_color(cs) then pr div 2 else pr
	end

fun officiate (cs,ms,goal) = 
	let fun helper (held_card,remain_card,move) = 
		case (held_card,remain_card,move) of
			(_,_,[]) => score(held_card,goal)
			|(_,_,x::xs) => case x of
				Discard c => helper(remove_card(held_card,c,IllegalMove),remain_card,xs)
				|Draw => case remain_card of
					[] => score(held_card,goal)
					|y::ys => if sum_cards(y::held_card) > goal then score(y::held_card,goal) else
						helper(y::held_card,ys,xs)
	in
		helper([],cs,ms)
	end

fun ace_count cs = 
	case cs of
		[] => 0
		|(a,b) :: xs => if b = Ace then 1 + ace_count xs else ace_count xs

(*todo challenge problen*)





		





	

