import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private static int EXIT = 0;
    private Logger logger = null;

    public void start() {
        int option;
        StringBuilder sb = new StringBuilder();
        sb.append("\nOptions:\n")
                .append("1. Compare the content of all corresponding files in two directories with the same structure.\n")
                .append("2. Compare the content of two files.\n")
                .append("3. Compare all corresponding files in two directories with the same structure by size and modification date.\n")
                .append("4. Compare two files by size and modification date.\n")
                .append("5. List files that are in the destination directory, but are not in the source directory and have equal files placed on other location in the source. (Possibly time consuming operation)\n")
                .append("6. List files that are in the source directory, but are not in the destination directory and have equal files placed on other location in the destination. (Possibly time consuming operation)\n")
                .append("7. Remove files that are in the destination directory, but are not in the source directory and have equal files placed on other location in the source. (Possibly time consuming operation)\n")
                .append("8. Remove files that are in the source directory, but are not in the destination directory and have equal files placed on other location in the destination. (Possibly time consuming operation)\n")
                .append("9. List all duplicates in a given directory. (Possibly time consuming operation)\n")
                .append(EXIT).append(". Exit.\n");

        do {
            display(sb.toString());
            option = selectOption(EXIT, 9);

            try {
                if (option != 0) {
                    logger = new Logger();
                    display("Extra data will be logged on '" + logger.getPath() + "'.\n");
                }

                switch (option) {
                    case 1:
                        compareDirectoriesByContent();
                        break;
                    case 2:
                        compareFilesByContent();
                        break;
                    case 3:
                        compareDirectoriesBySizeAndDate();
                        break;
                    case 4:
                        compareFilesBySizeAndDate();
                        break;
                    case 5:
                        listFilesThatAreNotInSourceButHaveCopiesThere();
                        break;
                    case 6:
                        listFilesThatAreNotInDestinationButHaveCopiesThere();
                        break;
                    case 7:
                        removeFilesThatAreNotInSourceButHaveCopiesThere();
                        break;
                    case 8:
                        removeFilesThatAreNotInDestinationButHaveCopiesThere();
                        break;
                    case 9:
                        listDuplicates();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (logger != null)
                        logger.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } while (option != EXIT);
    }

    private void compareDirectoriesByContent() throws Exception {
        compareDirectories(true);
    }

    private void compareFilesByContent() throws Exception {
        compareFiles(true);
    }

    private void compareDirectoriesBySizeAndDate() throws Exception {
        compareDirectories(false);
    }

    private void compareFilesBySizeAndDate() throws Exception {
        compareFiles(false);
    }

    private void compareDirectories(boolean compareContent) throws Exception {
        HashMap<String, File> source = new HashMap<>();
        HashMap<String, File> destination = new HashMap<>();
        getDirectoriesFiles(source, destination);

        ArrayList<File> notFoundOnDestination = Checker.findNonExistingOnDestination(source, destination);
        ArrayList<File> notFoundOnSource = Checker.findNonExistingOnDestination(destination, source);
        System.out.println("Not found on source: " + notFoundOnSource.size());
        System.out.println("Not found on destiny: " + notFoundOnDestination.size());

        Comparator comparator = new Comparator(compareContent);
        HashMap<File, File> notEqual = comparator.compareDirectories(source, destination);
        System.out.println("Not equal content: " + notEqual.size());
        list(notEqual);
    }

    private void compareFiles(boolean compareContent) throws Exception {
        String path1 = getString("File 1: ");
        String path2 = getString("File 2: ");
        File f1 = getFile(path1);
        File f2 = getFile(path2);

        Comparator comparator = new Comparator(compareContent);
        if (comparator.areFilesEqual(f1, f2))
            display("The files are equal.\n");
        else
            display("The files are not equal.\n");
    }

    private ArrayList<ArrayList<File>> listFilesThatAreNotInSourceButHaveCopiesThere() throws Exception {
        HashMap<String, File> source = new HashMap<>();
        HashMap<String, File> destination = new HashMap<>();
        getDirectoriesFiles(source, destination);

        Finder finder = new Finder();
        ArrayList<ArrayList<File>> repeatedFiles = finder.findFilesThatAreNotInSourceButHaveCopiesThere(source, destination);

        if (repeatedFiles.isEmpty())
            display("No repeated files have been found.\n");
        else
            logger.list("Files that are not in source but have copies there:", repeatedFiles);
        return repeatedFiles;
    }

    private ArrayList<ArrayList<File>> listFilesThatAreNotInDestinationButHaveCopiesThere() throws Exception {
        HashMap<String, File> source = new HashMap<>();
        HashMap<String, File> destination = new HashMap<>();
        getDirectoriesFiles(source, destination);

        Finder finder = new Finder();
        ArrayList<ArrayList<File>> repeatedFiles = finder.findFilesThatAreNotInDestinationButHaveCopiesThere(source, destination);

        if (repeatedFiles.isEmpty())
            display("No repeated files have been found.\n");
        else {
            logger.list("Files that are not in destination but have copies there:", repeatedFiles);
        }
        return repeatedFiles;
    }

    private void removeFilesThatAreNotInSourceButHaveCopiesThere() throws Exception {
        ArrayList<ArrayList<File>> repeatedFiles = listFilesThatAreNotInSourceButHaveCopiesThere();
        if (!repeatedFiles.isEmpty() && confirm()) {
            display("Removing files...");
            Changer changer = new Changer();
            changer.deleteFirstFileInList(repeatedFiles);
        }
    }

    private void removeFilesThatAreNotInDestinationButHaveCopiesThere() throws Exception {
        ArrayList<ArrayList<File>> repeatedFiles = listFilesThatAreNotInDestinationButHaveCopiesThere();
        if (!repeatedFiles.isEmpty() && confirm()) {
            display("Removing files...");
            Changer changer = new Changer();
            changer.deleteFirstFileInList(repeatedFiles);
        }
    }

    private void listDuplicates() throws Exception {
        HashMap<String, File> files = new HashMap<>();
        getDirectoryFiles("Directory: ", files);

        Finder finder = new Finder();
        ArrayList<ArrayList<File>> duplicates = finder.findDuplicates(files);

        if (duplicates.isEmpty())
            display("No duplicates files have been found.\n");
        else {
            int quantity = 0;
            long size = 0;
            for (ArrayList<File> list : duplicates)
                for (int i = 1; i < list.size(); i++) {
                    quantity++;
                    size += list.get(i).length();
                }
            String message = "Duplicated files: " + quantity + " = " + size + " bytes";
            logger.list(message, duplicates);
        }
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

    private boolean confirm() {
        boolean confirm = false;
        String line;
        boolean invalid = true;
        do {
            line = getString("Are you sure that you want to proceed? (Y/N): ");
            if (line.equals("Y") || line.endsWith("y")) {
                confirm = true;
                invalid = false;
            } else if (line.equals("N") || line.equals("n")) {
                confirm = false;
                invalid = false;
            } else
                display("Invalid option.\n");
        } while (invalid);
        return confirm;
    }

    private void getDirectoriesFiles(HashMap<String, File> source, HashMap<String, File> destination) throws Exception {
        getDirectoryFiles("Source directory: ", source);
        getDirectoryFiles("Destination directory: ", destination);
    }

    private void getDirectoryFiles(String message, HashMap<String, File> files) throws Exception {
        String dir = getString(message);
        dir = adjustDirectory(dir);
        File directory = getDirectory(dir);
        Loader.getFiles(dir, directory, files);
    }

    private String getString(String message) {
        display(message);
        Scanner scanner = new Scanner(System.in);
        String dir = scanner.nextLine();
        return dir;
    }

    private void list(HashMap<File, File> map) {
        StringBuilder sb = new StringBuilder();
        for (File f1 : map.keySet()) {
            File f2 = map.get(f1);
            sb.append(f1.getAbsolutePath()).append("\n")
                    .append(f2.getAbsolutePath()).append("\n\n");
        }
        display(sb.toString());
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

    private File getDirectory(String dir) throws Exception {
        File f = new File(dir);
        if (!f.isDirectory())
            throw new Exception("'" + dir + "' is not a directory.");
        return f;
    }

    private File getFile(String path) throws Exception {
        File f = new File(path);
        if (!f.isFile())
            throw new Exception("'" + path + "' is not a file. File extension missing?");
        return f;
    }
}
