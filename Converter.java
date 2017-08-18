import java.util.ArrayList;
import java.util.HashMap;

public class Converter {

    public static ArrayList<File> convertToArrayListOfValues(HashMap<String, File> map) {
        ArrayList<File> converted = new ArrayList<>(map.size());
        for (File f : map.values())
            converted.add(f);
        return converted;
    }
}
