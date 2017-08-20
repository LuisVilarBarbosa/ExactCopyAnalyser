package ui;

import logic.*;
import objects.File;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    private static int EXIT = 0;
    private Text text = new Text();
    private int backtrackCounter = 0;
    private RemainingTime remainingTime = null;

    public void start() {
        display(text.getOptionsMsg());
        int option;

        do {
            option = selectOption(EXIT, 8);

            try {
                if (option != EXIT)
                    executeOption(option);
            } catch (NotDirectoryException e) {
                display(text.getNotDirErrorMsg(e.getFile()));
            } catch (NoSuchFileException e) {
                display(text.getNotFileErrorMsg(e.getFile()));
            } catch (IOException e) {
                display(e.getMessage());
            }
        } while (option != EXIT);
    }

    public Text getText() {
        return text;
    }

    private void executeOption(int option) throws IOException {
        switch (option) {
            case 1:
                listCorrespondentsNotEqualInDirectoriesWithSameStructure(true);
                break;
            case 2:
                compareFiles(true);
                break;
            case 3:
                listCorrespondentsNotEqualInDirectoriesWithSameStructure(false);
                break;
            case 4:
                compareFiles(false);
                break;
            case 5:
                listDir1FilesWithoutCorrespondentInDir2ButWithCopiesThere();
                break;
            case 6:
                deleteDir1FilesWithoutCorrespondentInDir2ButWithCopiesThere();
                break;
            case 7:
                listDuplicates();
                break;
            case 8:
                listDir1FilesWithCopiesSomewhereInDir2();
                break;
            default:
                display(text.getInvalidOptionMsg());
                break;
        }
    }

    private void listCorrespondentsNotEqualInDirectoriesWithSameStructure(boolean compareContent) throws IOException {
        HashMap<String, File> dir1 = getDirectoryFiles(text.getDir1Msg());
        HashMap<String, File> dir2 = getDirectoryFiles(text.getDir2Msg());

        Finder finder = new Finder(this);
        HashMap<String, File> withoutCorrespondentOnDir2 = finder.findFiles1WithoutCorrespondentOnFiles2(dir1, dir2);
        HashMap<String, File> withoutCorrespondentOnDir1 = finder.findFiles1WithoutCorrespondentOnFiles2(dir2, dir1);
        display(text.getDir2FilesWithoutCorrespondentOnDir1Msg(withoutCorrespondentOnDir1.size()));
        display(text.getDir1FilesWithoutCorrespondentOnDir2Msg(withoutCorrespondentOnDir2.size()));

        Comparator comparator = new Comparator(compareContent, this);
        ArrayList<ArrayList<File>> notEqual = comparator.findNotEqualCorrespondentsInDirectoriesWithSameStructure(dir1, dir2);
        String message = text.getNotEqualCorrespondentsMsg(notEqual.size());
        display(message);
        log(message, notEqual);
    }

    private void compareFiles(boolean compareContent) throws IOException {
        String path1 = getString(text.getFile1Msg());
        String path2 = getString(text.getFile2Msg());
        File f1 = getFile(path1);
        File f2 = getFile(path2);

        Comparator comparator = new Comparator(compareContent, this);
        if (comparator.areFilesEqual(f1, f2))
            display(text.getEqualFilesMsg());
        else
            display(text.getNotEqualFilesMsg());
    }

    private ArrayList<File> listDir1FilesWithoutCorrespondentInDir2ButWithCopiesThere() throws IOException {
        HashMap<String, File> dir1 = getDirectoryFiles(text.getDir1Msg());
        HashMap<String, File> dir2 = getDirectoryFiles(text.getDir2Msg());
        Finder finder = new Finder(this);
        ArrayList<File> withCopies = finder.findFiles1WithoutCorrespondentInFiles2ButWithCopiesThere(dir1, dir2);
        String message = text.getDir1FilesWithoutCorrespondentInDir2ButWithCopiesThereMsg(withCopies.size());
        display(message);
        log(message, Converter.convertToArrayListOfArrayLists(withCopies));
        return withCopies;
    }

    private void deleteDir1FilesWithoutCorrespondentInDir2ButWithCopiesThere() throws IOException {
        ArrayList<File> withCopies = listDir1FilesWithoutCorrespondentInDir2ButWithCopiesThere();
        if (!withCopies.isEmpty() && confirm()) {
            display(text.getDeletingFilesMsg());
            Changer.deleteFiles(withCopies, this);
        }
    }

    private void listDuplicates() throws IOException {
        HashMap<String, File> files = getDirectoryFiles(text.getDirMsg());
        Finder finder = new Finder(this);
        ArrayList<ArrayList<File>> duplicates = finder.findDuplicates(files);

        int quantity = 0;
        long size = 0;
        for (ArrayList<File> list : duplicates)
            for (int i = 1; i < list.size(); i++) {
                quantity++;
                size += list.get(i).length();
            }
        String message = text.getDuplicateFilesMsg(quantity, size);
        display(message);
        log(message, duplicates);
    }

    private void listDir1FilesWithCopiesSomewhereInDir2() throws IOException {
        HashMap<String, File> dir1 = getDirectoryFiles(text.getDir1Msg());
        HashMap<String, File> dir2 = getDirectoryFiles(text.getDir2Msg());
        Finder finder = new Finder(this);
        ArrayList<File> withCopies = finder.findFiles1WithCopiesSomewhereInFiles2(dir1, dir2);
        String message = text.getDir1FilesWithCopiesSomewhereInDir2(withCopies.size());
        display(message);
        log(message, Converter.convertToArrayListOfArrayLists(withCopies));
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

    private HashMap<String, File> getDirectoryFiles(String message) throws NotDirectoryException {
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
        String myStr = str;
        if (backtrackCounter > 0)
            myStr = "\n" + myStr;
        displayAux(myStr);
        backtrackCounter = 0;
        remainingTime = null;
    }

    public void displayProgress(long done, long total, int found) {
        double percentage = total == 0 ? 100 : done * 100.0 / total;
        if (remainingTime == null)
            remainingTime = new RemainingTime(total);

        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < backtrackCounter; i++)
            sb1.append("\b");

        StringBuilder sb2 = new StringBuilder();
        sb2.append(done).append(" / ").append(total).append(" = ").append(String.format("%.3f", percentage)).append("% ").append(text.getFoundMsg()).append(found);
        sb2.append(" ").append(text.getRemainingTimeMsg()).append(remainingTime.getRemainingTime(done, text));

        for (int i = sb2.length(); i < backtrackCounter; i++)
            sb2.append(" ");

        backtrackCounter = sb2.length();
        sb1.append(sb2);
        displayAux(sb1.toString());
    }

    private void displayAux(String str) {
        System.out.print(str);
        System.out.flush();
    }

    private String adjustDirectory(String dir) {
        String newDir = dir;
        if (newDir.endsWith("/") || newDir.endsWith("\\"))
            newDir = newDir.substring(0, newDir.length() - 1);
        return newDir;
    }

    private File getFile(String path) throws NoSuchFileException {
        File f = new File(path);
        if (!f.isFile())
            throw new NoSuchFileException(path);
        return f;
    }

    private void log(String message, ArrayList<ArrayList<File>> list) throws IOException {
        Logger logger = new Logger(text);
        display(text.getGeneratedLoggerMsg(logger.getPath()));
        logger.list(message, list);
        logger.close();
    }
}
