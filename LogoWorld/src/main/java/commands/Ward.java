package commands;

import game.Environment;
import status.Status;
import status.StatusCode;

public class Ward extends Command {
    public Ward(Environment environment) {
        super(environment);
    }

    @Override
    public boolean isValidArgsNum(String[] arguments) {
        return (arguments.length == 1);
    }

    @Override
    public Status execute() {
        if (!environment.isInitialized) {
            return new Status(StatusCode.ERROR, "Enter Init command first!");
        }

        environment.turtle.setStylusMode(false);

        return new Status(StatusCode.SUCCESS, "Successfully turned off drawing mode!");
    }
}
