package assignment2;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		
		String line10 = console.nextLine();
		String line20 = console.nextLine();
		String line30 = console.nextLine();
		String line4 = console.nextLine();
		console.close();
		
		String line1 = formatLine(line10);//formatting the line using the formatLine method
		String line2 = formatLine(line20);//formatting the line using the formatLine method
		String line3 = formatLine(line30);//formatting the line using the formatLine method
		String line = formatInput(line4);// formatted input using formatInput method
		
		line = replaceVaribale(line1,line);//replacing variable by its a numeric value in the fourth line(if present) using method replaceVaribale
		line = replaceVaribale(line2,line);//replacing variable by its a numeric value in the fourth line(if present) using method replaceVaribale
		line = replaceVaribale(line3,line);//replacing variable by its a numeric value in the fourth line(if present) using method replaceVaribale
		
		//checking the fourth line for all parentheses and performing operations inside of them using  parentheses method
		line = parentheses(line);
		
		//checking the fourth line for multiplication and division operations using the method checkForArithmeticOper
		line = checkForArithmeticOper(line,'*','/');
		
		//checking the fourth line for summation and subtraction operations using the method checkForArithmeticOper
		line = checkForArithmeticOper(line,'+','-');
		
		// printing the result in the console without the the semicolon
		System.out.println(line.replace(';', ' '));
		
		
	}
	//This method is used to find parentheses and perform operations inside of them
	public static String parentheses(String line) {
		//using while loop to find all the parentheses
		while(line.contains(")")){
			//taking the first inner parentheses and storing these expression in the new variable toCal
			String s = line.substring(0, line.indexOf(")")+1) ;
			String toCalc = s.substring(s.lastIndexOf("("));
			//checking for multiplication and subtraction inside the parentheses, and performing all the operations that exist by the order of precedence
			String expression = checkForArithmeticOper(toCalc,'*','/');
			expression = checkForArithmeticOper(expression,'+','-');
			//the result inside the brackets is stored in the expression variable
			expression = expression.substring(expression.indexOf("(")+1, expression.length()-1);
			//replacing the parentheses with some arithmetic operations by the result of these operation(s) 
			line = line.replace(toCalc, expression);
			//formatting the line
			line = formatInput(line);
			}
		return line;
		
	}
	
	//this method is used to replace all the variables in the fourth line by its numerical values.
	public static String replaceVaribale(String line1, String line) {
		//the name variable is used to store the name of the declared variable
		String name = line1.substring(line1.indexOf(" ") + 1, line1.indexOf("=") - 1);
		//the number variable stores the number assigned to the variable
		String number = line1.substring(line1.indexOf("=") + 2, line1.indexOf(";"));
		//using if else statement to determine if the it is an integer or double value
		if(line1.substring(0,3).equals("int")) // if it is an integer
		{
			if(line.contains(name)) {
				line = line.replace(name, number);
			}
		}else //  it must be a double variable
		{
			if(number.contains(".")) {//if the double  contained a dot 
				//replace the variable in the last line by its numeric value
				line = line.replace(name, number);
			}else {// the double did not have a dot 
				//add a dot to the number
				number = number + ".";
				//replace the variable in the last line by its numeric value
				line = line.replace(name, number);
			}
				
		}
		return line;
	}
	
	//This method is used to format one of the first three lines which has a variable declaration and initialization
	public static String formatLine(String line1) {
		Scanner exp = new Scanner(line1);
		String variableLine = "";
		//writing all tokens without spaces using a while loop
		while(exp.hasNext()) {
				variableLine += exp.next();
		}
		exp.close();
		variableLine = variableLine.toLowerCase();
		//replacing the "int", "double" and "=" by their spaced versions
		variableLine = variableLine.replace("int", "int ");
		variableLine = variableLine.replace("double", "double ");
		variableLine = variableLine.replace("=", " = ");
	return variableLine;
			
	}
	
	//this method is used to format the fourth line of input
	public static String formatInput(String line4) {
		Scanner exp = new Scanner(line4);
		String expression = "";
		// writing all tokens without spaces using a while loop
		while(exp.hasNext()) {
				expression += exp.next();
		}
		exp.close();
		String line = "";
		for(int i = 0; i < expression.length(); i++) {
			char a = expression.charAt(i);
			//checking every character of the expression and adding spaces before and after the characters,which are not numbers
			if((a == '+') || (a == '-') || (a == '/') || (a == '*') || (a=='(') || (a==')')) {
				line += " " + a + " ";
				}
			else {
					line += a;
				}
		}
		return line;
	}
	
	//this method finds the number before the specified operator
	public static String numberBefore(String line, int indexOfOperator) {
		//the num variable stores the substring before that operator
		String num = line.substring(0, indexOfOperator-1);
		//if num has  space inside of it, it would cut the string, and return the substring without spaces, which is our number
		if(num.contains(" ")) {
			num = num.substring(num.lastIndexOf(" ")+1);
		}
		return num;
	}
	
	//this method finds the number after the specified operator
	public static String numberAfter(String line, int indexOfOperator) {
		//the num variable stores the substring after that operator
		String num = line.substring(indexOfOperator + 2);
		if(num.contains(" ")) {//if num has  space inside of it, it would take the string, and return the substring without spaces, which is our number
			num = num.substring(0, num.indexOf(" ") );
		}else {// num does not have any spaces but has a semicolon which is cut off the string by the substring method, and we get our number
			num = num.substring(0, num.indexOf(";") );
		}
		
		return num;
		}

	//this method is used to evaluate the specified arithmetical operation inside the line
	public static String evaluateOperation(String line, int indexOfOperator, char operator) {
		
		String result = "";
		//storing the number before the certain operator in the number1 variable
		String number1 = numberBefore(line,line.indexOf(operator));
		//storing the number after the certain operator in the number2 variable
		String number2 = numberAfter(line, line.indexOf(operator));
		//if number1 and number2 did not have dots 
		if(!number1.contains(".") && !number2.contains(".")){
			//get integer numeric value of number1 and store it in the num1 variable
			int num1 = Integer.parseInt(number1);
			//get integer numeric value of number2 and store it in the num2 variable
			int num2 = Integer.parseInt(number2);
			//store the result of the operation between num1 and num2 as a string in the result variable
			result = Integer.toString(returnIntResult(num1,num2,operator));
		}
		else{//one of the number1 or number2 or both of them might have dots
			//get double numeric value of number1 and store it in the num1 variable
			double num1 = Double.parseDouble(number1);
			//get double numeric value of number2 and store it in the num2 variable
			double num2 = Double.parseDouble(number2);
			//store the result of the operation between num1 and num2 as a string in the result variable
			result = Double.toString(returnDoubleResult(num1, num2, operator));
		}
		//replacing the number1, operator and number 2 with the result of the operation between them
		line = line.replace(number1 + " " + operator + " " + number2, result);
		
		return line;
	}
	
	// This method is used to return an integer result of the operation between two integer numbers
	public static int returnIntResult(int num1, int num2, char operator) {
		int result = 0;
		//using switch to perform the operation between two numbers depending on the type of the operator
		switch(operator) {
		case '+' :
			result = num1 + num2;
			break;
		case '-' :
			result = num1 - num2;
			break;
		case '*' :
			result = num1 * num2;
			break;
		case '/' :
			result = num1 / num2;
			break;
		}
		
		return result;
	}
	
	// This method is used to return a double result of the operation between two double numbers
	public static double returnDoubleResult(double num1, double num2, char operator) {
		double result = 0;
		//using switch to perform the operation between two numbers depending on the type of the operator
		switch(operator) {
		case '+' :
			result = num1 + num2;
			break;
		case '-' :
			result = num1 - num2;
			break;
		case '*' :
			result = num1 * num2;
			break;
		case '/' :
			result = num1 / num2;
			break;
		}
		return result	;
	}
	
	//This method checks and performs arithmetic operations of either (multiplication and division) or (summation and subtraction)
	public static String checkForArithmeticOper(String line, char a, char b) {
		//using while loop to perform the operations while "a" and "b" operators exist
		while( line.contains(""+a) || line.contains(""+b) ) {
			//if the line has both of the operators
			if( line.contains(""+a) && line.contains(""+b) ) {
				//storing index of the first operator
				int index1 = line.indexOf(a);
				//storing index of the second operator
				int index2 = line.indexOf(b);
				//while loop that checks while the the index1 is less than index2
				while(index1 < index2) {
					//perform the operation which corresponds to operator "a"
					line = evaluateOperation(line, index1, a);
					//if both of the operators exist
					if(line.contains(""+a) && line.contains(""+b)) {
						//assign new values to index1 and index2
						index1 = line.indexOf(a);
						index2 = line.indexOf(b);
						continue;
						}
						else {//one of the operators or both of them does not  exist
							break;
						}
				}
				//while loop that checks while the index1 is greater than index2
				while(index1 > index2) {
					//perform the operation which corresponds to operator "b"
					line = evaluateOperation(line, line.indexOf(b), b);
					//if both of the operators exist
					if(line.contains(""+a) && line.contains(""+b)) {
						//assign new values to index1 and index2
						index1 = line.indexOf(a);
						index2 = line.indexOf(b);
						continue;
					}
					else {//one of the operators or both of them does not  exist
						break;
					}
				}
		    // if line has "a" operator, but not "b" operator
			}else if(line.contains(""+a) && !(line.contains(""+b))){
				//perform the operation which corresponds to operator "a"
				line = evaluateOperation(line, line.indexOf(a), a);
			}else {// line must have an operator "b", but not "a"
				//perform the operation which corresponds to operator "b"
				line = evaluateOperation(line, line.indexOf(b), b);
			}
		}
		return line;
	}	
	


}
