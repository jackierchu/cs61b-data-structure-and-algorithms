
public class IntDList {

    protected DNode _front, _back;

    public IntDList() {
        _front = _back = null;
    }

    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     *
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     *
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     *
     * @return The number of elements in this list.
     */
    public int size() {
        int n;
        n = 0;
        for (DNode p = _front; p != null; p = p._next) {
            n = n + 1;
        }
        return n;

    }

    /**
     *
     * @param i index of element to return, where i = 0 returns the first element,
     *          i = 1 returns the second element, i = -1 returns the last element,
     *          i = -2 returns the second to last element, and so on.
     *          You can assume i will always be a valid index, i.e 0 <= i < size
     *          for positive indices and -size <= i < 0 for negative indices.
     * @return The integer value at index i
     */
    public int get(int i) {
        DNode x;
        if (i >= 0) {
            x = _front;
            while (i > 0) {
                i -= 1;
                x = x._next;
            }
        } else {
            x = _back;
            while (i < -1) {
                i += 1;
                x = x._prev;
            }
        }
        return x._val;
    }

    /**
     *
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        _front = new DNode(null, d, _front);
        if (_back == null) {
            _back = _front;
        } else {
            _front._next._prev = _front;
        }
    }

    /**
     *
      * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        _back = new DNode(_back, d, null);
        if (_front == null) {
            _front = _back;
        } else {
            _back._prev._next = _back;
        }
    }

    /**
     * Removes the last item in the IntDList and returns it
     * @return the item that was deleted
     */
    public int deleteBack() {
        int a = _back._val;
        _back = _back._prev;
        if (_back == null) {
            _front = null;
        } else {
            _back._next = null;
        }
        return a;
    }

    /**
     *
     * @return a string representation of the IntDList in the form
     *  [] (empty list) or [1, 2], etc..
     *  Hint:
     *  String a = "a";
     *  a += "b";
     *  System.out.println(a); //prints ab
     */
    public String toString() {
        String out = new String();
        out += "[";
        for (DNode p = _front; p != null; p = p._next) {
            if (p != _front) {
                out += ", ";
            }
            out += p._val;
        }
        out += "]";
        return out.toString();
    }

    /* DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information! */
    protected static class DNode {
        protected DNode _prev;
        protected DNode _next;
        protected int _val;

        protected DNode(int val) {
            this(null, val, null);
        }

        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
