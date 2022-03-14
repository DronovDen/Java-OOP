package morseCoder;

import alphabetLoader.MapLoader;
import statistics.SymbolCounter;

import java.io.IOException;
import java.util.Locale;

public class MorseDecoder extends MorseCoder {
    public MorseDecoder(String inputFile) {
        super(inputFile);
        morseAlphabet = new MapLoader("alphabet.txt").getDecodeMap();
    }

    public void decode() {
        try {
            while (true) {
                StringBuilder encodedLine = new StringBuilder();
                String line = inputFile.readLine();
                if (line == null) break;
                String[] words = line.split("   "); //3 spaces
                for (String word : words) {
                    String[] wordCharacters = word.split(" "); //1 space
                    for (String character : wordCharacters) {
                        String encodedChar = morseAlphabet.get(character.toUpperCase(Locale.ROOT));
                        if (encodedChar != null) {
                            encodedLine.append(encodedChar);
                            encodedLine.append(""); //empty symbol
                            symbolStatistics.add(new SymbolCounter(character.toUpperCase(Locale.ROOT).charAt(0)));
                        }
                    }
                    encodedLine.append(" "); //1 space - end of symbol
                }
                outputStream.println(encodedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
