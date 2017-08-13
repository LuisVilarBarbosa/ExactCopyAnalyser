import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Logger {
    private File file;
    private FileOutputStream fileOutputStream;

    public Logger() throws Exception {
        String filename;

        do {
            filename = "ExactCopyAnalyser " + Long.toHexString(Double.doubleToLongBits(Math.random())) + ".txt";
            file = new File(filename);
        } while (file.exists());

        this.fileOutputStream = new FileOutputStream(file);
        this.fileOutputStream.write("ExactCopyAnalyser data will be stored below.\n\n".getBytes());
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

    public void list(String message, ArrayList<ArrayList<File>> list) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append("\n");
        for (ArrayList<File> l : list) {
            for (File f : l)
                sb.append(f.getAbsolutePath()).append("\n");
            sb.append("\n");
        }
        fileOutputStream.write(sb.toString().getBytes());
    }

    public void list(String message, HashMap<File, File> map) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append("\n");
        for (File f1 : map.keySet()) {
            File f2 = map.get(f1);
            sb.append(f1.getAbsolutePath()).append("\n")
                    .append(f2.getAbsolutePath()).append("\n\n");
        }
        fileOutputStream.write(sb.toString().getBytes());
    }

    public void close() throws IOException {
        fileOutputStream.close();
    }
}
