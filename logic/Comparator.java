package logic;

import objects.File;
import ui.Status;
import ui.UserInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Comparator {
    private static final int BUFFER_SIZE = 10485760;   // 10 MB
    private boolean compareContent;
    private byte[] data1;
    private byte[] data2;
    private UserInterface userInterface;
    private Status status;

    public Comparator(boolean compareContent, UserInterface userInterface) {
        this.compareContent = compareContent;
        this.data1 = new byte[BUFFER_SIZE];
        this.data2 = new byte[BUFFER_SIZE];
        this.userInterface = userInterface;
        this.status = this.userInterface.getStatus();
    }

    // To move to Finder
    public ArrayList<ArrayList<File>> findNotEqualCorrespondentsInDirectoriesWithSameStructure(LinkedHashMap<String, File> files1, LinkedHashMap<String, File> files2) throws IOException {
        ArrayList<ArrayList<File>> notEqual = new ArrayList<>();

        int size = files1.size();
        double interval = size > 100 ? size / 100 : 1;
        Iterator<String> it = files1.keySet().iterator();

        int i = 0;
        status.setup(i, size, notEqual.size());
        while (it.hasNext()) {
            for (int j = 0; j < interval && it.hasNext(); j++, i++) {
                String key = it.next();
                File f2 = files2.get(key);
                if (f2 != null) {
                    File f1 = files1.get(key);
                    if (!areFilesEqual(f1, f2)) {
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

    public boolean areFilesEqual(File f1, File f2) throws IOException {
        long f1Length = f1.length();
        if (f1Length != f2.length() || f1.lastModified() != f2.lastModified())
            return false;

        if (compareContent) {
            FileInputStream fis1 = new FileInputStream(f1.getAbsolutePath());
            FileInputStream fis2 = new FileInputStream(f2.getAbsolutePath());

            try {
                for (long i = 0; i < f1Length; i += BUFFER_SIZE) {
                    int read1 = fis1.read(data1);
                    int read2 = fis2.read(data2);

                    if (read1 != read2)
                        throw new IOException(userInterface.getText().getFileReadErrorMsg());

                    for (int j = 0; j < read1; j++)
                        if (data1[j] != data2[j])
                            return false;
                }
            } finally {
                // This code has been tested to be executed before the return, before the throw and on normal execution.
                fis1.close();
                fis2.close();
            }
        }

        return true;
    }
}
