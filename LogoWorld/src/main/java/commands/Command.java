package commands;

import game.Environment;
import status.Status;

import java.util.List;

public abstract class Command {
    protected String[] arguments;
    protected Environment environment;
    protected Integer argsNumber = 0;

    /**
     * Creates Command instance to be executed
     *
     * @param env used for initializing commands (where to execute)
     */
    public Command(Environment env) {
        this.environment = env;
    }

    public abstract Status execute();

    /**
     *
     * @param arguments for using in command execution
     * @return {@code true} if number of arguments is appropriate for specific command
     */
    public abstract boolean isValidArgsNum(String[] arguments);

    public void initialize(String[] args) {
        this.arguments = args;
    }

}
