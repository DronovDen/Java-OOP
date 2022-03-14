package alphabetLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MapLoader {
    //HashMap похож на массив или список, если бы у них
    //в качестве индексов выступали слова (String), а не числа.

    //Ключ - буква(символ), Значение - символ, закодированный в последовательность "*" и "-"
    //При декодировании - наоборот
    private final Map<String, String> encodeAlphabet = new HashMap<>();
    private final Map<String, String> decodeAlphabet = new HashMap<>();

    public MapLoader(String alphabetFileName){
        try {
            Path inPath = Paths.get(System.getProperty("user.dir"), "src", alphabetFileName);
            File alphabetFile = new File(inPath.normalize().toString());
            BufferedReader alphabetReader = new BufferedReader(new FileReader(alphabetFile));
            while (true) {
                String str = alphabetReader.readLine();
                if (str == null) break;
                String[] letters = str.split(" ");
                String normChar = letters[0];
                String morseChar = letters[1];
                encodeAlphabet.put(normChar, morseChar);
                decodeAlphabet.put(morseChar, normChar);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public Map<String, String> getEncodeMap(){
        return encodeAlphabet;
    }

    public Map<String, String> getDecodeMap(){
        return decodeAlphabet;
    }

}
