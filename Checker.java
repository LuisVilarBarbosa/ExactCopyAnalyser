import java.io.File;
import java.util.HashMap;

public class Checker {

    public static HashMap<String, File> findNonExistingOnDestination(HashMap<String, File> source, HashMap<String, File> destination) {
        HashMap<String, File> notFound = new HashMap<>();
        for (String key1 : source.keySet()) {
            File f1 = source.get(key1);
            File f2 = destination.get(key1);
            if (f2 == null)
                notFound.put(key1, f1);
        }
        return notFound;
    }
}
