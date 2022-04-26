package view;

import field.Coordinates;
import field.FieldSize;
import game.Environment;
//import org.apache.log4j.*;
import java.util.logging.Logger;

public class View {
    private final Environment environment;
    //public static final Logger logger = Logger.getLogger(View.class);
    public static final Logger logger = Logger.getLogger(View.class.toString());

    /**
     * Creates new view for drawing game field
     *
     * @param env - environment where field and turtle are located
     */
    public View(Environment env) {
        logger.info("Creating view");
        this.environment = env;
        logger.info("Successfully created view");
    }

    /**
     * Draws full field with all previously painted cells and the turtle on it
     * "@" is a symbol that represents turtle
     */
    public void draw() {
        logger.info("Drawing field");
        FieldSize size = environment.field.getSize();
        char[][] cells = environment.field.getField();
        Coordinates turtleCoords = environment.turtle.getCoordinates();

        for (int i = 0; i < size.height; ++i) {
            for (int j = 0; j < size.width; ++j) {
                if (turtleCoords.x == j && turtleCoords.y == i) {
                    System.out.print("@");
                } else {
                    System.out.print(cells[j][i]);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        logger.info("Field drawn successfully");
    }
}
