package logic;

import objects.File;
import ui.Status;
import ui.UserInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Finder {
    private UserInterface userInterface;
    private Comparator comparator;
    private Status status;

    public Finder(UserInterface userInterface) {
        this.userInterface = userInterface;
        this.comparator = new Comparator(true, this.userInterface);
        this.status = this.userInterface.getStatus();
    }

    public ArrayList<File> findFiles1WithoutCorrespondentInFiles2ButWithCopiesThere(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2) throws IOException {
        LinkedHashMap<String, File> withoutCorrespondent = findFiles1WithoutCorrespondentOnFiles2(files1, files2);
        ArrayList<File> withCopies = findFiles1WithCopiesSomewhereInFiles2(withoutCorrespondent, files2);
        return withCopies;
    }

    public LinkedHashMap<String, File> findFiles1WithoutCorrespondentOnFiles2(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2) {
        LinkedHashMap<String, File> withoutCorrespondent = new LinkedHashMap<>();
        for (String key1 : files1.keySet()) {
            File f1 = files1.get(key1);
            File f2 = files2.get(key1);
            if (f2 == null)
                withoutCorrespondent.put(key1, f1);
        }
        return withoutCorrespondent;
    }

    public ArrayList<File> findFiles1WithCopiesSomewhereInFiles2(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2) throws IOException {
        ArrayList<File> withCopies = new ArrayList<>();
        int files2Size = files2.size();
        long completedComparisons = 0;
        long totalComparisons = files1.size() * files2Size;

        status.setup(completedComparisons, totalComparisons, withCopies.size());
        for (String key1 : files1.keySet()) {
            File f1 = files1.get(key1);
            if (fileHasCopy(key1, f1, files2))
                withCopies.add(f1);
            completedComparisons += files2Size;
            status.update(completedComparisons, withCopies.size());
        }
        status.complete();
        return withCopies;
    }

    public ArrayList<ArrayList<File>> findDuplicates(LinkedHashMap<String, File> files) throws IOException {
        ArrayList<ArrayList<File>> duplicates = new ArrayList<>();

        ArrayList<String> keys = Converter.convertToArrayListOfKeys(files);
        int size = keys.size();
        HashSet<String> alreadyAnalysed = new HashSet<>(size);
        long completedComparisons = 0;
        long totalComparisons = 0;
        int alreadyFound = 0;

        for (int i = 0; i < size; i++)
            totalComparisons += size - i - 1;

        status.setup(completedComparisons, totalComparisons, alreadyFound);
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
            status.update(completedComparisons, alreadyFound);
        }
        status.complete();
        return duplicates;
    }

    public ArrayList<File> findFiles1WithoutCopiesAnywhereOnFiles2(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2) throws IOException {
        ArrayList<File> withoutCopies = new ArrayList<>();
        int files2Size = files2.size();
        long completedComparisons = 0;
        long totalComparisons = files1.size() * files2Size;

        status.setup(completedComparisons, totalComparisons, withoutCopies.size());
        for (String key1 : files1.keySet()) {
            File f1 = files1.get(key1);
            if (!fileHasCopy(key1, f1, files2))
                withoutCopies.add(f1);
            completedComparisons += files2Size;
            status.update(completedComparisons, withoutCopies.size());
        }
        status.complete();
        return withoutCopies;
    }

    private boolean fileHasCopy(String fileKey, File file, LinkedHashMap<String, File> files) throws IOException {
        File fileOfKey = files.get(fileKey);
        if (fileOfKey != null && comparator.areFilesEqual(file, fileOfKey))
            return true;

        for (File f : files.values())
            if (comparator.areFilesEqual(file, f))
                return true;

        return false;
    }
}
