package game;

import field.Coordinates;
import field.Field;
import field.FieldSize;

public class Environment {
    public Turtle turtle;
    public Field field;
    public boolean isInitialized = false;

    /**
     * Initializes the environment with new filed, turtle and its coordinates
     * and sets {@code isInitialized} flag with true
     *
     * @param fieldSize is a size of new field
     * @param coords is coordinates of new turtle
     */
    public void initialize(FieldSize fieldSize, Coordinates coords) {
        field = new Field(fieldSize);
        turtle = new Turtle(coords, fieldSize);
        isInitialized = true;
    }
}
