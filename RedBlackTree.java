package sxa190016;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import sxa190016.BinarySearchTree.Entry;

/**
 * @author sxa190016
 * @author axs190140
 * @author epm180002
 * @author nxs190026
 * @version 1.0 Red Black Tree: Implement add, remove & verifyRBT methods of generic Red Black Tree
 * 								by extending the BinarySearchTree class.
 * @param <T>	Generic variable for storing objects of type T
 */
@SuppressWarnings("unused")
public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	/**
	 * Label for RED color node
	 */
	public static final boolean RED = true;

	/**
	 * Label for BLACK color node
	 */
	public static final boolean BLACK = false;

	/**
	 * Nil node for leaves of the tree
	 */
	public Entry<T> nil;


	/**
	 * @author sxa190016
	 * @author axs190140
	 * @author epm180002
	 * @author nxs190026
	 * @version 1.0 Entry: Generic class to store nodes of the Red Black Tree, extends BinarySearchTree.Entry.
	 * @param <T>	Generic variable for storing objects of type T
	 */
	static class Entry<T> extends BinarySearchTree.Entry<T> {

		/**
		 * Field to store the color of a node
		 */
		boolean color;

		/**
		 * Constructor to initialize a node
		 * 
		 * @param x		Generic value of the node
		 * @param left	Points to the left child
		 * @param right	Points to the right child
		 */
		Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			color = RED;
		}

		/**
		 * Checks is node is RED
		 * 
		 * @return		True if node is RED else False
		 */
		boolean isRed() {
			return color == RED;
		}

		/**
		 * Checks if node is BLACK
		 * 
		 * @return		True if node is BLACK else False
		 */
		boolean isBlack() {
			return color == BLACK;
		}
	}

	/**
	 * Constructor for the RedBlackTree class.
	 */
	RedBlackTree() {
		super();
		nil = new Entry<T>(null, null, null);
		nil.color = BLACK;
	}

	/**
	 * Adds a new node to the Red Black Tree
	 * 
	 * @param x		Node value of generic type T
	 * @return		True if node is successfully added else False		
	 */
	public boolean add(T x)
	{
		/**
		 * Create a new node to insert in the Red Black Tree. 
		 */
		Entry<T> e = new Entry<T>(x, nil, nil);
		
		/**
		 * Stack to store the path to the newly inserted node.
		 */
		Stack<BinarySearchTree.Entry<T>> s = super.add(e);
		
		/**
		 * Variable to store the result of the operation.
		 */
		boolean result = s.peek().equals(e);
		if(result)
		{
			/**
			 * Variable to refer to the current node.
			 */
			Entry<T> cur = (Entry<T>) s.pop();
			
			/**
			 * Variable to refer to the parent node
			 */
			Entry<T> par;
			
			/**
			 * Variable to refer to the uncle of the current node
			 */
			Entry<T> unc;
			while(!cur.equals(this.root) && (par = (Entry<T>) s.pop()).isRed())
			{
				unc = (Entry<T>) (s.peek().left.equals(par)?s.peek().right:s.peek().left);
				if(par.equals(s.peek().left))
				{
					if(unc.isRed())
					{
						par.color = unc.color = BLACK;
						cur = (Entry<T>) s.pop();
						cur.color = RED;
					}
					else
					{
						Entry<T> gram = (Entry<T>) s.pop();
						gram.color = RED;
						if(cur.equals(par.right))
						{
							leftRotate(par, gram);
							cur.color = BLACK;
						}
						else
						{
							par.color = BLACK;
						}
						rightRotate(gram, (Entry<T>) s.pop());
						break;
					}
				}
				else
				{
					if(unc.isRed())
					{
						par.color = unc.color = BLACK;
						cur = (Entry<T>) s.pop();
						cur.color = RED;
					}
					else
					{
						Entry<T> gram = (Entry<T>) s.pop();
						gram.color = RED;
						if(cur.equals(par.left))
						{
							rightRotate(par, gram);
							cur.color = BLACK;
						}
						else
						{
							par.color = BLACK;
						}
						leftRotate(gram, (Entry<T>) s.pop());
						break;
					}
				}
			}
			((Entry<T>)this.root).color = BLACK;
		}    	
		return result;
	}

	/**
	 * Remove a node with value x from the Red Black Tree
	 * 
	 * @param x		Generic value of the node to be deleted
	 * @return		Returns the value if node is deleted else returns null
	 */
	public T remove(T x)
	{
		/**
		 * Stack to save the path from the root to the deleted node
		 */
		Stack<BinarySearchTree.Entry<T>> s = super.removeNode(x);
		if(s.size()>0)
		{
			if(((Entry<T>)s.pop()).color==BLACK && this.size>0)
			{
				fixup(s);
			}
			return x;
		}    	
		return null;
	}

	/**
	 * Fix the Red-Black properties after the deletion of a node
	 * 
	 * @param s		Stack to store the path from the root to the deleted node
	 */
	public void fixup(Stack<BinarySearchTree.Entry<T>> s)
	{
		/**
		 * Variable to store the current node
		 */
		Entry<T> cur = (Entry<T>) s.pop(); 
		
		/**
		 * Variable to store the sibling of the current node
		 */
		Entry<T> sib;
		
		/**
		 * Variable to store the parent of the current node
		 */
		Entry<T> par;
		while(!(cur).equals(this.root) && cur.isBlack())
		{
			par = (Entry<T>)s.pop();
			if(cur.equals(par.left))
			{
				if((sib = (Entry<T>) par.right).isRed())
				{
					sib.color = BLACK;
					par.color = RED;
					Entry<T> right = (Entry<T>) par.right;
					leftRotate(par, (Entry<T>)s.peek());
					s.push(right);
				}
				sib = (Entry<T>) par.right;
				if(((Entry<T>)sib.left).isBlack() && ((Entry<T>)sib.right).isBlack())
				{
					sib.color = RED;
					cur = par;
				}
				else
				{
					if(((Entry<T>)sib.right).isBlack())
					{
						((Entry<T>)sib.left).color = BLACK;
						sib.color = RED;
						rightRotate(sib, par);
						sib = (Entry<T>) par.right;
					}
					sib.color = par.color;
					par.color = BLACK;
					((Entry<T>)sib.right).color = BLACK;
					leftRotate(par, (Entry<T>)s.peek());
					cur = (Entry<T>) this.root;
				}
			}
			else
			{
				sib = (Entry<T>) par.left;
				if(sib.isRed())
				{
					sib.color = BLACK;
					par.color = RED;
					Entry<T> left = (Entry<T>) par.left;
					rightRotate(par, (Entry<T>)s.peek());
					s.push(left);
					sib = (Entry<T>) par.left;
				}
				if(((Entry<T>)sib.left).isBlack() && ((Entry<T>)sib.right).isBlack())
				{
					sib.color = RED;
					cur = par;
				}
				else
				{
					if(((Entry<T>)sib.left).isBlack())
					{
						((Entry<T>)sib.right).color = BLACK;
						sib.color = RED;
						leftRotate(sib, par);
						sib = (Entry<T>) par.left;
					}
					sib.color = par.color;
					par.color = BLACK;
					((Entry<T>)sib.left).color = BLACK;
					rightRotate(par, (Entry<T>)s.peek());
					cur = (Entry<T>) this.root;
				}
			}
		}
		cur.color = BLACK;
	}

	/**
	 * Left rotate the current node
	 * 
	 * @param cur		Reference to the current node
	 * @param par		Reference to the parent of the current node
	 */
	public void leftRotate(Entry<T> cur, Entry<T> par)
	{
		if(cur.equals(this.root))
		{
			this.root = cur.right;
			cur.right = this.root.left;
			this.root.left = cur;
		}
		else
		{
			if(cur.equals(par.left))
			{
				par.left = cur.right;
				par = (Entry<T>) par.left;
				cur.right = par.left;
				par.left = cur;
			}
			else
			{
				par.right = cur.right;
				par = (Entry<T>) par.right;
				cur.right = par.left;
				par.left = cur;
			}
		}

	}

	/**
	 * Right rotate the current node
	 * 
	 * @param cur		Reference to the current node
	 * @param par		Reference to the parent of the current node
	 */
	public void rightRotate(Entry<T> cur, Entry<T> par)
	{
		if(cur.equals(this.root))
		{
			this.root = cur.left;
			cur.left = this.root.right;
			this.root.right = cur;
		}
		else
		{
			if(cur.equals(par.left))
			{
				par.left = cur.left;
				par = (Entry<T>) par.left;
				cur.left = par.right;
				par.right = cur;
			}
			else
			{
				par.right = cur.left;
				par = (Entry<T>) par.right;
				cur.left = par.right;
				par.right = cur;
			}
		}    	
	}

	/**
	 * Verify if the tree satisfies all the Red-Black tree properties
	 * 
	 * @return		True if it satisfies else False
	 */
	public boolean verifyRBT()
	{
		if(this.size == 0)
		{
			return true;
		}
		if(((Entry<T>)this.root).isRed())
		{
			System.out.print("Root is red.");
			return false;
		}
		/**
		 * Stack to store the path from the root to the current node
		 */
		Stack<Entry<T>> stack = new Stack<Entry<T>>();
		/**
		 * Stack to store the depth of the respective node in the stack
		 */
		Stack<Integer> depth = new Stack<>();
		/**
		 * Refers to the current node
		 */
		Entry<T> current = (Entry<T>) this.root;
		/**
		 * The depth of the leaves
		 */
		Integer finalDepth=null;
		/**
		 * The depth of the current node
		 */
		Integer curDepth=0;
		while(current.element!=null || stack.size()>0)
		{
			while(current.element!=null)
			{
				stack.push(current);
				depth.push(curDepth);
				current=(Entry<T>) current.left;
				if(current.element!=null)
				{
					if(current.isBlack())
					{
						curDepth++;
					}
					else
					{
						if(stack.peek().isRed())
						{
							System.out.print("current "+current.element+" is red and stack.peek() "+stack.peek().element+" is also red. ");
							return false;
						}
					}
				}    			
			}
			if(finalDepth==null)
			{
				finalDepth = curDepth;
			}
			else
			{
				if(curDepth!=finalDepth)
				{
					System.out.print("stack.peek().element "+stack.peek().element+" has curDepth"+curDepth+" != finalDepth"+finalDepth+". ");
					return false;
				}
			}
			current = stack.pop();
			curDepth = depth.pop();
			if(current.isBlack())
			{
				current = (Entry<T>) current.right;
				if(current.element!=null && current.isBlack())
				{
					curDepth++;
				}
			}
			else
			{
				current = (Entry<T>) current.right;
				if(current.isRed())
				{
					System.out.print(current.element+" is red.");
					return false;
				}
				else
				{
					if(current.element!=null)
					{
						curDepth++;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Print an Red Black Tree in human readable form
	 */
	public void printRBLTree()
	{
		if(this.size>0)
		{
			/**
			 * Queue to store the next lower level node in correct order
			 */
			Queue<Entry<T>> q = new LinkedList<Entry<T>>();
			q.add((Entry<T>) this.root);
			while(!q.isEmpty())
			{
				/**
				 * Store the size of the queue
				 */
				int run = q.size();
				for(int i=0; i<run; i++)
				{
					Entry<T> t = q.poll();
					System.out.print((t.color?"R":"B")+t.element+"{"+(t.left.element==null?"":t.left.element.toString())+","+(t.right.element==null?"":t.right.element.toString())+"} ");
					if(t.left.element!=null)
					{
						q.add((Entry<T>) t.left);
					}
					if(t.right.element!=null)
					{
						q.add((Entry<T>) t.right);
					}
				}
				System.out.println();
			}
		}
		else
		{
			System.out.println("null");
		}
	}
}