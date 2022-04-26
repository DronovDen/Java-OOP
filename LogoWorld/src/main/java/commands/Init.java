package commands;

import field.Coordinates;
import field.FieldSize;
import game.Environment;
import status.Status;
import status.StatusCode;

public class Init extends Command {
    public Init(Environment environment) {
        super(environment);
    }

    @Override
    public boolean isValidArgsNum(String[] arguments) {
        return (arguments.length == 5);
    }

    @Override
    public Status execute() {
        Integer width = Integer.parseInt(arguments[1]);
        Integer height = Integer.parseInt(arguments[2]);
        if(width <= 0 || height <= 0){
            return new Status(StatusCode.ERROR, "Incorrect field size!");
        }
        FieldSize fieldSize = new FieldSize(width, height);

        Integer x = Integer.parseInt(arguments[3]);
        Integer y = Integer.parseInt(arguments[4]);
        Coordinates turtleCoords = new Coordinates(x, y);

        environment.initialize(fieldSize, turtleCoords);
        return new Status(StatusCode.SUCCESS, "Successfully initialized field size and turtle coords");
    }
}
