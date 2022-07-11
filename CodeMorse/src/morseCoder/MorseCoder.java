package morseCoder;

import statistics.SymbolCounter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


abstract class MorseCoder {
    protected BufferedReader inputFile;
    protected PrintStream outputStream;
    protected PrintStream statisticStream;
    protected Map<String, String> morseAlphabet;
    protected Set<SymbolCounter> symbolStatistics;

    public MorseCoder(String inputFileName) {
        try {
            Path inPath = Paths.get(System.getProperty("user.dir"), "resources", inputFileName);
            File inputFile = new File(inPath.normalize().toString());
            this.inputFile = new BufferedReader(new FileReader(inputFile));

            Path outPath = Paths.get(System.getProperty("user.dir"), "resources", "out.txt");
            File outFile = new File(outPath.normalize().toString());
            this.outputStream = new PrintStream(outFile);

            Path statPath = Paths.get(System.getProperty("user.dir"), "resources", "statistics.txt");
            File statFile = new File(statPath.normalize().toString());
            this.statisticStream = new PrintStream(statFile);

            this.symbolStatistics = new HashSet<>();

        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
    }

    public void writeStatistic() {
        List<SymbolCounter> statList = new ArrayList<>(symbolStatistics);
        for (SymbolCounter symbol : statList) {
            statisticStream.println("'" + symbol.getSymbol() + "'" + " - " + symbol.getFrequency());
        }
    }
}
