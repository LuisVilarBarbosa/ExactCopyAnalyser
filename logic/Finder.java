package logic;

import objects.File;
import ui.Status;
import ui.Text;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.*;

public class Finder {
    private final boolean compareLastModified;
    private Text text;
    private Status status;

    public Finder(boolean compareLastModified, Text text, Status status) {
        this.compareLastModified = compareLastModified;
        this.text = text;
        this.status = status;
    }

    public ArrayList<File> findFiles1WithoutCorrespondentInFiles2WithOrWithoutCopiesThere(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2, boolean withCopies) throws IOException {
        LinkedHashMap<String, File> withoutCorrespondent = findFiles1WithoutCorrespondentOnFiles2(files1, files2);
        return findFiles1WithOrWithoutCopiesSomewhereInFiles2(withoutCorrespondent, files2, withCopies);
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

        final int size = files1.size();
        final double interval = size > 100000 ? size / 100000 : (size > 100 ? size / 100 : 1);
        Iterator<String> it = files1.keySet().iterator();

        long analysedBytes = 0;
        long totalBytes = 0;
        for (String key : files1.keySet()) {
            File f2 = files2.get(key);
            if (f2 != null) {
                File f1 = files1.get(key);
                totalBytes += f1.length() + f2.length();
            }
        }

        status.setup(analysedBytes, totalBytes, notEqual.size());
        while (it.hasNext()) {
            for (int j = 0; j < interval && it.hasNext(); j++) {
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
                    analysedBytes += f1.length() + f2.length();
                }
            }
            status.update(analysedBytes, notEqual.size());
        }
        status.complete();
        return notEqual;
    }

    public ArrayList<File> findFiles1WithOrWithoutCopiesSomewhereInFiles2(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2, boolean withCopies) throws IOException {
        ArrayList<File> withOrWithoutCopies = new ArrayList<>();
        final long files2SumLengths = sumLengths(files2);
        long analysedBytes = 0;
        final long totalBytes = sumLengths(files1) + files2SumLengths * files1.size();

        status.setup(analysedBytes, totalBytes, withOrWithoutCopies.size());
        for (String key1 : files1.keySet()) {
            File f1 = files1.get(key1);
            boolean hasCopy = fileHasCopy(key1, f1, files2);
            if ((withCopies && hasCopy) || (!withCopies && !hasCopy))
                withOrWithoutCopies.add(f1);
            analysedBytes += f1.length() + files2SumLengths;
            status.update(analysedBytes, withOrWithoutCopies.size());
        }
        status.complete();
        return withOrWithoutCopies;
    }

    public ArrayList<ArrayList<File>> findDuplicates(LinkedHashMap<String, File> files) throws IOException {
        ArrayList<ArrayList<File>> duplicates = new ArrayList<>();

        ArrayList<String> keys = Converter.convertToArrayListOfKeys(files);
        final int size = keys.size();
        HashSet<String> alreadyAnalysed = new HashSet<>(size);
        long analysedBytes = 0;
        long totalBytes = 0;
        int alreadyFound = 0;

        final long filesSumLengths = sumLengths(files);
        long filesSumLengthsReducing = filesSumLengths;
        for (File f : files.values()) {
            totalBytes += filesSumLengthsReducing;
            filesSumLengthsReducing -= f.length();
        }

        filesSumLengthsReducing = filesSumLengths;
        status.setup(analysedBytes, totalBytes, alreadyFound);
        for (int i = 0; i < size; i++) {
            String key1 = keys.get(i);
            File f1 = files.get(key1);
            if (!alreadyAnalysed.contains(key1)) {
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
            analysedBytes += filesSumLengthsReducing;
            filesSumLengthsReducing -= f1.length();
            status.update(analysedBytes, alreadyFound);
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

    private long sumLengths(LinkedHashMap<String, File> files) {
        long sum = 0;
        for (File f : files.values())
            sum += f.length();
        return sum;
    }
}
