import java.io.File;
import java.util.HashMap;

public class Loader {

    public static void getFiles(String baseDir, File file, HashMap<String, File> files) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory())
                getFiles(baseDir, f, files);
            else {
                String dir = f.getAbsolutePath().substring(baseDir.length());
                files.put(dir, f);
            }
        }
    }
}
