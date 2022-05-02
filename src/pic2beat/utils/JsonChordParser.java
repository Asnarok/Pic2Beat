package pic2beat.utils;

import com.google.gson.*;

import pic2beat.harmonia.Chord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JsonChordParser {

    public static final Map<String, Map<String, Integer>> MAJOR;

    static {
        MAJOR = new HashMap<>();
        Gson gson = new Gson();

        try {
            String content = Files.readString(Paths.get("chords.json"));

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

                    //System.out.println("degree " +degree+" / proba "+proba);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
