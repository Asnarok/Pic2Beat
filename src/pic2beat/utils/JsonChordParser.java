package pic2beat.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonChordParser {

    public static final Map<String, Map<String, Integer>> MAJOR;
    public static final Map<String, Map<String, Integer>> MINOR;


    /*
     * Reads the json chord progression database and caches it as matrices.
     * The matrices contain strings that represent chords in a relative way (not in a key yet)
     */
    static {
        MAJOR = new HashMap<>();
        MINOR = new HashMap<>();

        try {
            String content = FileUtils.resourceFileAsString("chords.json"); // in jar
            if(content == null) {
                content = Files.readString(Path.of("chords.json")); // on disk
            }

            if(content == null) {
                throw new FileNotFoundException();
            }

            JsonElement element = new JsonParser().parse(content);
            JsonObject jsonObject = element.getAsJsonObject().getAsJsonObject("MAJOR");

            for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                final String outerChord = entry.getKey();

                MAJOR.put(outerChord, new HashMap<>());

                final JsonArray jarray = entry.getValue().getAsJsonArray();

                for(JsonElement chord : jarray) {
                    final JsonObject chordObj = chord.getAsJsonObject();

                    final String degree = chordObj.get("degree").getAsString();
                    final int proba = chordObj.get("proba").getAsInt();

                    MAJOR.get(outerChord).put(degree, proba);
                }
            }

            element = new JsonParser().parse(content);
            jsonObject = element.getAsJsonObject().getAsJsonObject("MINOR");

            for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                final String outerChord = entry.getKey();

                MINOR.put(outerChord, new HashMap<>());

                final JsonArray jarray = entry.getValue().getAsJsonArray();

                for(JsonElement chord : jarray) {
                    final JsonObject chordObj = chord.getAsJsonObject();

                    final String degree = chordObj.get("degree").getAsString();
                    final int proba = chordObj.get("proba").getAsInt();

                    MINOR.get(outerChord).put(degree, proba);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
