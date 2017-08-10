import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ExactCopyAnalyser {

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
            Loader.getFiles(dir1, f1, source);
            Loader.getFiles(dir2, f2, destination);

            ArrayList<File> notFoundOnDestination = Checker.findNonExistingOnDestination(source, destination);
            ArrayList<File> notFoundOnSource = Checker.findNonExistingOnDestination(destination, source);
            System.out.println("Not found on source: " + notFoundOnSource.size());
            System.out.println("Not found on destiny: " + notFoundOnDestination.size());

            HashMap<File, File> notEqual = Comparator.compareContent(source, destination);
            System.out.println("Not equal content: " + notEqual.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
