package commands;

import field.Coordinates;
import field.Directions;
import game.Environment;
import status.Status;
import status.StatusCode;

public class Move extends Command {

    public Move(Environment environment) {
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
        if(!Directions.directions.containsKey(arguments[1])){
            return new Status(StatusCode.ERROR, "Wrong direction!");
        }

        int stepsNum = Integer.parseInt(arguments[2]);
        Directions direction = new Directions();

        Coordinates diff = direction.getCoordinates(arguments[1]).mul(stepsNum);

        Coordinates previousCoords = environment.turtle.getCoordinates();
        environment.turtle.setCoordinates(previousCoords.add(diff));

        if (environment.turtle.getStylusMode()) {
            int begin = Math.min(0, diff.x);
            int end = Math.max(0, diff.x);
            for (int i = begin; i <= end; ++i) {
                Coordinates toPaint = previousCoords.add(new Coordinates(i, 0));
                environment.field.setCell(toPaint, true);
            }
            begin = Math.min(0, diff.y);
            end = Math.max(0, diff.y);
            for (int i = begin; i <= end; ++i) {
                Coordinates toPaint = previousCoords.add(new Coordinates(0, i));
                environment.field.setCell(toPaint, true);
            }

        }
        return new Status(StatusCode.SUCCESS, "Successfully moved!");
    }
}
