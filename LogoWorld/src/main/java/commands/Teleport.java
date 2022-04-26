package commands;

import field.Coordinates;
import game.Environment;
import status.Status;
import status.StatusCode;

public class Teleport extends Command {
    public Teleport(Environment environment) {
        super(environment);
    }

    @Override
    public boolean isValidArgsNum(String[] arguments) {
        return (arguments.length == 3);
    }

    @Override
    public Status execute() {
        if (!environment.isInitialized) {
            return new Status(StatusCode.ERROR, "Enter Init command first!");
        }

        int newX = Integer.parseInt(arguments[1]);
        int newY = Integer.parseInt(arguments[2]);
        Coordinates newCoordinates = new Coordinates(newX, newY);

        environment.turtle.setCoordinates(newCoordinates);

        return new Status(StatusCode.SUCCESS, "Successfully teleported!");
    }
}
