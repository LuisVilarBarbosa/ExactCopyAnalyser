import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private static int EXIT = 0;
    private Text text = new Text();
    private Logger logger = null;
    private int backtrackCounter = 0;

    public void start() {
        int option;

        do {
            display(text.getOptionsMsg());
            option = selectOption(EXIT, 7);

            try {
                if (option != EXIT) {
                    logger = new Logger();
                    display(text.getGeneratedLoggerMsg(logger.getPath()));
                }

                switch (option) {
                    case 1:
                        compareDirectoriesWithSameStructure(true);
                        break;
                    case 2:
                        compareFiles(true);
                        break;
                    case 3:
                        compareDirectoriesWithSameStructure(false);
                        break;
                    case 4:
                        compareFiles(false);
                        break;
                    case 5:
                        listDir1FilesThatAreNotInDir2ButHaveCopiesThere();
                        break;
                    case 6:
                        removeDir1FilesThatAreNotInDir2ButHaveCopiesThere();
                        break;
                    case 7:
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

    public Text getText() {
        return text;
    }

    private void compareDirectoriesWithSameStructure(boolean compareContent) throws Exception {
        HashMap<String, File> dir1 = getDirectoryFiles(text.getDir1Msg());
        HashMap<String, File> dir2 = getDirectoryFiles(text.getDir2Msg());

        HashMap<String, File> notFoundOnDir2 = Finder.findDir1FilesNonExistingOnDir2(dir1, dir2);
        HashMap<String, File> notFoundOnDir1 = Finder.findDir1FilesNonExistingOnDir2(dir2, dir1);
        display(text.getNotFoundOnDir1Msg(notFoundOnDir1.size()));
        display(text.getNotFoundOnDir2Msg(notFoundOnDir2.size()));

        Comparator comparator = new Comparator(compareContent);
        HashMap<File, File> notEqual = comparator.compareDirectoriesWithSameStructure(dir1, dir2, this);
        String message = "Not equal content: " + notEqual.size();
        display(message + "\n");
        logger.list(message, notEqual);
    }

    private void compareFiles(boolean compareContent) throws Exception {
        String path1 = getString(text.getFile1Msg());
        String path2 = getString(text.getFile2Msg());
        File f1 = getFile(path1);
        File f2 = getFile(path2);

        Comparator comparator = new Comparator(compareContent);
        if (comparator.areFilesEqual(f1, f2))
            display("The files are equal.\n");
        else
            display("The files are not equal.\n");
    }

    private ArrayList<ArrayList<File>> listDir1FilesThatAreNotInDir2ButHaveCopiesThere() throws Exception {
        HashMap<String, File> dir1 = getDirectoryFiles(text.getDir1Msg());
        HashMap<String, File> dir2 = getDirectoryFiles(text.getDir2Msg());
        ArrayList<ArrayList<File>> copies = Finder.findDir1FilesThatAreNotInDir2ButHaveCopiesThere(dir1, dir2, this);

        if (copies.isEmpty())
            display("No repeated files have been found.\n");
        else {
            logger.list("Files that are not in destination but have copies there:", copies);
        }
        return copies;
    }

    private void removeDir1FilesThatAreNotInDir2ButHaveCopiesThere() throws Exception {
        ArrayList<ArrayList<File>> copies = listDir1FilesThatAreNotInDir2ButHaveCopiesThere();
        if (!copies.isEmpty() && confirm()) {
            display("Removing files...");
            Changer.deleteFirstFileInList(copies, this);
        }
    }

    private void listDuplicates() throws Exception {
        HashMap<String, File> files = getDirectoryFiles(text.getDirMsg());
        ArrayList<ArrayList<File>> duplicates = Finder.findDuplicates(files, this);

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
        Integer option = null;
        boolean invalid = true;
        do {
            String input = getString(text.getSelectOptionMsg(begin));
            if (input.matches("[0-9]+")) {
                option = Integer.parseInt(input);
                if (option >= begin && option <= end)
                    invalid = false;
            }
            if (invalid)
                display(text.getInvalidOptionMsg());
        } while (invalid);
        return option;
    }

    private boolean confirm() {
        boolean confirm = false;
        String line;
        boolean invalid = true;
        do {
            line = getString(text.getConfirmationMsg());
            if (line.equalsIgnoreCase(text.getConfirmInput())) {
                confirm = true;
                invalid = false;
            } else if (line.equalsIgnoreCase(text.getDenyInput())) {
                confirm = false;
                invalid = false;
            } else
                display(text.getInvalidOptionMsg());
        } while (invalid);
        return confirm;
    }

    private HashMap<String, File> getDirectoryFiles(String message) throws Exception {
        String dir = getString(message);
        dir = adjustDirectory(dir);
        HashMap<String, File> files = Loader.getDirectoryFiles(dir);
        return files;
    }

    private String getString(String message) {
        display(message);
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

    public void display(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < backtrackCounter; i++)
            sb.append("\b");
        System.out.print(sb);
        System.out.print(str);
        System.out.flush();
        backtrackCounter = 0;
    }

    public void displayProgress(int done, int total, int found) {
        double percentage = done * 100 / total;
        StringBuilder sb = new StringBuilder();
        sb.append(done).append(" / ").append(total).append(" = ").append(percentage).append("% ").append(text.getFoundMsg()).append(": ").append(found);
        display(sb.toString());
        backtrackCounter = sb.length();
    }

    private String adjustDirectory(String dir) {
        String newDir = dir;
        if (newDir.endsWith("/") || newDir.endsWith("\\"))
            newDir = newDir.substring(0, newDir.length() - 1);
        return newDir;
    }

    private File getFile(String path) throws Exception {
        File f = new File(path);
        if (!f.isFile())
            throw new Exception(text.getNotFileErrorMsg(path));
        return f;
    }
}
