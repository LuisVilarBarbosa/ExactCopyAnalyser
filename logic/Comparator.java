package logic;

import objects.File;
import ui.Text;

import java.io.FileInputStream;
import java.io.IOException;

public class Comparator {
    private static final int BUFFER_SIZE = 10485760;   // 10 MB
    private static final byte[] data1 = new byte[BUFFER_SIZE];
    private static final byte[] data2 = new byte[BUFFER_SIZE];

    public static boolean areFilesEqual(File f1, File f2, boolean compareLastModified, boolean compareContent, Text text) throws IOException {
        long f1Length = f1.length();
        if (f1Length != f2.length() || (compareLastModified && f1.lastModified() != f2.lastModified()))
            return false;

        if (compareContent) {
            FileInputStream fis1 = new FileInputStream(f1.getAbsolutePath());
            FileInputStream fis2 = new FileInputStream(f2.getAbsolutePath());

            try {
                for (long i = 0; i < f1Length; i += BUFFER_SIZE) {
                    int read1 = fis1.read(data1);
                    int read2 = fis2.read(data2);

                    if (read1 != read2)
                        throw new IOException(text.getFileReadErrorMsg());

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
