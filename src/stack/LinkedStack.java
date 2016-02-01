package stack;

import stack.StackUnderflowException;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure
 * to allow for unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {
	
	LLNode<T> head;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
    		throw new StackUnderflowException();
    	}
		
		T temp = head.getData();
		head = head.getNext();
		
		return temp;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T top() throws StackUnderflowException {
		return head.getData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		
		if (isEmpty()) {
			return 0;
		}
		
		LLNode<T> currNode = head;
		int count = 1;
		
		System.out.println(" 1 ");
		
		while (currNode.getNext() != null) {
			count++;
			currNode = currNode.getNext();
		}
		
		return count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void push(T elem) {
		
		
		LLNode<T> newNode = new LLNode<T>(elem);
		
		newNode.setNext(head);
		
		head = newNode;
	}

}
