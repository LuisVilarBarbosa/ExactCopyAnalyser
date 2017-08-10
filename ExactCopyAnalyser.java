import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ExactCopyAnalyser {
    private static int BUFFER_SIZE = 10485760;   // 10 MB

    private static String adjustDirectory(String dir) {
        String newDir = dir;
        if (newDir.endsWith("/") || newDir.endsWith("\\"))
            newDir = newDir.substring(0, newDir.length() - 1);
        return newDir;
    }

    private static File getFile(String dir) throws Exception {
        File f = new File(dir);
        if (!f.isDirectory())
            throw new Exception("'" + dir + "' is not a directory.");
        return f;
    }

    private static void getFiles(String baseDir, File file, HashMap<String, File> files) {
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

    private static ArrayList<File> findNonExistingOnDestination(HashMap<String, File> source, HashMap<String, File> destination) {
        ArrayList<File> notFound = new ArrayList<>();
        for (String key1 : source.keySet()) {
            File f1 = source.get(key1);
            File f2 = destination.get(key1);
            if (f2 == null)
                notFound.add(f1);
        }
        return notFound;
    }

    private static HashMap<File, File> compareContent(HashMap<String, File> source, HashMap<String, File> destination) throws Exception {
        HashMap<File, File> notEqual = new HashMap<>();
        Set<String> keys = source.keySet();
        double interval = keys.size() > 100 ? keys.size() / 100 : 1;
        Iterator<String> it = keys.iterator();
        for (int i = 0; it.hasNext(); ) {
            for (int j = 0; j < interval && it.hasNext(); j++, i++) {
                String key = it.next();
                File f2 = destination.get(key);
                if (f2 != null) {
                    File f1 = source.get(key);
                    if (!areFilesEqual(f1, f2))
                        notEqual.put(f1, f2);
                }
            }
            double percentage = i * 100 / keys.size();
            System.out.println("Compared " + percentage + "%.");
        }
        return notEqual;
    }

    private static boolean areFilesEqual(File f1, File f2) throws Exception {
        long f1Length = f1.length();
        if (f1Length != f2.length() || f1.lastModified() != f2.lastModified())
            return false;

        FileInputStream fis1 = new FileInputStream(f1.getAbsolutePath());
        FileInputStream fis2 = new FileInputStream(f2.getAbsolutePath());

        byte data1[] = new byte[BUFFER_SIZE];
        byte data2[] = new byte[BUFFER_SIZE];
        boolean equal = true;
        boolean readError = false;

        for (long i = 0; i < f1Length && equal; i += BUFFER_SIZE) {
            int read1 = fis1.read(data1);
            int read2 = fis2.read(data2);

            if (read1 != read2) {
                readError = true;
                break;
            }

            if (!Arrays.equals(data1, data2))
                equal = false;
        }

        fis1.close();
        fis2.close();

        if (readError)
            throw new Exception("An error occurred reading the data from the files.");

        return equal;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ExactCopyAnalyser <source> <destination>");
            return;
        }

        String dir1 = args[0];
        String dir2 = args[1];
        dir1 = adjustDirectory(dir1);
        dir2 = adjustDirectory(dir2);

        HashMap<String, File> source = new HashMap<>();
        HashMap<String, File> destination = new HashMap<>();

        try {
            File f1 = getFile(dir1);
            File f2 = getFile(dir2);
            getFiles(dir1, f1, source);
            getFiles(dir2, f2, destination);

            ArrayList<File> notFoundOnDestination = findNonExistingOnDestination(source, destination);
            ArrayList<File> notFoundOnSource = findNonExistingOnDestination(destination, source);
            System.out.println("Not found on source: " + notFoundOnSource.size());
            System.out.println("Not found on destiny: " + notFoundOnDestination.size());

            HashMap<File, File> notEqual = compareContent(source, destination);
            System.out.println("Not equal content: " + notEqual.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
