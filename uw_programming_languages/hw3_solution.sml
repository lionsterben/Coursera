(* Coursera Programming Languages, Homework 3, Provided Code *)

exception NoAnswer

datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu

fun g f1 f2 p =
    let 
	val r = g f1 f2 
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end

(**** for the challenge problem only ****)

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string

(**** you can put all your code here ****)
val only_capitals = List.filter (fn x => Char.isUpper(String.sub(x,0)))

val longest_string1 =  foldl (fn (x,y) => if String.size x > String.size y then x else y) "" 

val longest_string2 = foldl (fn (x,y) => if String.size x >= String.size y then x else y) "" 

fun longest_string_helper f xs = 
		case xs of
			[] => ""
			|x::xs' => let val tmp = longest_string_helper f xs' 
				in 
					if f(String.size x,String.size tmp) then x else tmp
				end

val longest_string3  = longest_string_helper (fn(x,y) => x >= y) 

val longest_string4 = longest_string_helper (fn(x,y) => x > y) 

val longest_capitalized = (longest_string_helper (fn(x,y) => x >= y)) o only_capitals

val  rev_string = implode o rev o explode

fun first_answer f xs = case xs of
		[] => raise NoAnswer
		|x::xs' => case f x of
			SOME v => v
			|NONE => first_answer f xs'

fun all_answers f xs = 
	let fun helper acc = foldl (fn (x,(m,n)) => case f x of SOME v => (m@v,n andalso true)  | NONE=>([],false))  acc xs		
	in
		case (xs,helper ([],true)) of 
			([],_) => SOME []
			|(_,(x,true)) => SOME(x)
			| _ => NONE	
	end

val count_wildcards = g (fn() => 1) (fn y => 0) 

val count_wild_and_variable_lengths = g (fn() => 1) (fn y => String.size y)

fun count_some_var (s,p) = g (fn() => 0) (fn y => if y = s then 1 else 0) p

fun check_pat p = 
	let 
		fun helper_1 p = case p of
			Wildcard => []
			|Variable x => [x]
			|TupleP ps => List.foldl (fn (p,i) => (helper_1 p) @ i) [] ps
			|ConstructorP(_,x) => helper_1 x
			|_ => []
		fun helper_2 xs = case xs of
			[] => true
			|x::xs' =>  not(List.exists (fn y => y = x) xs') andalso helper_2 xs'
	in
		helper_2(helper_1(p))
	end

fun match (va,pa) = case (va,pa) of
	(Const a,ConstP b) => if a = b then SOME [] else NONE
	|(Unit,UnitP) => SOME []
	|(Tuple a,TupleP b) => if List.length a <> List.length b then NONE else
		all_answers (fn (x,y) => match(x,y)) (ListPair.zip(a,b))
	|(_,Variable b) => SOME [(b,va)]
	|(Constructor(m,a),ConstructorP(n,b)) => if m = n then match(a,b) else NONE
	|(_,Wildcard) => SOME []
	|_ => NONE

fun first_match va pa_list = 
	SOME (first_answer (fn x => match(va,x)) pa_list)
	handle NoAnswer => NONE

