import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Logger {
    private File file;
    private FileOutputStream fileOutputStream;

    public Logger() throws Exception {
        String filename;
        boolean invalidFilename = true;

        do {
            filename = Long.toHexString(Double.doubleToLongBits(Math.random()));
            filename = filename.concat(".txt");
            file = new File(filename);
            if (!file.exists())
                invalidFilename = false;
        } while (invalidFilename);

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

    public void close() throws IOException {
        fileOutputStream.close();
    }
}