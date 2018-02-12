fun is_older (xs : int*int*int, ys : int*int*int) = 
	if #1 xs  <> #1 ys
	then if #1 xs < #1 ys
		then true
		else false
	else if #2 xs <> #2 ys
		then if #2 xs < #2 ys
			then true
			else false
		else if #3 xs <> #3 ys
			then if #3 xs < #3 ys
				then true
				else false
			else false

fun number_in_month(date_list : (int*int*int) list, month : int) = 
	 if null date_list
	 then 0
	 else if #2(hd date_list) = month
	 	then 1 + number_in_month(tl date_list, month)
	 	else number_in_month(tl date_list, month)

fun number_in_months(date_list : (int*int*int) list, month_list : int list) = 
	if null month_list
	then 0
	else number_in_month(date_list, hd month_list) + number_in_months(date_list, tl month_list)

fun dates_in_month(date_list : (int*int*int) list, month : int) = 
	if null date_list
	then []
	else if #2 (hd date_list) = month
		then hd date_list :: dates_in_month(tl date_list, month)
		else dates_in_month(tl date_list, month)

fun dates_in_months(date_list : (int*int*int) list, month_list : int list) = 
	if null month_list
	then []
	else dates_in_month(date_list, hd month_list) @ dates_in_months(date_list, tl month_list)

fun get_nth(string_list : string list, num : int) = 
	if num = 1
	then hd string_list
	else get_nth(tl string_list, num-1)

fun date_to_string(date : int*int*int) = 
	let
		val month_string = ["January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December"]
		val month = get_nth(month_string, #2 date)
	in
		month^" "^Int.toString(#3 date)^", "^Int.toString(#1 date)
	end

fun number_before_reaching_sum(sum : int, all : int list) = 
	if sum-hd(all) <= 0 
	then 0
	else 1 + number_before_reaching_sum(sum-hd(all),tl(all))

fun what_month(day : int) = 
	let
		val month_day = [31,28,31,30,31,30,31,31,30,31,30,31]
	in
		1+number_before_reaching_sum(day,month_day)
	end

fun month_range(day1 : int, day2 : int) = 
	if day1 > day2
	then []
	else what_month day1 :: month_range(day1+1,day2)

fun oldest(date_list : (int*int*int) list) = 
	if null date_list
	then NONE
	else 
		let val tmp = oldest(tl date_list)
		in
			if  isSome tmp andalso is_older(valOf tmp,hd date_list)
			then tmp
			else SOME(hd date_list)
		end

fun is_there(day_list : int list,day : int) = 
	if null day_list
	then false
	else if hd(day_list) = day
		then true
		else is_there(tl(day_list),day)

fun unique_list(day_list : int list) = 
	if null day_list
	then []
	else 
		let val tmp = unique_list(tl(day_list))
		in
			if is_there(tmp, hd day_list)
			then tmp
			else hd(day_list)::tmp
		end


fun number_in_months_challenge(date_list : (int*int*int) list, month_list : int list) = 
	number_in_months(date_list,unique_list(month_list))

fun dates_in_months_challenge(date_list : (int*int*int) list, month_list : int list) = 
	dates_in_months(date_list,unique_list(month_list))

fun is_leapyear(year : int) = 
	(year mod 400 = 0) orelse ((year mod 4 =0) andalso (year mod 100 <> 0))

fun get_int(int_list : int list, num) = 
	if num = 1
	then hd int_list
	else get_int(tl(int_list),num-1)

fun reasonable_date(date : int*int*int) = 
	let
		val year = #1 date
		val month = #2 date
		val day = #3 date
		val regular_month_day = [31,28,31,30,31,30,31,31,30,31,30,31]
		val leap_month_day = [31,29,31,30,31,30,31,31,30,31,30,31]
	in
		if year > 0
		then if month > 0 andalso month < 13
			then if is_leapyear year
				then if day >0 andalso day <= get_int(leap_month_day,month)
					then true
					else false
				else if day > 0 andalso day <= get_int(regular_month_day,month)
					then true
					else false
			else false
		else false
	end



