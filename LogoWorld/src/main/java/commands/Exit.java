package commands;

import game.Environment;
import status.Status;
import status.StatusCode;

public class Exit extends Command {
    public Exit(Environment environment) {
        super(environment);
    }

    @Override
    public boolean isValidArgsNum(String[] arguments) {
        return (arguments.length == 1);
    }

    @Override
    public Status execute() {
        return new Status(StatusCode.END, "Exiting the game...");
    }
}
