package sxa190016;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

//Driver program for skip list implementation

public class SkipListDriver {
	public static void main(String[] args) throws FileNotFoundException {
	
		int n = 4;
		System.out.print(String.format("%40s", n+" Million operations"));
		System.out.print(String.format("%10s", "add()"));
		System.out.print(String.format("%11s", "contains()"));
		System.out.print(String.format("%15s", "remove()"));
		System.out.println();
		

		Timer t = new Timer();
		long[] arr = new long[n*1000000];
		int i = 0;
		
		//Uncomment the below line if you want to read from the file
		//File name should be [# of values].txt, ex. 4M.txt, 16M.txt, 64M.txt, etc.
//		File file = new File("C:\\Users\\shari\\eclipse-workspace\\LP3\\src\\sxa190016\\"+n+"M.txt");		
//		Scanner sc = new Scanner(file);
//		while(sc.hasNext())
//		{
//			arr[i++] = sc.nextLong();
//		}
//		in.close();
		
		//Uncomment the below line to auto-generate input
		for(i=0; i<n*1000000; i++)
		{
			arr[i] = i;
		}
		
		SkipList<Long> skipList = new SkipList<>();
		
		t.start();
		//Add
		for(i=0;i<n*1000000;i++)
		{
			skipList.add(arr[i]);
		}
		t.end();
		System.out.print(String.format("%40s", ""));
		System.out.print(String.format("%10s", t.elapsedTime));

		t.start();
		//find
		for(i=0;i<n*1000000;i++)
		{
			skipList.contains(arr[i]);
		}
		t.end();
		System.out.print(String.format("%11s", t.elapsedTime));

		t.start();
		//remove
		for(i=0;i<n*1000000;i++)
		{
			skipList.remove(arr[i]);
		}
		t.end();
		System.out.print(String.format("%15s", t.elapsedTime));
		System.out.println();

	}
}
