README:
======

Long Project # 03: Skip List and Red-Black Tree algorithm Implementation and Comaprison with Java TreeSet.


Authors :
------
1) Shariq Ali SXA190016
2) Abhigyan axs190140
3) Enakshi epm180002
4) Navanil nxs190026


How to compile and run the code:
-------------------------------
The files Timer.java, SkipList.java, BinarySearchTree.java, RedBlackTree.java & RedBlackTreeDriver.java should be placed inside the folder named as 'sxa190016' which is the package name.
Run the below commands sequentially to execute the program

1) The command prompt path should be in "sxa190016" directory
2) javac Timer.java
3) javac SkipList.java
4) javac SkipListDriver.java
5) java SkipListDriver
6) javac BinarySearchTree.java
7) javac RedBlackTree.java
8) javac RedBlackTreeDriver.java
9) java RedBlackTreeDriver


===SkipList===

The main methods written for SkipList class are:

add		- Adds a new node to the Skiplist if it does not exist

remove		- Remove a node with value x from the Skiplist

last		- Returns the last element in the list

isEmpty		- check if the skip list is empty or not

getLog		- Finding the nth element in the list in O(log N) time

getLinear	- Finding the nth element in O(N) time

get		- Return element at index n of list

floor		- largest element that is less than or equal to x

first		- Return the first element in the list

contains	- Does the list contains x

find 		- Find the element present in the skip list or not

ceiling		- Find smallest element that is greater or equal to x

chooseLevel	- Method to randomly choose the level

calcDist	- Calculate the distance between two nodes

addElementsWithLevel - Add elements with level to skip list

printList       - Print the Skiplist in human readable form


The main function:
-------------------
When you run the main function, it will
1. Specify the size of the input data in millions
2. Scanner to read the input file
3. Timer to record the time of operations
4. Array to store the input
5. Create an object of the skiplist
6. Check the performance of add
7. Check the performance of contains
8. Check the performance of remove


Report:
-------------------
Note: 
# All the values are in milli-seconds
# M - million operations

                    4 Million operations     add() 	contains()      remove()
                           SkipList	     18747       9776            9323

                   16 Million operations     add() 	contains()      remove()
                           SkipList	     87423	69024           65297

                   64 Million operations     add() 	contains()      remove()
                           SkipList	     java.lang.OutOfMemoryError : GC overhead limit exceeded

                  256 Million operations     add() 	contains()      remove()
                           SkipList	     java.lang.OutOfMemoryError : GC overhead limit exceeded



===Red-Black Tree===


Methods in Code:
-------------------
The main methods written for BinarySearchTree class are:

find            - Find if x is contained in the tree

contains        - Find if x is contained in the tree

get             - Element in tree that is equal to x is returned, null otherwise.

add             - If tree contains a node with same key, replace element by x.

remove          - Remove x from tree. 

splice          - Splice the node t from the tree

printBSTree	- Print the Binary Search Tree in a human readable form

min             - The minimum value in the tree

max 	        - The maximum value in the tree

toArray 	- Create an array with the elements using in-order traversal of tree


The main methods written for RedBlackTree class are:

add		- Adds a new node to the Red Black Tree

remove		- Remove a node with value x from the Red Black Tree

fixup		- Fix the Red-Black properties after the deletion of a node

leftRotate	- Left rotate the current node

rightRotate	- Right rotate the current node

verifyRBT	- Verify if the tree satisfies all the Red-Black tree properties

printRBLTree	- Print an Red Black Tree in human readable form


The main function:
-------------------
When you run the main function, it will
1. Specify the size of the input data in millions
2. Scanner to read the input
3. Timer to record the time of operations
4. Array to store the input
5. Create an object of the TreeSet class
6. Check the performance of add
7. Check the performance of contains
8. Check the performance of remove
9. Create an object of the RedBlackTree class
10. Check the performance of add
11. Verify if the Red-Black tree follows all the properties
12. Check the performance of contains
13. Check the performance of remove


Report:
-------------------
Note: 
# All the values are in milli-seconds
# M - million operations

                    4 Million operations     add() contains()       remove()
                           Java Tree Set      2727       2370           3758
                          Red-Black Tree      3188       2171           4850

                   16 Million operations     add() contains()       remove()
                           Java Tree Set     32940      16382          20944
                          Red-Black Tree     29600      15671          26364

                   64 Million operations     add() contains()       remove()
                           Java Tree Set    573123      84223         143333
                          Red-Black Tree    407817      81032         172907

                  256 Million operations     add() contains()       remove()
                           Java Tree Set    java.lang.OutOfMemoryError: Java heap space: failed reallocation of scalar replaced objects
                          Red-Black Tree    java.lang.OutOfMemoryError: Java heap space: failed reallocation of scalar replaced objects


Summary:
-------------------
As you can see in the above report Red-Black Tree has a much better performance as compared to Skip List and Java Tree Set in respect of both add() and contains() operations. 
Red-Black Tree lags behind in case of remove() operation because it has to perform tedious fixup() operation to maintain the Red-Black properties after deletion.
Overall I feel that Red-Black Tree is a good choice as it has a faster search time and promises O(log n) time complexity for all three operations.

Note: Please see the LP3_Report.pdf for detailed report.

