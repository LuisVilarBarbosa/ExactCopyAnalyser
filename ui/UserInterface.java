package ui;

import logic.*;
import objects.File;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class UserInterface {
    private static final int EXIT = 0;
    private static final int SLEEP_MILLIS = 100;
    private static final int EXIT_STATUS = 1;
    private Text text;
    private int backtrackCounter;
    private Status status;

    public UserInterface() {
        this.text = new Text();
        this.backtrackCounter = 0;
        this.status = new Status(text);
    }

    public void start() {
        display(text.getOptionsMsg());
        int option;

        do {
            status.reset();
            option = selectOption(EXIT, 9);
            if (option != EXIT) {
                Thread executionThread = executeOptionOnNewThread(option);
                displayProgressWhileThreadAlive(executionThread);
            }
        } while (option != EXIT);
    }

    public Text getText() {
        return text;
    }

    public Status getStatus() {
        return status;
    }

    private Thread executeOptionOnNewThread(int option) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    executeOption(option);
                } catch (NotDirectoryException e) {
                    display(text.getNotDirErrorMsg(e.getFile()));
                } catch (NoSuchFileException e) {
                    display(text.getNotFileErrorMsg(e.getFile()));
                } catch (IOException e) {
                    display(e.getMessage());
                }
            }
        };
        thread.start();
        return thread;
    }

    private void displayProgressWhileThreadAlive(Thread thread) {
        /*
        Thread.sleep() and status.isUpdated() reduce the thread load
        and reduce the probability of interleaving the text output
        of tools as "java -Xprof" with the text output of this program.
         */
        try {
            while (thread.isAlive() && !status.isActive())
                Thread.sleep(SLEEP_MILLIS);
            while (thread.isAlive() && status.isActive() && !status.isComplete()) {
                if (status.isUpdated())
                    displayProgress();
                Thread.sleep(SLEEP_MILLIS);
            }
            while (thread.isAlive())
                Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException e) {
            display(text.getThreadInterruptedExceptionMsg());
            System.exit(EXIT_STATUS);
        }
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
            case 9:
                listDir1FilesWithoutCopiesAnywhereInDir2();
                break;
            default:
                display(text.getInvalidOptionMsg());
                break;
        }
    }

    private void listCorrespondentsNotEqualInDirectoriesWithSameStructure(boolean compareContent) throws IOException {
        LinkedHashMap<String, File> dir1 = getDirectoryFiles(text.getDir1Msg());
        LinkedHashMap<String, File> dir2 = getDirectoryFiles(text.getDir2Msg());

        Finder finder = new Finder(this);
        LinkedHashMap<String, File> withoutCorrespondentOnDir2 = finder.findFiles1WithoutCorrespondentOnFiles2(dir1, dir2);
        LinkedHashMap<String, File> withoutCorrespondentOnDir1 = finder.findFiles1WithoutCorrespondentOnFiles2(dir2, dir1);
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
        LinkedHashMap<String, File> dir1 = getDirectoryFiles(text.getDir1Msg());
        LinkedHashMap<String, File> dir2 = getDirectoryFiles(text.getDir2Msg());
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
        LinkedHashMap<String, File> files = getDirectoryFiles(text.getDirMsg());
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
        LinkedHashMap<String, File> dir1 = getDirectoryFiles(text.getDir1Msg());
        LinkedHashMap<String, File> dir2 = getDirectoryFiles(text.getDir2Msg());
        Finder finder = new Finder(this);
        ArrayList<File> withCopies = finder.findFiles1WithCopiesSomewhereInFiles2(dir1, dir2);
        String message = text.getDir1FilesWithCopiesSomewhereInDir2Msg(withCopies.size());
        display(message);
        log(message, Converter.convertToArrayListOfArrayLists(withCopies));
    }

    private void listDir1FilesWithoutCopiesAnywhereInDir2() throws IOException {
        LinkedHashMap<String, File> dir1 = getDirectoryFiles(text.getDir1Msg());
        LinkedHashMap<String, File> dir2 = getDirectoryFiles(text.getDir2Msg());
        Finder finder = new Finder(this);
        ArrayList<File> withoutCopies = finder.findFiles1WithoutCopiesAnywhereOnFiles2(dir1, dir2);
        String message = text.getDir1FilesWithoutCopiesAnywhereInDir2Msg(withoutCopies.size());
        display(message);
        log(message, Converter.convertToArrayListOfArrayLists(withoutCopies));
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

    private LinkedHashMap<String, File> getDirectoryFiles(String message) throws NotDirectoryException {
        String dir = getString(message);
        dir = adjustDirectory(dir);
        LinkedHashMap<String, File> files = Loader.getDirectoryFiles(dir);
        return files;
    }

    private String getString(String message) {
        display(message);
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

    public synchronized void display(String str) {
        String myStr = str;
        if (backtrackCounter > 0) {
            if (status.isComplete() && status.isUpdated())
                displayProgress();
            myStr = "\n" + myStr;
        }
        displayAux(myStr);
        backtrackCounter = 0;
    }

    public synchronized void displayProgress() {
        long done = status.getDone();
        long total = status.getTotal();
        int found = status.getFound();
        String remainingTime = status.getRemainingTime();

        double percentage = total == 0 ? 100 : done * 100.0 / total;

        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < backtrackCounter; i++)
            sb1.append("\b");

        StringBuilder sb2 = new StringBuilder();
        sb2.append(done).append(" / ").append(total).append(" = ").append(String.format("%.3f", percentage)).append("% ").append(text.getFoundMsg()).append(found);
        sb2.append(" ").append(text.getRemainingTimeMsg()).append(remainingTime);

        for (int i = sb2.length(); i < backtrackCounter; i++)
            sb2.append(" ");

        backtrackCounter = sb2.length();
        sb1.append(sb2);
        displayAux(sb1.toString());
    }

    private void displayAux(String str) {
        synchronized (System.out) {
            System.out.print(str);
            System.out.flush();
        }
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
