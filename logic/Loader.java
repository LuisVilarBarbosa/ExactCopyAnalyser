package logic;

import objects.File;

import java.nio.file.NotDirectoryException;
import java.util.HashMap;

public class Loader {

    public static HashMap<String, File> getDirectoryFiles(String baseDirectory) throws NotDirectoryException {
        File directory = new File(baseDirectory);
        if (!directory.isDirectory())
            throw new NotDirectoryException(baseDirectory);

        HashMap<String, File> files = new HashMap<>();
        getDirectoryFilesAux(baseDirectory, directory, files);
        return files;
    }

    private static void getDirectoryFilesAux(String baseDirectory, File directory, HashMap<String, File> files) {
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
