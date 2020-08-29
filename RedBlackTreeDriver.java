
package sxa190016;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author sxa190016
 * @author axs190140
 * @author epm180002
 * @author nxs190026
 * @version 1.0 Red Black Tree Driver: Test the add, remove & verifyRBT methods of generic Red Black Tree.
 */
@SuppressWarnings("unused")
public class RedBlackTreeDriver {
	/**
	 * Main function to test the LP3 class
	 * 
	 * @param args	Arguments to be passed to the main function
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		boolean testing = true;

		if(testing)
		{
			/**
			 * Specify the size of the input data in millions
			 */
			int n = 4;
			
			System.out.print(String.format("%40s", n+" Million operations"));
			System.out.print(String.format("%10s", "add()"));
			System.out.print(String.format("%11s", "contains()"));
			System.out.print(String.format("%15s", "remove()"));
			System.out.println();
			
			/**
			 * Array to store the input
			 */
			int [] arr = new int [n*1000000];
			int i = 0;

			//Uncomment the below line if you want to read from the file
			//File name should be [# of values].txt, ex. 4M.txt, 16M.txt, 64M.txt, etc.
//			File file = new File("C:\\Users\\shari\\eclipse-workspace\\LP3\\src\\sxa190016\\"+n+"M.txt");
//			/**
//			 * Scanner to read the input
//			 */
//			Scanner in = new Scanner(file);
//			while(in.hasNext())
//			{
//				arr[i++] = in.nextInt();
//			}
//			in.close();
			
			//Uncomment the below line to auto-generate input
			for(i=0; i<n*1000000; i++)
			{
				arr[i] = i;
			}
			
			/**
			 * Timer to record the time of operations
			 */
			Timer t = new Timer();
			
			System.out.print(String.format("%40s", "Java Tree Set"));
			
			/**
			 * Create an object of the TreeSet class
			 */
			TreeSet<Integer> ts1 = new TreeSet<Integer>();        
			t.start();
			
			//Check the performance of add
			for(i=0;i<n*1000000;i++)
			{
				ts1.add(arr[i]);
			}
			t.end();
			System.out.print(String.format("%10s", t.elapsedTime));

			t.start();
			
			//Check the performance of contains
			for(i=0;i<n*1000000;i++)
			{
				ts1.contains(arr[i]);
			}
			t.end();
			System.out.print(String.format("%11s", t.elapsedTime));

			t.start();
			//Check the performance of remove
			for(i=0;i<n*1000000;i++)
			{
				ts1.remove(arr[i]);
			}
			t.end();
			System.out.print(String.format("%15s", t.elapsedTime));
			System.out.println();			
			

			System.out.print(String.format("%40s", "Red-Black Tree"));
			
			/**
			 * Create an object of the RedBlackTree class
			 */
			RedBlackTree<Integer> bst = new RedBlackTree<Integer>();        
			t.start();
			
			//Check the performance of add
			for(i=0;i<n*1000000;i++)
			{
				bst.add(arr[i]);
			}
			t.end();
			
			//Verify if the Red-Black tree follows all the properties
			if(!bst.verifyRBT())
			{
				System.out.println("RBT not verified!");
			}
			System.out.print(String.format("%10s", t.elapsedTime));

			t.start();
			//Check the performance of contains
			for(i=0;i<n*1000000;i++)
			{
				bst.contains(arr[i]);
			}
			t.end();
			System.out.print(String.format("%11s", t.elapsedTime));

			t.start();
			//Check the performance of remove
			for(i=0;i<n*1000000;i++)
			{
				bst.remove(arr[i]);
			}
			t.end();
			System.out.print(String.format("%15s", t.elapsedTime));
			System.out.println();
		}
	}
}
