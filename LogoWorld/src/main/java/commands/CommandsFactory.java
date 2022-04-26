package commands;

import game.Environment;
//import org.apache.log4j.Logger;
import java.util.logging.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;


public class CommandsFactory {
    public static final Logger logger = Logger.getLogger(CommandsFactory.class.toString());
    private final Map<String, Command> commandsInstances;
    private final Map<String, String> commandsClasses;
    private final Environment environment;

    /**
     * Creates CommandsFactory instance that contains maps of command instances
     * and their classes paths. Firstly loads the {@code  Properties properties} from properties file
     * and fills two maps using this props
     *
     * @param environment for initializing environment where factory will work
     */
    public CommandsFactory(Environment environment) {
        logger.info("Creating commands factory");
        this.environment = environment;

        /*Path path = Paths.get("src//main//resources//commands.properties");
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        logger.info("Loading commands properties");
        InputStream commandsStream = ClassLoader.getSystemResourceAsStream("commands.properties");

        Properties commandProps = new Properties();
        try {
            commandProps.load(commandsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        commandsInstances = new HashMap<>();
        commandsClasses = new HashMap<>();

        for (String property : commandProps.stringPropertyNames()) {
            //String value = commandProps.getProperty(property);
            commandsInstances.put(property.toLowerCase(Locale.ROOT), null);
            commandsClasses.put(property.toLowerCase(Locale.ROOT), commandProps.getProperty(property));
        }
    }

    /**
     * Creates command instance using reflection mechanics and puts it to {@code commandsInstances} map
     *
     * @param command for searching command in maps by its name
     */
    private void createCommandInstance(String command) {
        try {
            Class<?> commandClass = Class.forName(commandsClasses.get(command));
            Constructor<?> commandCtor = commandClass.getDeclaredConstructor(Environment.class);
            commandsInstances.put(command, (Command) commandCtor.newInstance(environment));
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Tries to get command instance from {@code commandsInstances} map or calls the function
     * for creating new instance
     *
     * @param args contains command name with its arguments
     * @return instance of created or taken from map command
     */
    public Command getCommandInstance(String[] args) {

        String cmd = args[0].toLowerCase(Locale.ROOT);
        if (commandsInstances.get(cmd) == null) {
            createCommandInstance(cmd);
        }
        Command command = commandsInstances.get(cmd);
        command.initialize(args /*arguments*/);
        return command;
    }

    /**
     *
     * @return compliance map of command names ant their classes paths
     */
    public Map<String, String> getCommandsClasses() {
        return commandsClasses;
    }
}
