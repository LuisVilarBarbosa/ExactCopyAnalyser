package logic;

import objects.File;
import ui.UserInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Finder {

    public static ArrayList<ArrayList<File>> findDir1FilesThatAreNotInDir2ButHaveCopiesThere(HashMap<String, File> dir1, HashMap<String, File> dir2, UserInterface userInterface) throws IOException {
        HashMap<String, File> notFound = findDir1FilesNonExistingOnDir2(dir1, dir2);
        ArrayList<ArrayList<File>> copies = findCopiesOfFilesToSearch(dir2, notFound, userInterface);
        return copies;
    }

    public static HashMap<String, File> findDir1FilesNonExistingOnDir2(HashMap<String, File> dir1, HashMap<String, File> dir2) {
        HashMap<String, File> notFound = new HashMap<>();
        for (String key1 : dir1.keySet()) {
            File f1 = dir1.get(key1);
            File f2 = dir2.get(key1);
            if (f2 == null)
                notFound.put(key1, f1);
        }
        return notFound;
    }

    public static ArrayList<ArrayList<File>> findCopiesOfFilesToSearch(HashMap<String, File> allFiles, HashMap<String, File> filesToSearch, UserInterface userInterface) throws IOException {
        Comparator comparator = new Comparator(true, userInterface);
        ArrayList<ArrayList<File>> repeatedWithoutDirectCorrespondent = new ArrayList<>();
        int allFilesSize = allFiles.size();
        long completedComparisons = 0;
        long totalComparisons = filesToSearch.size() * allFilesSize;
        int repeated = 0;

        userInterface.displayProgress(completedComparisons, totalComparisons, repeated);
        for (File f1 : filesToSearch.values()) {
            ArrayList<File> equalsToF1 = new ArrayList<>();
            equalsToF1.add(f1);

            for (File f2 : allFiles.values())
                if (!f1.equals(f2) && comparator.areFilesEqual(f1, f2)) {
                    equalsToF1.add(f2);
                    repeated++;
                }
            completedComparisons += allFilesSize;
            userInterface.displayProgress(completedComparisons, totalComparisons, repeated);

            if (equalsToF1.size() > 1)
                repeatedWithoutDirectCorrespondent.add(equalsToF1);
        }
        return repeatedWithoutDirectCorrespondent;
    }

    public static ArrayList<ArrayList<File>> findDuplicates(HashMap<String, File> files, UserInterface userInterface) throws IOException {
        Comparator comparator = new Comparator(true, userInterface);
        ArrayList<ArrayList<File>> duplicates = new ArrayList<>();

        ArrayList<String> keys = Converter.convertToArrayListOfKeys(files);
        int size = keys.size();
        HashSet<String> alreadyAnalysed = new HashSet<>(size);
        long completedComparisons = 0;
        long totalComparisons = 0;
        int alreadyFound = 0;

        for (int i = 0; i < size; i++)
            totalComparisons += size - i - 1;

        userInterface.displayProgress(completedComparisons, totalComparisons, alreadyFound);
        for (int i = 0; i < size; i++) {
            String key1 = keys.get(i);
            File f1 = files.get(key1);
            if (!alreadyAnalysed.contains(key1)) {
                alreadyAnalysed.add(key1);
                ArrayList<File> equalsToF1 = new ArrayList<>();
                equalsToF1.add(f1);

                for (int j = i + 1; j < size; j++) {
                    String key2 = keys.get(j);
                    File f2 = files.get(key2);
                    if (!alreadyAnalysed.contains(key2) && comparator.areFilesEqual(f1, f2)) {
                        equalsToF1.add(f2);
                        alreadyAnalysed.add(key2);
                        alreadyFound++;
                    }
                }

                if (equalsToF1.size() > 1)
                    duplicates.add(equalsToF1);
            }
            completedComparisons += size - i - 1;
            userInterface.displayProgress(completedComparisons, totalComparisons, alreadyFound);
        }
        return duplicates;
    }
}
