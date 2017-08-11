import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Finder {

    public ArrayList<ArrayList<File>> findRepeatedFiles(HashMap<String, File> files, ArrayList<File> notFound) throws Exception {
        Comparator comparator = new Comparator(true);
        ArrayList<ArrayList<File>> repeatedWithoutDirectCorrespondent = new ArrayList<>();
        int size = notFound.size();

        for (int i = 0; i < size; i++) {
            System.out.println(i + " / " + size);
            File f1 = notFound.get(i);
            ArrayList<File> equalsToF1 = new ArrayList<>();
            equalsToF1.add(f1);

            for (File f2 : files.values())
                if (!f1.equals(f2) && comparator.areFilesEqual(f1, f2))
                    equalsToF1.add(f2);

            if (equalsToF1.size() > 1)
                repeatedWithoutDirectCorrespondent.add(equalsToF1);
        }
        return repeatedWithoutDirectCorrespondent;
    }
}
