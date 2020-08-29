package sxa190016;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

public class SkipList<T extends Comparable<? super T>> {
    static final int PossibleLevels = 33;

    /**
     * Entry representing the Linked List contains
     * next[] representing the pointer for max level in the list
     * width[] representing the number of elements between two nodes
     */

    static class Entry<E> {
        E element;
        @SuppressWarnings("rawtypes")
		Entry[] next;
        @SuppressWarnings("rawtypes")
		Entry prev;
        int level;
        int[] width;

        public Entry(E x, int lev) {
            element = x;
            next = (Entry[]) Array.newInstance(Entry.class, lev);
            width = new int[lev];
            level = lev;
        }

        public E getElement() {
            return element;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Entry) {
                @SuppressWarnings("rawtypes")
				Entry o = (Entry) obj;
                if (this.element != null && o.element != null)
                    return this.element.equals(o.element);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this);
        }

        @Override
        public String toString() {
            return element == null ? "null" : this.element.toString();
        }
    }

    /** skipListStack class
    /* It contains entry and total width as its attributes
     * 
     */
    public class SkipListStack {
        @SuppressWarnings("rawtypes")
		private Entry entry;
        private int totalWidth;

        public SkipListStack(@SuppressWarnings("rawtypes") Entry entry, int totalWidth) {
            this.entry = entry;
            this.totalWidth = totalWidth;
        }

        @Override
        public String toString() {
            return entry.toString() + ", " + totalWidth;
        }
    }

    
    @SuppressWarnings("rawtypes")
	private Entry head, tail, lastNode; 
    private int size, maxLevel;
    private Random random;
    private SkipListStack[] prev;

    public SkipList() {
        head = new Entry<>(null, PossibleLevels + 2);
        tail = new Entry<>(null, PossibleLevels + 2);
        size = 0;
        maxLevel = 1;
        initializeSentinels();
        random = new Random();
    }

    /**
     * Adding element to skip list
     *
     * @param x Add x to list.
     * @return If x already exists, return false else true
     */
    @SuppressWarnings("unchecked")
	public boolean add(T x) {

        if (x == null)
            return false;

        if (contains(x))
            return false;

        int level = Math.min(chooseLevel(), PossibleLevels);
        maxLevel = Math.max(maxLevel, level);

        @SuppressWarnings("rawtypes")
		Entry new_Node = addElementsWithLevel(x, level, prev);
        
        if (new_Node.next[0].element != null) {
            new_Node.next[0].prev = new_Node;
        }

        if (new_Node.next[0].element == null)
            this.lastNode = new_Node;

        size++;
        return true;
    }

    /**
     * Add elements with level to skip list
     *
     * @param x Add x to list.
     * @param level 
     * @return void
     */
    private void addElementsWithLevel(T x, int level) {
        find(x);
        addElementsWithLevel(x, level, prev);
    }

    /**
     * Add elements with level to skip list
     *
     * @param x Add x to list.
     * @param level 
     * @param prev 
     * @return new node
     */
    @SuppressWarnings("rawtypes")
	private Entry addElementsWithLevel(T x, int level, SkipListStack[] prev) {
        Entry newNode = new Entry<>(x, level + 1);

        int distance = 0;

        for (int i = 0; i <= PossibleLevels; i++) {
            Entry prevNode = i < prev.length ? prev[i].entry : head;
            if (i <= level) {
                newNode.next[i] = prevNode.next[i];
                prevNode.next[i] = newNode;
                // update distance

                newNode.width[i] = Math.max(prevNode.width[i] - distance, 1);
                prevNode.width[i] = distance + 1;

                distance += prev[i].totalWidth;
            } else {
                prevNode.width[i]++;
            }
        }
        return newNode;
    }
    
    /**
     * Calculate the distance between two nodes
     *
     * @param x representing the source node
     * @param y representing the destination node
     * @return distance between source and destination node
     */

    @SuppressWarnings({ "unused", "unchecked" })
	private int calcDist(Entry<T> x, Entry<T> y) {
        if (x == y || x == null || y == null) {
            return 0;
        }
        Entry<T> current = x;
        int distance = 0;
        while (current != y) {
            for (int i = 0; i < maxLevel; i++) {
                if (!current.next[i].equals(tail) && y.element.compareTo((T) current.next[i].element) >= 0) {
                    distance += current.width[i];
                    current = current.next[i];
                    break;
                }
            }
        }
        return distance;
    }

    /**
     * Method to randomly choose the level
     *
     * @return random level as int
     */

    public int chooseLevel() {
        int level = 1 + Integer.numberOfTrailingZeros(random.nextInt());
        return Math.min(level, maxLevel + 1);
    }

    /**
     * Find smallest element that is greater or equal to x
     *
     * @param x
     * @return ceiling of the number
     */

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public T ceiling(T x) {
        if (contains(x)) {
            return x;
        }
        Entry nxt = prev[0].entry;
        if (nxt == null || nxt.next[0] == null)
            return null;
        return (T) nxt.next[0].element;
    }

    /**
     * Find the element present in the skip list or not
     *
     * @param x element to be search in the list
     *          updates the next[] array which can be used to track of the height of the skip list
     */

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void find(T x) {
        prev = (SkipListStack[])
                Array.newInstance(SkipListStack.class, Math.min(maxLevel + 2, PossibleLevels+1));
        Entry pcur = head;
        for (int i = Math.min(maxLevel + 1, PossibleLevels); i >= 0; i--) {
            int hops = 0;
            while (pcur.next[i].element != null && x.compareTo((T) pcur.next[i].element) > 0) {
                hops += pcur.width[i];
                pcur = pcur.next[i];
            }
            prev[i] = new SkipListStack(pcur, hops);
        }
    }

    /**
     * Does the list contains x
     *
     * @param x element to check
     * @return true if the element present else false
     */

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean contains(T x) {
        find(x);
        Entry curr = prev[0].entry;
        return curr.next[0].element != null && x.compareTo((T) curr.next[0].element) == 0;
    }

    /**
     * Return the first element in the list
     *
     * @return first in the skip list
     */

    @SuppressWarnings("unchecked")
	public T first() {
        return (T) head.next[0].element;
    }

    /**
     * largest element that is less than or equal to x
     *
     * @param x element to floor
     * @return floor value of x
     */


    @SuppressWarnings({ "unchecked", "rawtypes" })
	public T floor(T x) {
        if (contains(x)) {
            return x;
        }
        Entry node = prev[0].entry;
        return (T) node.element;
    }

    /**
     * Return element at index n of list.  First element is at index 0.
     *
     * @param n index of list
     * @return element at index n in the list
     **/

    public T get(int n) {
        if (n < 0 || n >= size())
            return null;
//        return getLinear(n);
        return getLog(n);
    }

    /**
     * Finding the nth element in O(N) time
     *
     * @param n index
     * @return element at nth index
     */

    public T getLinear(int n) {
        SkipListIterator<T> iterator = new SkipListIterator<>(0);
        int counter = -1;
        T element = null;
        while (counter++ < n && iterator.hasNext()) {
            element = iterator.next();
        }
        return element;
    }

    /**
     * Finding the nth element in O(log N) time
     *
     * @param n index
     * @return element at nth index
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public T getLog(int n) {
        int currSpan = 0;
        Entry node = head;
        for (int i = maxLevel; i >= 0; i--) {
            while (node.next[i] != null && currSpan + node.width[i] <= n) {
                currSpan += node.width[i];
                node = node.next[i];
            }
        }
        return (T) node.next[0].element;
    }

    /**
     * check if the skip list is empty or not
     *
     * @return true if the skip list is empty else false
     */

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Iterate through the elements of list in sorted order
     */

    public Iterator<T> iterator() {
        return new SkipListIterator<>(0);
    }

    /**
     * @return last element in the list
     */

    @SuppressWarnings("unchecked")
	public T last() {
        return (T) this.lastNode.element;
    }

    /**
     * Reorganize the elements of the list into a perfect skip list
     */

    public void rebuild() throws CloneNotSupportedException {
        @SuppressWarnings("rawtypes")
		Entry[] copy = (Entry[]) clone();
        this.maxLevel = (int) Math.pow(2, Math.ceil(Math.log(this.size()) / Math.log(2)) - 1);
        int nextPower = (int) Math.pow(2, Math.ceil(Math.log(this.size()) / Math.log(2)) );
        initializeSentinels();
        rebuildWithLevels(copy, 0, nextPower, maxLevel);
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return cloneSkipHeader();
    }

    /**
     * Divide and conquer
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void rebuildWithLevels(Entry[] skipArr, int start, int end, int level) {

        if (start > end)
            return;


        int mid = (end - start) / 2 + start;

        if (mid < skipArr.length)

            this.addElementsWithLevel((T) skipArr[mid].element, level);

        // recurse both sub array

        rebuildWithLevels(skipArr, start, mid - 1, level - 1);

        if (mid < skipArr.length) 

            rebuildWithLevels(skipArr, mid + 1, end, level - 1);
    }


    @SuppressWarnings("rawtypes")
	private Entry[] cloneSkipHeader() {
        Entry[] copy = (Entry[]) Array.newInstance(Entry.class, this.size());
        Entry tempHead = this.head.next[0];
        int i = 0;
        while (tempHead.element != null) {
            copy[i++] = tempHead;
            tempHead = tempHead.next[0]; // move to next level
        }
        return copy;
    }

    private void initializeSentinels() {
        for (int i = 0; i <= PossibleLevels; i++) {
            this.head.next[i] = tail;
            this.head.width[i] = 1;
            this.tail.next[i] = null;
        }
    }


    /**
     * Remove x from list.
     *
     * @return Removed element is returned. Return null if x not in list
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public T remove(T x) {
        if (!contains(x))
            return null;

        Entry et = prev[0].entry.next[0];
        // last before
        if (et.next[0].element == null) {
            this.lastNode = prev[0].entry;
        }


        for (int i = 0; i <= PossibleLevels; i++) {
            Entry prev_Node = i < prev.length ? prev[i].entry : head;
            if (prev_Node.next[i].element != null && prev_Node.next[i].element.equals(x)) {
                prev_Node.width[i] += et.width[i] - 1;
                prev_Node.next[i] = et.next[i]; 
            } else {
                prev_Node.width[i]--;
            }

        }

        size = size - 1;
        return (T) et.element;
    }

    /**
     *print the list
     */
    @SuppressWarnings("rawtypes")
	public void printList() {
        Entry node = head.next[0];
        System.out.println("----------START----------");
        while (node != null && node.element != null) {
            for (int i = 0; i < node.level; i++) {
                System.out.print(node.element + "\t");
            }
            for (int j = node.level; j < maxLevel; j++) {
                System.out.print("|\t");
            }
            System.out.println();
            node = node.next[0];
        }
        System.out.println("----------END----------");
    }
    /**
     * @return size of the skip list
     */

    public int size() {
        return size;
    }

    /**
     * Iterator for skip list
     */

    @SuppressWarnings("hiding")
	private class SkipListIterator<T> implements Iterator<T> {
        //
        @SuppressWarnings("rawtypes")
		Entry it;
        int level;

        SkipListIterator(int level) {
            this.level = level;
            it = head.next[level];
        }

        @Override
        public boolean hasNext() {
            if (it != null && it.element != null) {
                return true;
            }
            return false;
        }

        @SuppressWarnings({ "unchecked" })
		@Override
        public T next() {
            Entry<T> node = null;
            if (hasNext()) {
                node = it;
                it = it.next[level];
            }
            return node == null ? null : node.element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            while (hasNext()) {
                action.accept(next());
            }
        }
    }
}