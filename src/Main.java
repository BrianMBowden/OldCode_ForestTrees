///////////////////////////////////////////////////////////////////////////////
//NAME:         Brian Bowden
//DATE:         Dec 9, 2016
//COURSE/SEC:   Comp2631//001
//PROF:			Laura Marik
//ASSIGNMENT:	4
///////////////////////////////////////////////////////////////////////////////
//Purpose: Parses user input for a infix expression and passes back the post, 
//		   pre, and equivalent infix expressions as well as the validity and
//		   value of the expression.
//
//Details: After taking in some expression, the string is rearranged into post
//		   fix using the Shunting-Yard algorithm. The validity of the new string
//		   is tested, if the string is deemed valid, it gets put into an
//		   expression tree and the prefix, infix and postfix expressions are 
//		   printed out (using preorder, inorder and postorder tree traversals).
//		   The user is allowed to input variables instead of truth values for 
//		   the expression, however, in order to properly evaluate the expression
//		   the program will query the user for values. Any redundant variables 
//		   are saved in a hashmap so the user is not prompted more than once.
//
// 		   Truth Table: | value 1 | value 2 | evaluation
//				&		|	0	  |	   0	| 	0
//				&		|	1	  |    0    |   0
//				&		|	0     |    1    |   0
//				&       |	1	  |    1	|   1
//
//				|		|	0	  |	   0	|   0
//				|		|	1	  |	   0	|   1
//				|		|	0	  |	   1	|   1
//				|		|	1	  |	   1	|   1
//
//				^		|	0	  |	   0	|   0
//				^		|	1	  |	   0	|   1
//				^		|	0	  |	   1	|   1
//				^		|	1	  |	   1	|   0
//
//				!		|	0	  |	   -	|   1
//				!		|	1	  |	   -	|   0
//
///////////////////////////////////////////////////////////////////////////////
//Bugs:	none to be aware of
//
//Limitations: Does not incorporate phase 3 of this assignment, so the
//			   expression does not simplify first.
//			   This program will simply ignore any superfluous input and only 
//			   take into account relevant input.
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.Stack;

public class Main {

	static public Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String query = "Enter equation in infix form \n";
		String prefix =  "Prefix  : ";
		String infix =   "Infix   : ";
		String postfix = "Postfix : ";
		String evaluated = "Evaluated Result : ";
		String inv = "String entered is not valid, program will terminate";
		String evalT = "true";
		String evalF = "false";
		String input;
		boolean valid = true;
		boolean eval = false;
		
		System.out.println(query);
		input = sc.nextLine();
		input = delimAll(input);
		input = infixCheck(input);
		valid = postCheck(input);
		
		if (valid){
			System.out.println("string is valid \n");
			Tree evergreen = new Tree(input);
			System.out.print(prefix);
			evergreen.preorderPrint(evergreen.root);
			System.out.print('\n' + infix);
			evergreen.inorderPrint(evergreen.root);
			System.out.print('\n' + postfix);
			evergreen.postorderPrint(evergreen.root);
			System.out.println();
			eval = evergreen.evaluation(evergreen.root);
			System.out.print(evaluated);
			if (eval)
				System.out.println(evalT);
			else
				System.out.println(evalF);
		}
		else 
			System.out.println(inv);
		
		sc.close();
	}

	private static boolean postCheck(String prefix){
		boolean valid = true;
		int count = 0;
		int i = 0;
		char[] ch = {};
		
		ch = prefix.toCharArray();
		
		while (valid && (i < prefix.length())){
			//unary check
			if (ch[i] == '!'){
				count--;
				if (count < 0)
					valid = false;
				else
					count++;
			}
			//binary check
			else if ((ch[i] == '&')||(ch[i] == '|')||(ch[i] == '^')){
				count -= 2;
				if (count < 0)
					valid = false;
				else 
					count++;
			}
			else 
				count++;
			i++;
		}
		
		if (valid){
			if (count != 1)
				valid = false;
		}
		
		return valid;
	}
	
	private static String infixCheck(String infix){
		boolean valid = true;
		Stack<Character> st = new Stack<Character>();
		ArrayDeque<Character> ad = new ArrayDeque<Character>();
		char[] ch = {};
		String polish;
		ch = infix.toCharArray();
		
		for(int i = 0; (i < infix.length()) && (valid); i++){
			if ((ch[i] <= 'Z' && ch[i] >= 'A')||
				(ch[i] <= 'z' && ch[i] >= 'a')||
				(ch[i] <= '9' && ch[i] >= '0')){
				ad.offer(ch[i]);
			}
			else if (ch[i] == '(')
				st.push(ch[i]);
			else if ((ch[i] == '!')||(ch[i] == '&')||
					 (ch[i] == '|')||(ch[i] == '^')){
						if(st.isEmpty()){
							st.push(ch[i]);
						}
						else{
							if (checkPrec(st.peek()) > checkPrec(ch[i])){
								ad.offer(st.pop());
								st.push(ch[i]);
							}
							else{
								st.push(ch[i]);
							}	
						}	
					}
			else if (ch[i] == ')'){
				if (st.peek() == '(')
					valid = false;
				while (!st.isEmpty() && valid && st.peek() != '('){
					ad.offer(st.pop());
				}
			}
			else 
				valid = false;
		}
		while (!st.isEmpty() && st.peek() != ')' && st.peek() != '('){
			ad.offer(st.pop());
		}
		polish = ad.toString();
		
		polish = polish.replace("[", "");
		polish = polish.replace("]", "");
		polish = polish.replace(",", "");
		polish = polish.replace(" ", "");
		return polish;
	}
	
	private static int checkPrec(char c){
		int p = 0;
		switch(p){
		case '|':
			p = 1;
			break;
		case '^':
			p = 2;
			break;
		case '&':
			p = 3;
			break;
		case '!':
			p = 4;
			break;
		}
		return p;
	}
	private static String delimAll(String st){
		String none = "";
		String blah = none;
		char[] ch = {};
		ch = st.toCharArray();
		
		for (int i = 0; i < st.length(); i++){
			if ((ch[i] != '&')&&(ch[i] != '|')&&(ch[i] != '^')
					&&(ch[i] != '!')&&(ch[i] != '(')&&(ch[i] != ')')){
				if (ch[i] < 'A' || ch[i] > 'Z'){
					if (ch[i] < 'a' || ch[i] > 'z'){
						if (ch[i] < '0' || ch[i] > '9'){
							ch[i] = ' ';	
						}
					}
				}
			}
		}
		for (int i = 0; i < ch.length; i++)
			blah += ch[i];
		st = blah;
		st = st.replace("[", none);
		st = st.replace("]", none);
		st = st.replace(",", none);
		st = st.replace(" ", none);
		st = st.replace("\n", none);
		st = st.replace("\t", none);
		return st;
	}
}
