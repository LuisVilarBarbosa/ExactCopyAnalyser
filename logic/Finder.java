package logic;

import objects.File;
import ui.UserInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Finder {
    private UserInterface userInterface;
    private Comparator comparator;

    public Finder(UserInterface userInterface) {
        this.userInterface = userInterface;
        this.comparator = new Comparator(true, this.userInterface);
    }

    public ArrayList<File> findFiles1WithoutCorrespondentInFiles2ButWithCopiesThere(HashMap<String, File> files1, HashMap<String, File> files2) throws IOException {
        HashMap<String, File> withoutCorrespondent = findFiles1WithoutCorrespondentOnFiles2(files1, files2);
        ArrayList<File> withCopies = findFiles1WithCopiesSomewhereInFiles2(withoutCorrespondent, files2);
        return withCopies;
    }

    public HashMap<String, File> findFiles1WithoutCorrespondentOnFiles2(HashMap<String, File> files1, HashMap<String, File> files2) {
        HashMap<String, File> withoutCorrespondent = new HashMap<>();
        for (String key1 : files1.keySet()) {
            File f1 = files1.get(key1);
            File f2 = files2.get(key1);
            if (f2 == null)
                withoutCorrespondent.put(key1, f1);
        }
        return withoutCorrespondent;
    }

    public ArrayList<File> findFiles1WithCopiesSomewhereInFiles2(HashMap<String, File> files1, HashMap<String, File> files2) throws IOException {
        ArrayList<File> withCopies = new ArrayList<>();
        int files2Size = files2.size();
        long completedComparisons = 0;
        long totalComparisons = files1.size() * files2Size;

        userInterface.displayProgress(completedComparisons, totalComparisons, withCopies.size());
        for (File f : files1.values()) {
            if (!findCopiesOfFile(f, files2, true).isEmpty())
                withCopies.add(f);
            completedComparisons += files2Size;
            userInterface.displayProgress(completedComparisons, totalComparisons, withCopies.size());
        }
        return withCopies;
    }

    public ArrayList<ArrayList<File>> findDuplicates(HashMap<String, File> files) throws IOException {
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
            if (!alreadyAnalysed.contains(key1)) {
                File f1 = files.get(key1);
                alreadyAnalysed.add(key1);
                ArrayList<File> equalsToF1 = new ArrayList<>();
                equalsToF1.add(f1);

                for (int j = i + 1; j < size; j++) {
                    String key2 = keys.get(j);
                    if (!alreadyAnalysed.contains(key2)) {
                        File f2 = files.get(key2);
                        if (comparator.areFilesEqual(f1, f2)) {
                            equalsToF1.add(f2);
                            alreadyAnalysed.add(key2);
                        }
                    }
                }

                int duplicatesOfF1 = equalsToF1.size() - 1;
                if (duplicatesOfF1 >= 1) {
                    duplicates.add(equalsToF1);
                    alreadyFound += duplicatesOfF1;
                }
            }
            completedComparisons += size - i - 1;
            userInterface.displayProgress(completedComparisons, totalComparisons, alreadyFound);
        }
        return duplicates;
    }

    private ArrayList<File> findCopiesOfFile(File file, HashMap<String, File> files, boolean stopOnFirstFound) throws IOException {
        ArrayList<File> copies = new ArrayList<>();
        for (File f : files.values())
            if (comparator.areFilesEqual(file, f)) {
                copies.add(f);
                if (stopOnFirstFound)
                    break;
            }
        return copies;
    }
}
