package game;

import commands.CommandParser;
import status.Status;
import status.StatusCode;
import view.View;

//import org.apache.log4j.Logger;
import java.util.logging.Logger;

public class Game {
    public static final Logger logger = Logger.getLogger(Game.class.toString());

    private final CommandParser commandParser;
    private final View gameView;
    private final Environment environment;
    private Status executionStatus;

    /**
     * Creates new game instance to be played and creates new
     * environment, view and parser instances
     */
    public Game() {
        logger.info("Creating game");
        logger.info("Creating environment");
        environment = new Environment();
        logger.info("Successfully created environment");
        gameView = new View(environment);
        commandParser = new CommandParser(environment);
        executionStatus = new Status(StatusCode.RUN, "Starting execution");
        logger.info("Successfully created game");
    }

    /**
     * Starts the main game loop and begin to parse, execute commands
     * and draw game field
     */
    public void play() {
        logger.info("Staring game execution");
        while (executionStatus.statusCode != StatusCode.END) {
            System.out.print(executionStatus.statusMessage + "\n" + "~$ ");
            executionStatus = commandParser.parseCommand(new String[]{});
            if (environment.isInitialized) {
                gameView.draw();
            }
        }
        logger.info("Ended game execution");
    }
}
