package sxa190016;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import sxa190016.RedBlackTree.Entry;

import java.util.Queue;

/**
 * @author sxa190016
 * @author axs190140
 * @author epm180002
 * @author nxs190026
 * @version 1.0 Binary Search Tree: Implement add, remove & contains methods of generic Binary Search Tree
 * 								by extending the Comparable class.
 * @param <T>	Generic variable for storing objects of type T
 */
@SuppressWarnings("unused")
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {

	/**
	 * @author sxa190016
	 * @author axs190140
	 * @author epm180002
	 * @author nxs190026
	 * @version 1.0 Entry: Static class for storing the nodes of the tree.
	 * @param <T>	Generic variable for storing objects of type T
	 */
	static class Entry<T> {
		/**
		 * Store the generic value of the node
		 */
		T element;
		
		/**
		 * Reference to the left child
		 */
		Entry<T> left;
		
		/**
		 * Reference to the right child
		 */
		Entry<T> right;

		/**
		 * Constructor to initialize the nodes
		 * 
		 * @param x		The value of the node
		 * @param left	Left child of the node
		 * @param right	Right child of the node
		 */
		public Entry(T x, Entry<T> left, Entry<T> right) {
			this.element = x;
			this.left = left;
			this.right = right;
		}
	}

	/**
	 * Stores the root of the BST
	 */
	Entry<T> root;
	
	/**
	 * Stores the size of the BST
	 */
	int size;

	/**
	 * Constructor to initialize the member variables of the Binary Search Tree
	 */
	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	/**
	 * Find a value x in the subtree rooted at t
	 * 
	 * @param t		The root of the subtree to be searched
	 * @param x		The value to be searched
	 * @return		The tree node if found else null
	 */
	public Entry<T> find(Entry<T> t, T x)
	{
		if (t==null || t.element.equals(x))
		{
			return t;
		}
		while(true)
		{
			if (t.element.compareTo(x)<0)
			{
				if(t.right.element == null)
				{
					break;
				}
				else
				{
					t = t.right;
				}
			}
			else if(t.element.compareTo(x)>0)
			{
				if(t.left.element == null)
				{
					break;
				}
				else
				{
					t = t.left;
				}
			}
			else
			{
				break;
			}
		}
		return t;
	}
	
	/**
	 * Find a value x in the subtree rooted at t
	 * 
	 * @param t		The root of the subtree to be searched
	 * @param x		The value to be searched
	 * @return		The tree node if found else null
	 */
	public Entry<T> search(Entry<T> t, T x)
	{
		if (t==null || t.element.equals(x))
		{
			return t;
		}
		while(t.element!=null)
		{
			if (t.element.compareTo(x)<0)
			{
				t = t.right;
			}
			else if(t.element.compareTo(x)>0)
			{
				t = t.left;
			}
			else
			{
				return t;
			}
		}
		return null;
	}

	/**
	 * Helper method to return the parent of the node to be deleted
	 * 
	 * @param t		The root of the subtree to be searched
	 * @param x		The generic value to be searched for
	 * @return		Return null if root null, root is x or element not found, else returns parent node
	 */
	public Entry<T> getParent(Entry<T> t, T x)
	{
		if (t==null || t.element.equals(x))
		{
			return null;
		}

		/**
		 * Variable to store the parent of the current node
		 */
		Entry<T> parent = null;
		while(true)
		{
			if (t.element.compareTo(x)<0)
			{
				if(t.right.element == null)
				{
					parent = null;
					break;
				}
				else
				{
					parent = t;
					t = t.right;
				}
			}
			else if(t.element.compareTo(x)>0)
			{
				if(t.left.element == null)
				{
					parent = null;
					break;
				}
				else
				{
					parent = t;
					t = t.left;
				}
			}
			else
			{
				break;
			}
		}
		return parent;
	}

	/**
	 * Helper method to return the path from root till the node.
	 * 
	 * @param t		The root of the subtree to be searched
	 * @param x		The node to be searched for
	 * @param s		The stack to store the path from the root to the node
	 * @return		Return stack containing path from root till the node if present or till the search dropped off
	 */
	public boolean getPath(Entry<T> t, Entry<T> x, Stack<Entry<T>> s)
	{
		s.push(null);
		while(true)
		{
			if (t.element.compareTo(x.element)<0)
			{
				if(t.right.element == null)
				{
					s.push(t);
					break;
				}
				else
				{
					s.push(t);
					t = t.right;
				}
			}
			else if(t.element.compareTo(x.element)>0)
			{
				if(t.left.element == null)
				{
					s.push(t);
					break;
				}
				else
				{
					s.push(t);
					t = t.left;
				}
			}
			else
			{
				s.push(t);
				return true;
			}
		}
		return false;
	}

	/**
	 * Find if x is contained in the tree
	 * 
	 * @param x		The generic value to be searched for
	 * @return		True if found else False
	 */
	public boolean contains(T x) {
		return this.search(this.root, x) != null;
	}

	/**
	 * Helper method of find if x is contained in the subtree rooted at t
	 * 
	 * @param t		The root of the subtree to be searched
	 * @param x		The generic value to be searched for
	 * @return		True if the value is found else False
	 */
	public boolean containsInSubtree(Entry<T> t, T x)
	{
		if(t==null)
		{
			return false;
		}
		if(t.element.compareTo(x)<0)
		{
			return this.containsInSubtree(t.right, x);
		}
		else if(t.element.compareTo(x)>0)
		{
			return this.containsInSubtree(t.left, x);
		}

		return true;
	}

	/** 
	 *  Element in tree that is equal to x is returned, null otherwise.
	 *  
	 *  @param		The generic value to be searched for
	 *  @return		Element in tree that is equal to x is returned, null otherwise
	 */
	public T get(T x) {
		Entry<T> t = find(this.root, x);
		if(t!=null && t.element.equals(x))
		{
			return t.element;
		}
		return null;
	}

	/**
	 *  If tree contains a node with same key, replace element by x.
	 *  
	 *  @param		The generic value to be added to the tree
	 *  @return		Returns true if x is a new element added to tree.
	 */
	public boolean add(T x) {
		if(this.size==0)
		{
			this.root = new Entry<T>(x, null, null);
		}
		else
		{
			Entry<T> t = find(this.root, x);
			if(t.element.compareTo(x)<0)
			{
				t.right = new Entry<T>(x, null, null);
			}
			else if(t.element.compareTo(x)>0)
			{
				t.left = new Entry<T>(x, null, null);
			}
			else
			{
				return false;
			}
		}
		this.size++;
		return true;
	}

	/**
	 *  If tree contains a node with same key, replace element by x.
	 *  
	 *  @param 		The node to be added to the tree
	 *  @return		Returns the stack containing the path from the root to the node.
	 */
	public Stack<Entry<T>> add(Entry<T> x) {
		/**
		 * Stack to store the path to the node.
		 */
		Stack<Entry<T>> s = new Stack<Entry<T>>();    	
		if(this.size==0)
		{
			s.push(null);
			this.root = x;
			s.push(this.root);
		}
		else
		{
			this.getPath(this.root, x, s);
			Entry<T> t = s.peek();
			if(t.element.compareTo(x.element)<0)
			{
				t.right = x;
				s.push(t.right);
			}
			else if(t.element.compareTo(x.element)>0)
			{
				t.left = x;
				s.push(t.left);
			}
			else
			{
				return s;
			}
		}
		this.size++;
		return s;
	}

	/**
	 * Remove x from tree. 
	 * 
	 * @param		The generic value to be removed from the tree
	 * @return		Return x if found, otherwise return null
	 */
	public T remove(T x) {
		if(this.size==0)
		{
			return null;
		}
		
		/**
		 * Node to store the parent of the current node
		 */
		Entry<T> parent = this.getParent(this.root, x);
		
		/**
		 * The current node
		 */
		Entry<T> t;
		if(parent==null)
		{
			if(this.root.element.equals(x))
			{
				t = this.root;
			}
			else
			{
				return null;
			}
		}
		else
		{
			t = (parent.left!=null && parent.left.element.equals(x)) ? parent.left : parent.right;   
		}

		if(t.left==null || t.right==null)
		{
			this.splice(parent, t);
		}
		else
		{
			t.element = this.findMinAndRemove(t, t.right);
		}
		this.size--;
		return x;
	}

	/**
	 * Remove x from tree. 
	 * 
	 * @param		The generic value to be removed from the tree
	 * @return		Return stack containing path from the root to the node
	 */
	public Stack<Entry<T>> removeNode(T x) {
		/**
		 * Stack to save the path from the root to the node
		 */
		Stack<Entry<T>> s = new Stack<Entry<T>>();
		if(this.size==0)
		{
			return s;
		}
		if(this.getPath(this.root, new Entry<T>(x, null, null), s))
		{
			s.pop();
		}
		else
		{
			s.clear();
			return s;
		}
		/**
		 * Variable to store the parent of the current node
		 */
		Entry<T> parent = s.pop();

		/**
		 * The current node
		 */
		Entry<T> t;
		if(parent==null)
		{
			if(this.root.element.equals(x))
			{
				this.size--;
				t = this.root;
				s.push(parent);
				if(t.left.element==null)
				{
					if(t.right.element==null)
					{
						s.push(t);
						return s;
					}
					else
					{
						this.root = t.right;
						s.push(t.right);
						s.push(t);
						return s;
					}
				}
				else
				{
					if(t.right.element==null)
					{
						this.root = t.left;
						s.push(t.left);
						s.push(t);
						return s;
					}
					else
					{
						/**
						 * The spliced child in the right subtree
						 */
						Entry<T> splicedChild = this.findMinAndRemoveNode(t, t.right, s);
						this.root.element = splicedChild.element;
						s.push(splicedChild);
						return s;
					}
				}
			}
			else
			{
				return s;
			}
		}
		else
		{
			size--;
			if(parent.right.element!=null && parent.right.element.equals(x))
			{    	    	
				t = parent.right;
				s.push(parent);
				if(t.left.element==null)
				{
					parent.right = t.right;
					s.push(t.right);
					s.push(t);
					return s;
				}
				else
				{
					if(t.right.element==null)
					{
						parent.right = t.left;
						s.push(t.left);
						s.push(t);
						return s;
					}
					else
					{
						/**
						 * The spliced child in the right subtree
						 */
						Entry<T> splicedChild = this.findMinAndRemoveNode(t, t.right, s);
						t.element = splicedChild.element;
						s.push(splicedChild);
						return s;
					}
				}
			}
			else
			{
				t = parent.left;
				s.push(parent);
				if(t.left.element==null)
				{
					parent.left = t.right;
					s.push(t.right);
					s.push(t);
					return s;
				}
				else
				{
					if(t.right.element==null)
					{
						parent.left = t.left;
						s.push(t.left);
						s.push(t);
						return s;
					}
					else
					{
						/**
						 * The spliced child in the right subtree
						 */
						Entry<T> splicedChild = this.findMinAndRemoveNode(t, t.right, s);
						t.element = splicedChild.element;
						s.push(splicedChild);
						return s;
					}
				}
			}
		}
	}

	/**
	 * Splice the node t from the tree
	 * 
	 * @param parent	The parent of t
	 * @param t			The node to be spliced
	 */
	public void splice(Entry<T> parent, Entry<T> t)
	{
		/**
		 * The child of the node t
		 */
		Entry<T> child = t.left==null ? t.right : t.left;
		if(parent==null)
		{
			this.root = child;
		}
		else if(parent.left.equals(t))
		{
			parent.left = child;
		}
		else
		{
			parent.right = child;
		}
	}

	/**
	 * Print the Binary Search Tree in a human readable form
	 */
	public void printBSTree()
	{
		/**
		 * Queue to maintain the order of the next lower level nodes to be printed
		 */
		Queue<Entry<T>> q = new LinkedList<Entry<T>>();
		q.add(this.root);
		while(!q.isEmpty())
		{
			int run = q.size();
			for(int i=0; i<run; i++)
			{
				Entry<T> t = q.poll();
				System.out.print(t.element+"{"+(t.left==null?"":t.left.element.toString())+","+(t.right==null?"":t.right.element.toString())+"} ");
				if(t.left!=null)
				{
					q.add(t.left);
				}
				if(t.right!=null)
				{
					q.add(t.right);
				}
			}
			System.out.println();
		}
	}

	/**
	 * Find and remove the minimum node in the subtree
	 * 
	 * @param parent	Parent of the subtree
	 * @param t			Root of the subtree
	 * @return			The value of the deleted node
	 */
	public T findMinAndRemove(Entry<T> parent, Entry<T> t)
	{
		while(t.left!=null)
		{
			parent  = t;
			t = t.left;
		}
		if(parent.left.equals(t))
		{
			parent.left = t.right;
		}
		else
		{
			parent.right = t.right;
		}
		return t.element;
	}

	/**
	 * Find and remove the minimum node in the subtree
	 * 
	 * @param parent	Parent of the subtree
	 * @param t			Root of the subtree
	 * @param s			Stack to store the path to the deleted node
	 * @return			The deleted node
	 */
	public Entry<T> findMinAndRemoveNode(Entry<T> parent, Entry<T> t, Stack<Entry<T>> s)
	{
		s.push(parent);
		while(t.left.element!=null)
		{
			parent  = t;
			t = t.left;
			s.push(parent);
		}
		if(parent.left.equals(t))
		{
			parent.left = t.right;
		}
		else
		{
			parent.right = t.right;
		}
		s.push(t.right);
		return t;
	}

	/**
	 * The minimum value in the tree
	 * 
	 * @return	The minimum value in the tree if it has nodes else null
	 */
	public T min() {
		if(this.size==0)
		{
			return null;
		}
		/**
		 * The reference to the current node
		 */
		Entry<T> t  = this.root;
		while(t.left!=null)
		{
			t = t.left;
		}
		return t.element;
	}

	/**
	 * The maximum value in the tree
	 * 
	 * @return	The maximum value in the tree if it has nodes else null
	 */
	public T max() {
		if(this.size==0)
		{
			return null;
		}
		/**
		 * The reference to the current node
		 */
		Entry<T> t = this.root;
		while(t.right!=null)
		{
			t = t.right;
		}
		return t.element;
	}

	/**
	 * Create an array with the elements using in-order traversal of tree
	 * 
	 * @return	an array with the elements using in-order traversal of tree
	 */
	@SuppressWarnings("rawtypes")
	public Comparable[] toArray() {
		/**
		 * Array to store the ordered elements
		 */
		Comparable[] arr = new Comparable[size];
		/**
		 * Stack to store the vertical path to the leaves
		 */
		Stack<Entry<T>> stack = new Stack<Entry<T>>();
		/**
		 * Variable to refer to the current node
		 */
		Entry<T> current = this.root;
		/**
		 * variable to reference the location in the array
		 */
		int i = 0;
		while(current!=null || stack.size()>0)
		{
			while(current!=null)
			{
				stack.push(current);
				current=current.left;
			}
			current = stack.pop();
			arr[i++] = current.element;
			current = current.right;
		}
		return arr;
	}


	// Start of Optional problem 2

	/** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
	 */
	public Iterator<T> iterator() {
		return null;
	}

	// Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
	public T floor(T x) {
		return null;
	}

	// Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
	public T ceiling(T x) {
		return null;
	}

	// Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
	public T predecessor(T x) {
		return null;
	}

	// Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
	public T successor(T x) {
		return null;
	}

	// End of Optional problem 2
	public static void main(String args[])
	{
		
	}
	
	/**
	 * Print the tree in a human readable form.
	 */
	public void printTree() {
		System.out.print("[" + size + "]");
		printTree(root);
		System.out.println();
	}

	/**
	 * Inorder traversal of tree
	 * 
	 * @param node		The root node
	 */
	void printTree(Entry<T> node) {
		if(node != null) {
			printTree(node.left);
			System.out.print(" " + node.element);
			printTree(node.right);
		}
	}

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

 */
