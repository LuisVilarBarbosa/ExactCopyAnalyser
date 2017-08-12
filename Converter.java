import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Converter {

    public static ArrayList<File> convertToArrayListOfValues(HashMap<String, File> source) {
        ArrayList<File> converted = new ArrayList<>();
        for (File f : source.values())
            converted.add(f);
        return converted;
    }
}
