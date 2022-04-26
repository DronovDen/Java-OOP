package commands;

import game.Environment;
import game.Game;
//import org.apache.log4j.Logger;
import java.util.logging.Logger;
import status.Status;
import status.StatusCode;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class CommandParser {
    public static final Logger logger = Logger.getLogger(Game.class.toString());

    private final CommandsFactory commandsFactory;
    private final Map<String, String> commandsClasses;

    /**
     * Creates CommandParser instance
     *
     * @param env used to initialize environment where factory works
     */
    public CommandParser(Environment env) {
        logger.info("Creating command parser");
        this.commandsFactory = new CommandsFactory(env);
        this.commandsClasses = commandsFactory.getCommandsClasses();
        logger.info("Successfully created command parser");
    }

    /**
     * Tries to parse string to command and execute it and fills {@code Status} with code and message
     *
     * @param arguments is a string that contains command with its arguments and needed to be parsed
     * @return {@code Status} of parsed and executed command, that contains string with short description
     */
    public Status parseCommand(String[] arguments) {
        logger.info("Scanning string of arguments");
        if (arguments.length == 0) {
            Scanner scanner = new Scanner(System.in);
            arguments = scanner.nextLine().split("\\s+");
        }
        Status gameStatus = new Status(StatusCode.ERROR, "");

        logger.info("Command handling");
        if (arguments.length == 0) {
            gameStatus.statusMessage = "Empty command string!";
            logger.info("No command entered");
        } else if (!commandsClasses.containsKey(arguments[0].toLowerCase(Locale.ROOT))) {
            gameStatus.statusMessage = "No such command!";
            logger.info("Command does not exist");
        } else {
            logger.info("Getting command instance");
            Command commandToExec = commandsFactory.getCommandInstance(arguments);
            logger.info("Checking validity of arguments number");
            if (commandToExec.isValidArgsNum(arguments)) {
                logger.info("Trying to execute command");
                gameStatus = commandToExec.execute();
                logger.info("Ended command execution");
            } else gameStatus.statusMessage = "Wrong arguments number!";
        }
        return gameStatus;
    }

}
