package amazons;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static java.lang.Math.abs;

import static amazons.Utils.*;

/** Represents a position on an Amazons board.  Positions are numbered
 *  from 0 (lower-left corner) to 99 (upper-right corner).  Squares
 *  are immutable and unique: there is precisely one square created for
 *  each distinct position.  Clients create squares using the factory method
 *  sq, not the constructor.  Because there is a unique Square object for each
 *  position, you can freely use the cheap == operator (rather than the
 *  .equals method) to compare Squares, and the program does not waste time
 *  creating the same square over and over again.
 *  @author
 */
final class Square {

    /** The regular expression for a square designation (e.g.,
     *  a3). For convenience, it is in parentheses to make it a
     *  group.  This subpattern is intended to be incorporated into
     *  other pattern that contain square designations (such as
     *  patterns for moves). */
    static final String SQ = "([a-j](?:[1-9]|10))";

    /** Return my row position, where 0 is the bottom row. */
    int row() {
        return _row;
    }

    /** Return my column position, where 0 is the leftmost column. */
    int col() {
        return _col;
    }

    /** Return my index position (0-99).  0 represents square a1, and 99
     *  is square j10. */
    int index() {
        return _index;
    }

    /** Return true iff THIS - TO is a valid queen move. FIXED */
    boolean isQueenMove(Square to) {
        if(this == to) {
            return false;
        }
        if(this.row() != to.row() && this.col() != to.col() && abs(this.row() - to.row()) != abs(this.col() - to.col())) {
            return false;
        }
        return true;
    }

    /** Definitions of direction for queenMove.  DIR[k] = (dcol, drow)
     *  means that to going one step from (col, row) in direction k,
     *  brings us to (col + dcol, row + drow). */
    private static final int[][] DIR = {
        { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
        { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };

    /** Return the Square that is STEPS>0 squares away from me in direction
     *  DIR, or null if there is no such square.
     *  DIR = 0 for north, 1 for northeast, 2 for east, etc., up to 7 for west.
     *  If DIR has another value, return null. Thus, unless the result
     *  is null the resulting square is a queen move away rom me. FIXED */
    Square queenMove(int dir, int steps) {
        if(dir < 0 || dir > 7) {
            throw error("Wrong direction");
        }
        if(steps <= 0) {
            throw error("Wrong steps");
        }
        int col = this.col() + steps * DIR[dir][0];
        int row = this.row() + steps * DIR[dir][1];
        return sq(col, row);
    }

    /** Return the direction (an int as defined in the documentation
     *  for queenMove) of the queen move THIS-TO. FIXED */
    int direction(Square to) {
        assert isQueenMove(to);
        if (this.col() == to.col()) {
            if (this.row() < to.row()) {
                return 0;
            } else {
                return 4;
            }
        }
        if (this.row() == to.row()) {
            if (this.col() < to.col()) {
                return 2;
            } else {
                return 6;
            }
        }
        if (this.col() > to.col()) {
            if (this.row() < to.row()) {
                return 7;
            } else {
                return 5;
            }
        }
        if (this.col() < to.col()) {
            if (this.row() < to.row()) {
                return 1;
            } else {
                return 3;
            }
        }
        return -1;
    }
    @Override
    public String toString() {
        return _str;
    }

    /** Return true iff COL ROW is a legal square. */
    static boolean exists(int col, int row) {
        return row >= 0 && col >= 0 && row < Board.SIZE && col < Board.SIZE;
    }

    /** Return the (unique) Square denoting COL ROW. */
    static Square sq(int col, int row) {
        if (!exists(row, col)) {
            throw error("row or column out of bounds");
        }
        int index = col * 10 + row;
        return sq(index);
    }

    /** Return the (unique) Square denoting the position with index INDEX. */
    static Square sq(int index) {
        return SQUARES[index];
    }

    /** Return the (unique) Square denoting the position COL ROW, where
     *  COL ROW is the standard text format for a square (e.g., a4). FIXED */
    static Square sq(String col, String row) {
        if(col.length() != 1 || row.length() != 1) {
            throw error("row or column out of bounds");
        }
        char char_col = col.charAt(0);
        char char_row = row.charAt(0);
        int num_col = char_col - 'a';
        int num_row = Character.getNumericValue(char_row) - 1;
        return sq(num_col, num_row);
    }

    /** Return the (unique) Square denoting the position in POSN, in the
     *  standard text format for a square (e.g. a4). POSN must be a
     *  valid square designation. FIXED */
    static Square sq(String posn) {
        assert posn.matches(SQ);
        char char_col = posn.charAt(0);
        char char_row = posn.charAt(1);
        int num_col = char_col - 'a';
        int num_row = Character.getNumericValue(char_row) - 1;
        return sq(num_col, num_row);
    }

    /** Return an iterator over all Squares. */
    static Iterator<Square> iterator() {
        return SQUARE_LIST.iterator();
    }

    /** Return the Square with index INDEX. FIXED */
    private Square(int index) {
        if(index < 0 && index > 99) {
            throw error("index out of bounds");
        }
        _index = index;
        _row = index % 10;
        _col = index / 10;
        _str = String.format("%c%d", (char)('a' + _col) , _row + 1);
    }

    /** The cache of all created squares, by index. */
    private static final Square[] SQUARES =
        new Square[Board.SIZE * Board.SIZE];

    /** SQUARES viewed as a List. */
    private static final List<Square> SQUARE_LIST = Arrays.asList(SQUARES);

    static {
        for (int i = Board.SIZE * Board.SIZE - 1; i >= 0; i -= 1) {
            SQUARES[i] = new Square(i);
        }
    }

    /** My index position. */
    private final int _index;

    /** My row and column (redundant, since these are determined by _index). */
    private final int _row, _col;

    /** My String denotation. */
    private final String _str;

}