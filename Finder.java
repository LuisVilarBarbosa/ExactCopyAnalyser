import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Finder {

    public static ArrayList<ArrayList<File>> findFilesThatAreNotInDestinationButHaveCopiesThere(HashMap<String, File> source, HashMap<String, File> destination) throws Exception {
        HashMap<String, File> notFound = findNonExistingOnDestination(source, destination);
        ArrayList<ArrayList<File>> repeatedFiles = findRepeatedFiles(destination, notFound);
        return repeatedFiles;
    }

    public static HashMap<String, File> findNonExistingOnDestination(HashMap<String, File> source, HashMap<String, File> destination) {
        HashMap<String, File> notFound = new HashMap<>();
        for (String key1 : source.keySet()) {
            File f1 = source.get(key1);
            File f2 = destination.get(key1);
            if (f2 == null)
                notFound.put(key1, f1);
        }
        return notFound;
    }

    public static ArrayList<ArrayList<File>> findRepeatedFiles(HashMap<String, File> files, HashMap<String, File> notFound) throws Exception {
        Comparator comparator = new Comparator(true);
        ArrayList<ArrayList<File>> repeatedWithoutDirectCorrespondent = new ArrayList<>();
        int i = 0;
        int size = notFound.size();

        for (File f1 : notFound.values()) {
            System.out.println(i + " / " + size);
            ArrayList<File> equalsToF1 = new ArrayList<>();
            equalsToF1.add(f1);

            for (File f2 : files.values())
                if (!f1.equals(f2) && comparator.areFilesEqual(f1, f2))
                    equalsToF1.add(f2);

            if (equalsToF1.size() > 1)
                repeatedWithoutDirectCorrespondent.add(equalsToF1);
            i++;
        }
        return repeatedWithoutDirectCorrespondent;
    }


    public static ArrayList<ArrayList<File>> findDuplicates(HashMap<String, File> files) throws Exception {
        Comparator comparator = new Comparator(true);
        ArrayList<ArrayList<File>> duplicates = new ArrayList<>();

        ArrayList<File> myFiles = Converter.convertToArrayListOfValues(files);
        HashSet<File> alreadyFound = new HashSet<>();
        int size = myFiles.size();

        for (int i = 0; i < size; i++) {
            System.out.println(i + " / " + size + " : " + alreadyFound.size());
            ArrayList<File> equalsToF1 = new ArrayList<>();

            File f1 = myFiles.get(i);
            equalsToF1.add(f1);

            for (int j = i + 1; j < size; j++) {
                File f2 = myFiles.get(j);
                if (!alreadyFound.contains(f2) && comparator.areFilesEqual(f1, f2)) {
                    equalsToF1.add(f2);
                    alreadyFound.add(f2);
                }
            }

            if (equalsToF1.size() > 1)
                duplicates.add(equalsToF1);
        }
        return duplicates;
    }
}
