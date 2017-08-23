package logic;

import objects.File;

import java.nio.file.NotDirectoryException;
import java.util.LinkedHashMap;

public class Loader {
    private static final int defaultInitialCapacity = 16;
    private static final float defaultLoadFactor = 0.75f;
    private static final boolean accessOrder = false;   // To use insertion order.

    public static LinkedHashMap<String, File> getDirectoryFiles(String baseDirectory) throws NotDirectoryException {
        File directory = new File(baseDirectory);
        if (!directory.isDirectory())
            throw new NotDirectoryException(baseDirectory);

        LinkedHashMap<String, File> files = new LinkedHashMap<>(defaultInitialCapacity, defaultLoadFactor, accessOrder);
        getDirectoryFilesAux(baseDirectory, directory, files);
        return files;
    }

    private static void getDirectoryFilesAux(String baseDirectory, File directory, LinkedHashMap<String, File> files) {
        for (File f : directory.listFiles()) {
            if (f.isDirectory())
                getDirectoryFilesAux(baseDirectory, f, files);
            else {
                String dir = f.getAbsolutePath().substring(baseDirectory.length());
                files.put(dir, f);
            }
        }
    }
}
