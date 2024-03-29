import java.util.ArrayList;

/** A Generic heap class. Unlike Java's priority queue, this heap doesn't just
  * store Comparable objects. Instead, it can store any type of object
  * (represented by type T) and an associated priority value.
  * @author CS 61BL Staff*/
public class ArrayHeap<T> {

    /* DO NOT CHANGE THESE METHODS. */

    /* An ArrayList that stores the nodes in this binary heap. */
    private ArrayList<Node> contents;

    /* A constructor that initializes an empty ArrayHeap. */
    public ArrayHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the number of elements in the priority queue. */
    public int size() {
        return contents.size() - 1;
    }

    /* Returns the node at index INDEX. */
    private Node getNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the node at INDEX to N */
    private void setNode(int index, Node n) {
        // In the case that the ArrayList is not big enough
        // add null elements until it is the right size
        while (index + 1 > contents.size()) {
            contents.add(null);
        }
        contents.set(index, n);
    }

    /* Returns and removes the node located at INDEX. */
    private Node removeNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.remove(index);
        }
    }

    /* Swap the nodes at the two indices. */
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        this.contents.set(index1, node2);
        this.contents.set(index2, node1);
    }

    /* Prints out the heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getNode(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getNode(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getNode(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getNode(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* A Node class that stores items and their associated priorities. */
    public class Node {
        private T item;
        private double priority;

        private Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        public T item() {
            return this.item;
        }

        public double priority() {
            return this.priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return this.item.toString() + ", " + this.priority;
        }
    }



    /* FILL IN THE METHODS BELOW. */

    /* Returns the index of the node to the left of the node at i. */
    private int getLeftOf(int i) {
        int leftindex = (i * 2);
        return leftindex;
    }

    /* Returns the index of the node to the right of the node at i. */
    private int getRightOf(int i) {
        int rightindex = (i * 2 + 1);
        return rightindex;
    }

    /* Returns the index of the node that is the parent of the node at i. */
    private int getParentOf(int i) {
        return (i / 2);
    }

    /* Adds the given node as a left child of the node at the given index. */
    private void setLeft(int index, Node n) {
        int leftchild = getLeftOf(index);
        contents.set(leftchild, n);
    }

    /* Adds the given node as the right child of the node at the given index. */
    private void setRight(int index, Node n) {
        int rightchild = getLeftOf(index);
        contents.set(rightchild, n);
    }

    /** Returns the index of the node with smaller priority. Precondition: not
      * both nodes are null. */
    private int min(int index1, int index2) {
        Node firstnode = getNode(index1);
        Node secondnode = getNode(index2);
        if (firstnode == null) {
            return index2;
        } else if (secondnode.priority > firstnode.priority) {
            return index1;
        } else if (secondnode == null) {
            return index1;
        } else {
            return index2;
        }
    }

    /* Returns the Node with the smallest priority value, but does not remove it
     * from the heap. */
    public Node peek() {
        return contents.get(1);
    }

    /* Bubbles up the node currently at the given index. */
    private void bubbleUp(int index) {
        int moveup = getParentOf(index);
        while (moveup != 0 && getNode(moveup).priority() > getNode(index).priority()) {
            swap(index, moveup);
            bubbleUp(moveup);
        }
    }

    /* Bubbles down the node currently at the given index. */
    private void bubbleDown(int index) {
        int rightnode = getRightOf(index);
        int leftnode = getLeftOf(index);

        if (rightnode <= size() && leftnode <= size() && contents.get(leftnode).priority() < contents.get(rightnode).priority()
                && contents.get(leftnode).priority() < contents.get(index).priority()) {
            swap(index, leftnode);
            bubbleDown(leftnode);
        }
        if (leftnode <= size() && contents.get(rightnode) == null
                && contents.get(index).priority() > contents.get(leftnode).priority()) {
            swap(index, leftnode);
        }
        if (rightnode <= size() && contents.get(rightnode).priority() <= contents.get(leftnode).priority()
                && contents.get(rightnode).priority() < contents.get(index).priority()) {
            swap(index, rightnode);
            bubbleDown(rightnode);
        }
    }

    /* Inserts an item with the given priority value. Same as enqueue, or offer. */
    public void insert(T item, double priority) {
        Node insertion = new Node(item, priority);
        setNode(size() + 1, insertion);
        bubbleUp(contents.indexOf(insertion));
    }

    /* Returns the element with the smallest priority value, and removes it from
     * the heap. Same as dequeue, or poll. */
    public T removeMin() {
        Node smallestvalue = contents.get(1);
        swap(1, size());
        contents.remove(size());
        bubbleDown(1);
        return smallestvalue.item;
    }

    /* Changes the node in this heap with the given item to have the given
     * priority. You can assume the heap will not have two nodes with the same
     * item. Check for item equality with .equals(), not == */
    public void changePriority(T item, double priority) {
        for (int i = 0; i < size(); i += 1) {
            if (contents.get(i).item() == item) {
                contents.get(i).item = item;
                bubbleDown(i);
                bubbleUp(i);
            }
        }
    }

}
