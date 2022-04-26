package field;

import java.util.HashMap;
import java.util.Locale;

public class Directions {
    /**
     *
     */
    public static final HashMap<String, Coordinates> directions = new HashMap<String, Coordinates>() {{
        put("u", new Coordinates(0, -1));
        put("d", new Coordinates(0, 1));
        put("l", new Coordinates(-1, 0));
        put("r", new Coordinates(1, 0));
    }};


    /**
     * Returns {@code Coordinates} of necessary direction
     *
     * @param direction is a name of direction which coordinates needed to return
     * @return {@code Coordinates} of certain direction movement
     */
    public Coordinates getCoordinates(String direction) {
        return directions.get(direction.toLowerCase(Locale.ROOT));
    }

}
