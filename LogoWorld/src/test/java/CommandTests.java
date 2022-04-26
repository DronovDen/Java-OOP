import commands.CommandParser;
import field.Coordinates;
import field.FieldSize;
import game.Environment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTests {
    @Test
    void initTest() {
        CommandParser parser = new CommandParser(new Environment());
        String[] test1 = {"Init", "10", "10", "0", "0"};
        String[] test2 = {"Init", "10", "10", "-10", "-10"};
        String[] test3 = {"Init", "0", "10", "0", "0"};
        String[] test4 = {"Init", "-10", "10", "0", "0"};
        String[] test5 = {"Init", "10", "10", "10", "10"};
        String[] test6 = {"INIT", "10", "10", "10", "10"};
        String[] test7 = {"init", "10", "10", "10", "10"};

        assertEquals("Successfully initialized field size and turtle coords",
                parser.parseCommand(test1).statusMessage);
        assertEquals("Successfully initialized field size and turtle coords",
                parser.parseCommand(test2).statusMessage);
        assertEquals("Incorrect field size!", parser.parseCommand(test3).statusMessage);
        assertEquals("Incorrect field size!", parser.parseCommand(test4).statusMessage);
        assertEquals("Successfully initialized field size and turtle coords",
                parser.parseCommand(test5).statusMessage);
        assertEquals("Successfully initialized field size and turtle coords",
                parser.parseCommand(test6).statusMessage);
        assertEquals("Successfully initialized field size and turtle coords",
                parser.parseCommand(test7).statusMessage);
    }

    @Test
    void moveTest() {
        Environment environment = new Environment();
        CommandParser parser = new CommandParser(environment);
        environment.initialize(new FieldSize(10, 10), new Coordinates(1, 1));

        String[] test1 = {"move", "u", "1"};
        String[] test2 = {"MOVE", "d", "1"};
        String[] test3 = {"move", "ewe", "1"};
        String[] test4 = {"move", "u", "10"};
        String[] test5 = {"move", "r", "10"};
        String[] test6 = {"move", "l", "10"};
        String[] test7 = {"move", "d", "10"};

        assertEquals("Successfully moved!", parser.parseCommand(test1).statusMessage);
        assertEquals(new Coordinates(1, 0), environment.turtle.getCoordinates());

        assertEquals("Successfully moved!", parser.parseCommand(test2).statusMessage);
        assertEquals(new Coordinates(1, 1), environment.turtle.getCoordinates());

        assertEquals("Wrong direction!", parser.parseCommand(test3).statusMessage);

        assertEquals("Successfully moved!", parser.parseCommand(test4).statusMessage);
        assertEquals(new Coordinates(1, 1), environment.turtle.getCoordinates());

        assertEquals("Successfully moved!", parser.parseCommand(test5).statusMessage);
        assertEquals(new Coordinates(1, 1), environment.turtle.getCoordinates());

        assertEquals("Successfully moved!", parser.parseCommand(test6).statusMessage);
        assertEquals(new Coordinates(1, 1), environment.turtle.getCoordinates());

        assertEquals("Successfully moved!", parser.parseCommand(test7).statusMessage);
        assertEquals(new Coordinates(1, 1), environment.turtle.getCoordinates());
    }

    @Test
    void teleportTest() {
        Environment environment = new Environment();
        CommandParser parser = new CommandParser(environment);
        environment.initialize(new FieldSize(10, 10), new Coordinates(1, 1));

        String[] test1 = {"teleport"};
        String[] test2 = {"teleport", "10", "10"};
        String[] test3 = {"teleport", "-9", "-9"};

        assertEquals("Wrong arguments number!", parser.parseCommand(test1).statusMessage);

        assertEquals("Successfully teleported!", parser.parseCommand(test2).statusMessage);
        assertEquals(new Coordinates(0, 0), environment.turtle.getCoordinates());

        assertEquals("Successfully teleported!", parser.parseCommand(test3).statusMessage);
        assertEquals(new Coordinates(1, 1), environment.turtle.getCoordinates());
    }

    @Test
    void drawTest() {
        Environment environment = new Environment();
        CommandParser parser = new CommandParser(environment);
        environment.initialize(new FieldSize(10, 10), new Coordinates(1, 1));

        String[] test1 = {"draw", "updown", "rightleft"};
        String[] test2 = {"draw"};

        assertEquals("Wrong arguments number!", parser.parseCommand(test1).statusMessage);

        assertEquals("Successfully turned on drawing mode!", parser.parseCommand(test2).statusMessage);
        assertTrue(environment.turtle.getStylusMode());
    }

    @Test
    void wardTest() {
        Environment environment = new Environment();
        CommandParser parser = new CommandParser(environment);
        environment.initialize(new FieldSize(10, 10), new Coordinates(2, 2));

        String[] test1 = {"ward", "updown", "rightleft"};
        String[] test2 = {"ward"};

        assertEquals("Wrong arguments number!", parser.parseCommand(test1).statusMessage);

        assertEquals("Successfully turned off drawing mode!", parser.parseCommand(test2).statusMessage);
        assertFalse(environment.turtle.getStylusMode());
    }

}
