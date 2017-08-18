import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;

public class Comparator {
    private static final int BUFFER_SIZE = 10485760;   // 10 MB
    private boolean compareContent;
    private byte[] data1;
    private byte[] data2;
    private UserInterface userInterface;

    public Comparator(boolean compareContent, UserInterface userInterface) {
        this.compareContent = compareContent;
        this.data1 = new byte[BUFFER_SIZE];
        this.data2 = new byte[BUFFER_SIZE];
        this.userInterface = userInterface;
    }

    public HashMap<File, File> compareDirectoriesWithSameStructure(HashMap<String, File> source, HashMap<String, File> destination) throws Exception {
        HashMap<File, File> notEqual = new HashMap<>();

        int size = source.size();
        double interval = size > 100 ? size / 100 : 1;
        Iterator<String> it = source.keySet().iterator();

        for (int i = 0; it.hasNext(); ) {
            for (int j = 0; j < interval && it.hasNext(); j++, i++) {
                String key = it.next();
                File f2 = destination.get(key);
                if (f2 != null) {
                    File f1 = source.get(key);
                    if (!areFilesEqual(f1, f2))
                        notEqual.put(f1, f2);
                }
            }
            userInterface.displayProgress(i, size, notEqual.size());
        }
        return notEqual;
    }

    public boolean areFilesEqual(File f1, File f2) throws Exception {
        long f1Length = f1.length();
        if (f1Length != f2.length() || f1.lastModified() != f2.lastModified())
            return false;

        if (compareContent) {
            FileInputStream fis1 = new FileInputStream(f1.getAbsolutePath());
            FileInputStream fis2 = new FileInputStream(f2.getAbsolutePath());

            for (long i = 0; i < f1Length; i += BUFFER_SIZE) {
                int read1 = fis1.read(data1);
                int read2 = fis2.read(data2);

                if (read1 != read2) {
                    fis1.close();
                    fis2.close();
                    throw new Exception(userInterface.getText().getFileReadErrorMsg());
                }

                for (int j = 0; j < read1; j++)
                    if (data1[j] != data2[j]) {
                        fis1.close();
                        fis2.close();
                        return false;
                    }
            }

            fis1.close();
            fis2.close();
        }

        return true;
    }
}
