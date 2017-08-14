import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Logger {
    private File file;
    private FileOutputStream fileOutputStream;

    public Logger(Text text) throws Exception {
        String filename;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(Calendar.getInstance().getTime());

        do {
            filename = "ExactCopyAnalyser " + timeStamp + " " + Long.toHexString(Double.doubleToLongBits(Math.random())) + ".txt";
            file = new File(filename);
        } while (file.exists());

        this.fileOutputStream = new FileOutputStream(file);
        this.fileOutputStream.write(text.getLoggerStartMsg().getBytes());
    }

    public String getPath() {
        return file.getPath();
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
