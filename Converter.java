import java.util.ArrayList;
import java.util.HashMap;

public class Converter {

    public static ArrayList<String> convertToArrayListOfKeys(HashMap<String, File> map) {
        ArrayList<String> converted = new ArrayList<>(map.size());
        for (String key : map.keySet())
            converted.add(key);
        return converted;
    }
}
