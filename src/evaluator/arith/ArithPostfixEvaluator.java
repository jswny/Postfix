package evaluator.arith;

import language.Operand;
import language.Operator;
import language.arith.DivOperator;
import language.arith.MultOperator;
import language.arith.PlusOperator;
import language.arith.SubOperator;
import language.arith.NegateOperator;
import parser.IllegalPostfixExpressionException;
import parser.PostfixParser.Type;
import parser.Token;
import parser.arith.ArithPostfixParser;
import stack.LinkedStack;
import stack.StackInterface;
import evaluator.PostfixEvaluator;

/**
 * An {@link ArithPostfixEvaluator} is a postfix evaluator over simple arithmetic expressions.
 *
 */
public class ArithPostfixEvaluator implements PostfixEvaluator<Integer> {

	private final StackInterface<Operand<Integer>> stack;
	
	LinkedStack<Operand<Integer>> operandStack;
	
	/**
	 * Constructs an {@link ArithPostfixEvaluator}
	 */
	public ArithPostfixEvaluator(){
		
		this.stack = null; //TODO initialize your LinkedStack
		
		operandStack = new LinkedStack<Operand<Integer>>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer evaluate(String expr) throws IllegalPostfixExpressionException {
		// TODO use all of the things you've built so far to 
		// implement the algorithm for postfix expression evaluation
		
		ArithPostfixParser parser = new ArithPostfixParser(expr);
		
		Operand<Integer> result = null;
		Operator<Integer> operator;
		Operand<Integer> operand0;
		Operand<Integer> operand1;
		int count = 0;
		
		Operator<Integer> oper;
		
		for (Token<Integer> token : parser) {
			Type type = token.getType();
			switch(type){ 
			case OPERAND:
				
				Operand<Integer> v = token.getOperand();
				
				operandStack.push(v);
				break;
			case OPERATOR:
				
				
				operator = token.getOperator();
				
				System.out.println("Operator: " + operator);
				
				if (operator.toString().equals("!")) {
					
					System.out.println("### Negate correctly recieved ###");
					oper = new NegateOperator();
					
					operator.setOperand(0, operandStack.pop());
					
					result = operator.performOperation();
					break;
				}
				
				if (count == 0) {
					operand0 = operandStack.pop();
					operand1 = operandStack.pop();
					
				} else {
					
					operand0 = result;
					operand1 = operandStack.pop(); 
				}
				
				count++;
				
				if (operator.toString().equals("+")) {
					
					oper =  new PlusOperator();
					
					operator.setOperand(0, operand0);
					operator.setOperand(1, operand1);
					
					result = operator.performOperation();
					
					break;
				}
				
				if (operator.toString().equals("-")) {
					System.out.println("Postfix Evaluator: Doing subtraction operation with: " + operand0 + " and " + operand1);
					oper = new SubOperator();
					
					operator.setOperand(1, operand0);
					operator.setOperand(0, operand1);
					
					result = operator.performOperation();
					break;
				}
				
				if (operator.toString().equals("/")) {
					oper = new DivOperator();
					
					operator.setOperand(0, operand0);
					operator.setOperand(1, operand1);
					
					result = operator.performOperation();
					break;
				}
				
				if (operator.toString().equals("*")) {
					oper = new MultOperator();
					
					operator.setOperand(0, operand0);
					operator.setOperand(1, operand1);
					
					result = operator.performOperation();
					break;
				}

				
				break;
			default:
				throw new IllegalStateException("Parser returned an invalid Type: " + type);
			}			
		}
		
		if (result == null) {
			return operandStack.pop().getValue();
		}
		return result.getValue();
	}

}
