package itcarlow.c00112726.vote.datastructures;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {

	private Node<T> head = null;
	private Node<T> tail = null;
	private int size = 0;
	
	/**
	 * Default constructor
	 */
	public LinkedList() {
	}
	
	/**
	 * Private static inner class used to create the Nodes of the list. This class should not be
	 * accessible from outside of the LinkedList.java class. It is a generic list, which means it can
	 * store data of any type. The static modifier ensures that this class cannot access member variables
	 * or functions from the LinkedList.java class.
	 * @author Michael Reid
	 *
	 * @param <T> The Generic Type that this node will store.
	 */
	private static class Node<T> {
		T data;
		Node<T> next;
		Node<T> previous;
		
		Node(T data, Node<T> next, Node<T> previous) {
			this.data = data;
			this.next = next;
			this.previous = previous;
		}
	}
	
	/**
	 * Adds new data at the end of the list.
	 * @param data The data to add to the list.
	 */
	public void add(T data) {
		// Create a node to point at the tail because we would rather avoid handling the tail explicitly, for safety.
		final Node<T> last = tail;
		// Create a new node, and assign the previous value to point to the tail.
		final Node<T> temp = new Node<T>(data, null, last);
		
		// We now reassign tail to the new node.
		tail = temp;
		
		// If tail was pointing to null, our list was empty. Therefore we are adding in the head node.
		// Otherwise, we assign the old tail to point to new tail.
		if(last == null) {
			head = temp;
		}
		else {
			last.next = temp;
		}	
		// Increase the size of the list by one.
        size++;
	}
	
	/**
	 * Utility method to add an array of data to the list.
	 * @param data The data array to add.
	 */
	public void addAllData(T[] data) {
		if(data == null) {
			throw new NullPointerException();
		}
		
		/*
		 * for(int i = 0; i < data.length; i++) {
		 * 		add(T[i]);
		 * }
		 */
		for(T t : data) {
			add(t);
		}
	}
	
	/**
	 * Removes data from the head of the list.
	 * @return The data at the head of the list.
	 */
	public T remove() {
		// Trying to remove an element from an empty list. We don't allow this.
		if(head == null) {
			throw new NoSuchElementException();
		}
		else {
			// We set up a node to point to the potential new head node.
			final Node<T> next = head.next;
			// Store the data in the head node, so we can return it later.
			final T data = head.data;
			
			// Clear the data in the head node. This allows the GC to dispose of the node for us.
			// Note: No need to say head.previous = null; as head Nodes will have this property already.
			head.data = null;
			head.next = null;
			
			// We node reassign head to the next node.
			head = next;
			
			// If next is null, we have removed the last node.
			// Otherwise, we have a new head node so we set previous to null.
			if(next == null) {
				tail = null;
			}
			else {
				next.previous = null;
			}
			// Decrease the size of the list by 1.
			size--;
			
			// Return the data from the original head node.
			return data;
		}
	}
	
	/**
	 * Removes data from a particular index in the list.
	 * @param index The index to remove at.
	 * @return The data removed.
	 */
	public T remove(int index) {
		// If the list is empty or the index is invalid, throw an exception.
		if(head == null) {
			throw new NoSuchElementException();
		}
		else if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		else {
			Node<T> current;
			// To speed things up, we will find the node containing the data by
			// searching from either the front or end of the list, depending on
			// the index;			
			if(index < size/2) {
				current = head;
				for(int i = 0; i < index; i++) {
					current = current.next;
				}
			}
			else {
				current = tail;
				for(int i = size - 1; i > index; i--) {
					current = current.previous;
				}
			}
			
			// We store the data, next, and previous values in the node.
			final T data = current.data;
			final Node<T> next = current.next;
			final Node<T> previous = current.previous;
			
			// If there is no next node, we are at the tail.
			// Otherwise we link the next node to the previous node.
			if(next == null) {
				tail = previous;
			}
			else {
				next.previous = previous;
			}

			// If there is no previous node, we are at the head.
			// Otherwise we link the previous node to the next node.
			if(previous == null) {
				head = next;
			}
			else {
				previous.next = next;
			}

			// Clear the data in the head node. This allows the GC to dispose of the node for us.
			current.data = null;
			current.next = null;
			current.previous = null;
			
			// Decrease the size of the list by 1.
			size--;
						
			// Return the data from the indexed node.
			return data;
		}
	}
	
	public void remove(Object obj) {
		// If the list is empty or the index is invalid, throw an exception.
		if(head == null) {
			throw new NoSuchElementException();
		}
		else {
			Node<T> current;		
			if(obj == null) {
				for(current = head; current.next != null; current = current.next) {
					if(current.data == null) {
						break;
					}
				}
			}
			else {
				for(current = head; current.next != null; current = current.next) {
					if(current.data.equals(obj)) {
						break;
					}
				}
			}
			
			// We store the next and previous values in the node.
			final Node<T> next = current.next;
			final Node<T> previous = current.previous;
			
			// If there is no next node, we are at the tail.
			// Otherwise we link the next node to the previous node.
			if(next == null) {
				tail = previous;
			}
			else {
				next.previous = previous;
			}
			// If there is no previous node, we are at the head.
			// Otherwise we link the previous node to the next node.
			if(previous == null) {
				head = next;
			}
			else {
				previous.next = next;
			}
			// Clear the data in the head node. This allows the GC to dispose of the node for us.
			current.data = null;
			current.next = null;
			current.previous = null;
			
			// Decrease the size of the list by 1.
			size--;
		}		
	}
	
	
	
	/**
	 * Returns data from the first list element.
	 * @return Data of the first list element.
	 */
	public T peek() {
		/*
		 * 	if(head == null) {
		 * 		return null;
		 * 	}
		 * 	else {
		 * 		return head.data;	
		 * 	}
		 */
		return head == null ? null : head.data;
	}
	
	public T get(int index) {
		// If the list is empty or the index is invalid, throw an exception.
		if(head == null) {
			throw new NoSuchElementException();
		}
		else if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		else {
			Node<T> current;
			// To speed things up, we will find the node containing the data by
			// searching from either the front or end of the list, depending on
			// the index;			
			if(index < size/2) {
				current = head;
				for(int i = 0; i < index; i++) {
					current = current.next;
				}
			}
			else {
				current = tail;
				for(int i = size - 1; i > index; i--) {
					current = current.previous;
				}
			}
			
			// We store the data, next, and previous values in the node.
			final T data = current.data;
						
			// Return the data from the indexed node.
			return data;
		}
	}
	
	/**
	 * Return the index of an object in our list. If the object is not found, returns -1.
	 * 
	 * @param obj The object we want to search for.
	 * @return Index of object searched for.
	 */
	public int indexOf(Object obj) {
		int index = 0;
		if(obj == null) {
			for(Node<T> current = head; current != null; current = current.next) {
				if(current.data == null) {
					return index;
				}
				index++;
			}
		}
		else {
			for(Node<T> current = head; current != null; current = current.next) {
				if(current.data.equals(obj)) {
					return index;
				}
				index++;
			}
		}		
		return -1;
	}
	
	/**
	 * Return the last index of an object in our list. If the object is not found, returns -1.
	 * 
	 * @param obj The object we want to search for.
	 * @return Last index of object searched for.
	 */
	public int lastIndexOf(Object obj) {
		int index = size - 1;
		if(obj == null) {
			for(Node<T> current = tail; current != null; current = current.previous) {
				if(current.data == null) {
					return index;
				}
				index--;
			}
		}
		else {
			for(Node<T> current = tail; current != null; current = current.previous) {
				if(current.data.equals(obj)) {
					return index;
				}
				index--;
			}
		}		
		return -1;
	}
	
	/**
	 * Returns whether or not the list contains a particular object.
	 * @param obj The object to check for in the list.
	 * @return true or false if list contains the object.
	 */
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}
	
	/**
	 * Returns the size of the list.
	 * @return Size of the list.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns whether or not the list is empty.
	 * @return true or false if the list is empty.
	 */
	public boolean isEmpty() {
		return size > 0;
	}
	
	/**
	 * Returns a copy of the list.
	 * @return A copy of the list.
	 */
	public LinkedList<T> getCopy() {		
		LinkedList<T> list = new LinkedList<T>();
		
		// Traverse through each element and add it to the new list.
		for(Node<T> current = head; current != null; current = current.next) {
			final T data = current.data;
			list.add(data);
		}
		
		return list;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		for(Node<T> current = head; current != null; current = current.next) {
			result += "[" + current.data + "]-> ";
		}
		
		return result;
	}

	@Override
	public LinkedListIterator iterator() {
		return new LinkedListIterator();
	}
	
	private class LinkedListIterator implements ListIterator<T> {
		
		private Node<T> current = head;
		private int currentIndex = 0;
		
		public boolean hasNext() {		
			return currentIndex < size;
		}
		
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			T data = current.data;
			current = current.next;
			currentIndex++;
			return data;
		}

		public boolean hasPrevious() {			
			return currentIndex > 0;
		}

		public T previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			T data = current.data;
			current = current.previous;
			currentIndex--;
			return data;
		}

		@Override
		public int nextIndex() {
			return currentIndex;
		}

		@Override
		public int previousIndex() {
			return currentIndex - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(T e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(T e) {
			throw new UnsupportedOperationException();
		}
		
	}
}
