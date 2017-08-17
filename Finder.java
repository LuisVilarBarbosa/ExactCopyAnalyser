import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Finder {

    public static ArrayList<ArrayList<File>> findDir1FilesThatAreNotInDir2ButHaveCopiesThere(HashMap<String, File> dir1, HashMap<String, File> dir2, UserInterface userInterface) throws Exception {
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

    public static ArrayList<ArrayList<File>> findCopiesOfFilesToSearch(HashMap<String, File> allFiles, HashMap<String, File> filesToSearch, UserInterface userInterface) throws Exception {
        Comparator comparator = new Comparator(true);
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
                if (!f1.equals(f2) && comparator.areFilesEqual(f1, f2, userInterface)) {
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

    public static ArrayList<ArrayList<File>> findDuplicates(HashMap<String, File> files, UserInterface userInterface) throws Exception {
        Comparator comparator = new Comparator(true);
        ArrayList<ArrayList<File>> duplicates = new ArrayList<>();

        ArrayList<File> myFiles = Converter.convertToArrayListOfValues(files);
        int size = myFiles.size();
        HashSet<File> alreadyFound = new HashSet<>(size);
        long completedComparisons = 0;
        long totalComparisons = 0;

        for (int i = 0; i < size; i++)
            totalComparisons += size - i - 1;

        userInterface.displayProgress(completedComparisons, totalComparisons, alreadyFound.size());
        for (int i = 0; i < size; i++) {
            ArrayList<File> equalsToF1 = new ArrayList<>();

            File f1 = myFiles.get(i);
            equalsToF1.add(f1);

            for (int j = i + 1; j < size; j++) {
                File f2 = myFiles.get(j);
                if (!alreadyFound.contains(f2) && comparator.areFilesEqual(f1, f2, userInterface)) {
                    equalsToF1.add(f2);
                    alreadyFound.add(f2);
                }
            }
            completedComparisons += size - i - 1;
            userInterface.displayProgress(completedComparisons, totalComparisons, alreadyFound.size());

            if (equalsToF1.size() > 1)
                duplicates.add(equalsToF1);
        }
        return duplicates;
    }
}
