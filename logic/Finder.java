package logic;

import objects.File;
import ui.Status;
import ui.Text;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.*;

public class Finder {
    private boolean compareLastModified;
    private Text text;
    private Status status;

    public Finder(boolean compareLastModified, Text text, Status status) {
        this.compareLastModified = compareLastModified;
        this.text = text;
        this.status = status;
    }

    public ArrayList<File> findFiles1WithoutCorrespondentInFiles2ButWithCopiesThere(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2) throws IOException {
        LinkedHashMap<String, File> withoutCorrespondent = findFiles1WithoutCorrespondentOnFiles2(files1, files2);
        return findFiles1WithOrWithoutCopiesSomewhereInFiles2(withoutCorrespondent, files2, true);
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

    public ArrayList<ArrayList<File>> findNotEqualCorrespondentsInDirectoriesWithSameStructure(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2, boolean compareContent) throws IOException {
        ArrayList<ArrayList<File>> notEqual = new ArrayList<>();

        int size = files1.size();
        double interval = size > 100000 ? size / 100000 : (size > 100 ? size / 100 : 1);
        Iterator<String> it = files1.keySet().iterator();

        int i = 0;
        status.setup(i, size, notEqual.size());
        while (it.hasNext()) {
            for (int j = 0; j < interval && it.hasNext(); j++, i++) {
                String key = it.next();
                File f2 = files2.get(key);
                if (f2 != null) {
                    File f1 = files1.get(key);
                    if (!Comparator.areFilesEqual(f1, f2, compareLastModified, compareContent, text)) {
                        ArrayList<File> notEqualPair = new ArrayList<>();
                        notEqualPair.add(f1);
                        notEqualPair.add(f2);
                        notEqual.add(notEqualPair);
                    }
                }
            }
            status.update(i, notEqual.size());
        }
        status.complete();
        return notEqual;
    }

    public ArrayList<File> findFiles1WithOrWithoutCopiesSomewhereInFiles2(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2, boolean withCopies) throws IOException {
        ArrayList<File> withOrWithoutCopies = new ArrayList<>();
        int files2Size = files2.size();
        long completedComparisons = 0;
        long totalComparisons = (long) files1.size() * files2Size;

        status.setup(completedComparisons, totalComparisons, withOrWithoutCopies.size());
        for (String key1 : files1.keySet()) {
            File f1 = files1.get(key1);
            boolean hasCopy = fileHasCopy(key1, f1, files2);
            if ((withCopies && hasCopy) || (!withCopies && !hasCopy))
                withOrWithoutCopies.add(f1);
            completedComparisons += files2Size;
            status.update(completedComparisons, withOrWithoutCopies.size());
        }
        status.complete();
        return withOrWithoutCopies;
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
                        if (Comparator.areFilesEqual(f1, f2, compareLastModified, true, text)) {
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

    private boolean fileHasCopy(String fileKey, File file, LinkedHashMap<String, File> files) throws IOException {
        final boolean compareContent = true;
        File fileOfKey = files.get(fileKey);
        if (fileOfKey != null && Comparator.areFilesEqual(file, fileOfKey, compareLastModified, compareContent, text))
            return true;

        for (File f : files.values())
            if (Comparator.areFilesEqual(file, f, compareLastModified, compareContent, text))
                return true;

        return false;
    }

    public ArrayList<File> findFoldersWithoutFilesAsDescendants(File baseDirectory) throws NotDirectoryException {
        if (!baseDirectory.isDirectory())
            throw new NotDirectoryException(baseDirectory.getAbsolutePath());

        ArrayList<File> folders = new ArrayList<>();
        findFoldersWithoutFilesAsDescendantsAux(baseDirectory, folders);
        return folders;
    }

    private void findFoldersWithoutFilesAsDescendantsAux(File baseDirectory, ArrayList<File> folders) {
        for (File f : baseDirectory.listFiles())
            if (f.isDirectory())
                findFoldersWithoutFilesAsDescendantsAux(f, folders);

        ArrayList<File> listedFiles = new ArrayList<>();
        Collections.addAll(listedFiles, baseDirectory.listFiles());
        if (listedFiles.isEmpty() || folders.containsAll(listedFiles))
            folders.add(baseDirectory);
    }
}
