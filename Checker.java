import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Checker {

    public static ArrayList<File> findNonExistingOnDestination(HashMap<String, File> source, HashMap<String, File> destination) {
        ArrayList<File> notFound = new ArrayList<>();
        for (String key1 : source.keySet()) {
            File f1 = source.get(key1);
            File f2 = destination.get(key1);
            if (f2 == null)
                notFound.add(f1);
        }
        return notFound;
    }
}
