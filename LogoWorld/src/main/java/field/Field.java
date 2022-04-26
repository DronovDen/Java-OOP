package field;

import java.util.Arrays;

public class Field {
    public static final char FILLED = '#';
    public static final char EMPTY = '_';

    private final FieldSize size;
    private final char[][] cells;

    /**
     * Creates {@code Field} instance with given {@code FieldSize size}
     *
     * @param size to initialize created field size
     */
    public Field(FieldSize size) {
        size.isValid();
        this.size = size;

        this.cells = new char[size.width][size.height];
        for (char[] row : cells) {
            Arrays.fill(row, EMPTY);
        }
    }

    /**
     *
     * @return the two-dimensional array of cells that represents field of game
     */
    public char[][] getField() {
        return this.cells;
    }

    /**
     *
     * @return field size
     */
    public FieldSize getSize() {
        return this.size;
    }

    /**
     * Sets a certain cell with type {@code FILLED} -- "#" or {@code EMPTY} -- "_"
     *
     * @param coords represents coordinates of cell whose type needs to be changed
     * @param fill {@code true} if needed to fill cell
     *                         and {@code false} if needed to make cell empty
     */
    public void setCell(Coordinates coords, boolean fill) {
        coords.validate(size);
        if (fill) {
            cells[coords.x][coords.y] = FILLED;
        } else {
            cells[coords.x][coords.y] = EMPTY;
        }
    }
}
