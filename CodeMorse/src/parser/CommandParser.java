package parser;

import morseCoder.MorseDecoder;
import morseCoder.MorseEncoder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class CommandParser {
    private final String[] commands = {"encode", "decode"};

    public void parseCommand() {
        Scanner scanner = new Scanner(System.in);
        String[] arguments = scanner.nextLine().split(" ");

        if (arguments.length != 2) {
            System.out.println("Wrong arguments number!");
            parseCommand();
        }
        String command = arguments[0];
        String inputFile = arguments[1];

        if (!Arrays.asList(commands).contains(command)) {
            System.out.println("Wrong command format!");
            parseCommand();
        }
        //How to check existing of file????
        Path path = Paths.get(System.getProperty("user.dir"), "src", inputFile);
        File file = new File(path.toString());
        if (!file.exists()) {
            System.out.println("No such file!");
            parseCommand();
        }
        /*else if (!Files.exists(Path.of(inputFile))) { //arguments[1] - input file name
            System.out.println("No such file!");
            parseCommand();
        }*/
        else if (arguments[0].equals("encode")) {
            MorseEncoder encoder = new MorseEncoder(inputFile);
            encoder.encode();
            encoder.writeStatistic();
        } else if (arguments[0].equals("decode")) {
            MorseDecoder decoder = new MorseDecoder(inputFile);
            decoder.decode();
            decoder.writeStatistic();
        }
    }
}
