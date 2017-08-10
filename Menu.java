import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private static int EXIT = 0;

    public void start() {
        int option;
        StringBuilder sb = new StringBuilder();
        sb.append("\nOptions:\n")
                .append("1. Compare the content of all corresponding files in two directories.\n")
                .append(EXIT).append(". Exit.\n");

        do {
            display(sb.toString());
            option = selectOption(EXIT, 1);

            try {
                switch (option) {
                    case 1:
                        compareDirectories();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (option != EXIT);
    }

    private void compareDirectories() throws Exception {
        String dir1 = getDirectory("Source directory: ");
        String dir2 = getDirectory("Destination directory: ");
        dir1 = adjustDirectory(dir1);
        dir2 = adjustDirectory(dir2);

        HashMap<String, File> source = new HashMap<>();
        HashMap<String, File> destination = new HashMap<>();

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
    }

    private int selectOption(int begin, int end) {
        int option;
        boolean invalid = true;
        Scanner scanner = new Scanner(System.in);
        do {
            display("Select an option (example: " + begin + "): ");
            option = scanner.nextInt();
            if (option >= begin && option <= end)
                invalid = false;
            else
                display("Invalid option.\n");
        } while (invalid);
        return option;
    }

    private String getDirectory(String message) {
        display(message);
        Scanner scanner = new Scanner(System.in);
        String dir = scanner.next();
        return dir;
    }

    private void display(String str) {
        System.out.print(str);
        System.out.flush();
    }

    private String adjustDirectory(String dir) {
        String newDir = dir;
        if (newDir.endsWith("/") || newDir.endsWith("\\"))
            newDir = newDir.substring(0, newDir.length() - 1);
        return newDir;
    }

    private File getFile(String dir) throws Exception {
        File f = new File(dir);
        if (!f.isDirectory())
            throw new Exception("'" + dir + "' is not a directory.");
        return f;
    }
}
