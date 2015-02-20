package itcarlow.c00112726.vote.datastructures;

import java.util.NoSuchElementException;

public class MinHeap<K extends Comparable<? super K>,V> {
		
	private static final int DEFAULT_SIZE = 16;
	private Entry<K,V>[] heap;
	private int size = 0;
	
	/**
	 * Default constructor.
	 */
	public MinHeap() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * Constructor with an initial heap size.
	 * @param initialSize
	 */
	@SuppressWarnings("unchecked")
	public MinHeap(int initialSize) {
		heap = (Entry<K,V>[])new Entry[DEFAULT_SIZE];
	}
	
	/**
	 * If the heap is full, expands it, so we can keep adding entries to it.
	 */
	private void expandHeap() {
		// Creates a new heap with a size 50% larger than the current heap.
		@SuppressWarnings("unchecked")
		final Entry<K,V>[] newHeap = (Entry<K,V>[])new Entry[size + (size>>1)];
		// Copy everything in to the new heap.
		for(int i = 0; i < heap.length; i++) {
			newHeap[i] = heap[i];
		}
		// Assign our bigger heap.
		heap = newHeap;
	}
	
	/**
	 * Add a new entry to the heap. Expands heap if required.
	 * @param key Entry key
	 * @param value Entry value
	 */
	public void add(K key, V value) {
		if(size == heap.length) {
			expandHeap();
		}
		
		final Entry<K,V> pair = new Entry<K,V>(key, value);
        heap[size] = pair;
        
        upheap(size);
        
        size++;
	}
	
	/**
	 * Inner recursive method to upheap.
	 * @param current Position we are currently moving around.
	 */
	private void upheap(int current) {
		// If we haven't reached the root.
		if (current != 0) {
			// Parent of current entry.
	        int parentIndex = parentOf(current);
	        // If parent > current child, swap them, then upheap the new parent
	        if(heap[parentIndex].key.compareTo(heap[current].key) > 0) {
		        swap(parentIndex, current);
		        upheap(parentIndex);
	        }
		}
		return;
	}
	
	/**
	 * Remove the 1st entry in the heap, ie the smallest value.
	 * @return
	 */
	public V remove() {
		if(size == 0) {
			throw new NoSuchElementException();
		}
		// Store root entry value.
		final V value = heap[0].value;
		size--;
		// Stick last entry as root.
		heap[0] = heap[size];
		// Remove old root from heap.
        heap[size] = null;
        
        if (size > 0) {
        	downheap(0);
        }
        
		return value;
	}
	
	/**
	 * Inner recursive method to downheap.
	 * @param current Position we are currently moving around.
	 */
	private void downheap(int current) {
		// Smaller of the two child entries.
		int min;
		// Child entries.
		int leftChild = leftChildOf(current);
		int rightChild = rightChildOf(current);
		        
		// Checks if the children indexes are valid indexes
		if (rightChild >= size) {
			if (leftChild >= size) {
				// Reached the end of the heap
				return;
			}
			else  {
				min = leftChild; 	// Right child can be invalid index while left child is valid.
									// If only 1 child, the min will be left child.
			}			              		
		} 
		else {
			// find smallest of the 2 child entries.
			if (heap[leftChild].key.compareTo(heap[rightChild].key) <= 0) {
				min = leftChild;
			}
			else {
				min = rightChild;
			}
		}
		
		// Parent > smallest child, swap them.
		if (heap[current].key.compareTo( heap[min].key) > 0) {
			swap(min, current);
			// Downheap with new parent entry.
			downheap(min);
		}
	}
	
	/**
	 * Utility method. Swaps two entries.
	 * @param i Entry 1
	 * @param j Entry 2
	 */
	private void swap(int i, int j) {
		final Entry<K,V> temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	/**
	 * Returns the value of the root entry.
	 * @return Root entry value
	 */
	public V peek() {
		if(size == 0) {
			throw new NoSuchElementException();
		}
		return heap[0].value;
	}
	
	/**
	 * Returns the amount of elements stored in the heap.
	 * @return Amount of elements stored in the heap
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Return if the heap is empty or not.
	 * @return true or false if heap is empty
	 */
	public boolean empty() {
		return size == 0;
	}
	
	/**
	 * Index of the left child.
	 * @param index Parent index
	 * @return Left child index
	 */
	private int leftChildOf(int index) {
		return 2 * index + 1;
	}
	
	/**
	 * Index of the right child.
	 * @param index Parent index
	 * @return Right child index
	 */
	private int rightChildOf(int index) {
		return 2 * index + 2;
	}
			
	/**
	 * Returns whether or not an index has no children.
	 * @param index Index to check
	 * @return true or false if leaf
	 */
	@SuppressWarnings("unused")
	private boolean isLeaf(int index)  {
		return  index < size && 2 * index + 1 > size;
	}
	
	/**
	 * Index of a parent of an entry.
	 * @param index The child index to check
	 * @return Parent index
	 */
	private int parentOf(int index) {
		return (index - 1) / 2;
	} 
	
	/**
	 * Checks if an index is the root entry.
	 * @param index Index to check
	 * @return true or false if root
	 */
	@SuppressWarnings("unused")
	private boolean isRoot(int index) {
		return index == 0;
	}
	
	/**
	 * Private static inner class used to create the Entries of the heap. This class should not be
	 * accessible from outside of the MinHeap.java class. It is a generic heap, which means it can
	 * store Key/Value of any type. The static modifier ensures that this class cannot access member variables
	 * or functions from the MinHeap.java class.
	 * @author Michael Reid
	 *
	 * @param <K, V> The Generic Types that this Entry will store.
	 */
	private static class Entry<K extends Comparable<? super K>,V> {
		K key;
		V value;
		
		Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
}
