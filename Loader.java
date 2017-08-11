import java.io.File;
import java.util.HashMap;

public class Loader {

    public static void getFiles(String baseDir, File directory, HashMap<String, File> files) {
        for (File f : directory.listFiles()) {
            if (f.isDirectory())
                getFiles(baseDir, f, files);
            else {
                String dir = f.getAbsolutePath().substring(baseDir.length());
                files.put(dir, f);
            }
        }
    }
}
