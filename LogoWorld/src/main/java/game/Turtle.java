package game;

import field.Coordinates;
import field.FieldSize;

public class Turtle {
    private final FieldSize fieldSize;
    private Coordinates coordinates;
    private boolean stylusMode;

    /**
     * Creates new turtle with the given coordinates and field size,
     * also sets stylus as turned off
     *
     * @param coords is coordinates for turtle to be set
     * @param size is size of field where turtle will move around
     */
    public Turtle(Coordinates coords, FieldSize size) {
        this.coordinates = coords;
        this.fieldSize = size;
        this.stylusMode = false;
    }

    /**
     *
     * @return current coordinates of turtle
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * Sets new coordinates of turtle
     * @param coords - new coordinates for turtle
     */
    public void setCoordinates(Coordinates coords) {
        coords.validate(fieldSize);
        this.coordinates = coords;
    }

    /**
     * Returns current mode of stylus
     *
     * @return {@code true} - stylus is turned on (turtle is drawing on field)
     *         {@code false} - stylus is turned off (turtle is not drawing on field)
     */
    public boolean getStylusMode() {
        return this.stylusMode;
    }

    /**
     * Sets the mode for stylus
     * @param newMode {@code true} if we want turtle to draw
     *                            {@code false} if we don't want it to draw
     */
    public void setStylusMode(boolean newMode) {
        this.stylusMode = newMode;
    }
}
