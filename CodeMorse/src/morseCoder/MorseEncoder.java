package morseCoder;

import alphabetLoader.MapLoader;
import statistics.SymbolCounter;

import java.io.IOException;
import java.util.Locale;

public class MorseEncoder extends MorseCoder {

    public MorseEncoder(String inputFile) {
        super(inputFile);
        morseAlphabet = new MapLoader("alphabet.txt").getEncodeMap();
    }

    public void encode() {
        try {
            while (true) {
                StringBuilder encodedLine = new StringBuilder();
                String line = inputFile.readLine();
                if (line == null) break;
                String[] words = line.split(" ");//1 space
                for (String word : words) {
                    String[] wordCharacters = word.split("");
                    for (String character : wordCharacters) {
                        String encodedChar = morseAlphabet.get(character.toUpperCase(Locale.ROOT));
                        if (encodedChar != null) {
                            encodedLine.append(encodedChar);
                            encodedLine.append(" ");
                            symbolStatistics.add(new SymbolCounter(character.toUpperCase(Locale.ROOT).charAt(0)));
                        }
                    }
                    encodedLine.append("   "); //3 spaces - end of word
                }
                outputStream.println(encodedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

